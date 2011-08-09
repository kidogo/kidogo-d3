package kidogod3.core.items;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import kidogod3.core.geo.GeoHashService;
import kidogod3.core.geo.client.LatLong;
import kidogod3.core.items.client.IItem;
import kidogod3.core.items.client.IItemImpl;
import kidogod3.core.items.client.ItemPhotoService;
import kidogod3.core.items.client.ItemsService;
import kidogod3.core.items.dao.Item;
import kidogod3.core.persistence.client.QueryResult;
import kidogod3.core.persistence.client.QueryResultImpl;
import kidogod3.core.users.client.UserService;

import org.datanucleus.store.appengine.query.JDOCursorHelper;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.GeoPt;
import com.google.inject.Inject;

public class ItemsServiceImpl implements ItemsService {
	
	PersistenceManager pm;
	UserService userService;
	GeoHashService geoCellsService;
	ItemPhotoService itemPhotoService;

	@Inject
	public ItemsServiceImpl(PersistenceManager pm,
			UserService userService,
			GeoHashService geoCellsService,
			ItemPhotoService itemPhotoService){
		this.pm = pm;
		this.userService = userService;
		this.geoCellsService = geoCellsService;
		this.itemPhotoService = itemPhotoService;
	}
	
	@Override
	public IItem saveItem(long userId, ItemOptions options) {
		if(!userService.isLoggedIn() || !userService.getCurrentUser().getUserId().equals(userId)){
			throw new IllegalStateException("Access Denied");
		}
		pm.currentTransaction().begin();
		try{
			Item item;
			if(options.getItemId()==null){
				item = new Item();
			}else{
				item = pm.getObjectById(Item.class, options.getItemId());
				Long itemUserId = item.getUserId();
				if(userId!=itemUserId){
					throw new IllegalStateException("Access denied: user does not own the item");
				}
			}
			if(options.getTitle()!=null){
				item.setTitle(options.getTitle());
			}
			if(options.getDescription()!=null){
				item.setDescription(options.getDescription());
			}
			if(options.getTagsToRemove()!=null){
				for(String tag:options.getTagsToRemove()){
					tag = tag.trim().toLowerCase();
					item.removeTag(tag);
				}
			}
			if(options.getTagsToAdd()!=null){
				for(String tag:options.getTagsToAdd()){
					tag = tag.trim().toLowerCase();
					item.addTag(tag);
				}
			}
			if(options.getPhotoIdsToRemove()!=null){
				for(String photoId:options.getPhotoIdsToRemove()){					
					item.removePhotoId(photoId);
				}
			}
			if(options.getPhotoIdsToAdd()!=null){
				for(String photoId:options.getPhotoIdsToAdd()){					
					item.addPhotoId(photoId);
				}
			}
			if(options.getPrice()!=null){
				if(options.getPrice().compareTo(BigDecimal.ZERO)==0){
					item.setPrice(null);
					item.setCurrencyCode(null);					
				}else{
					item.setPrice(options.getPrice());
					item.setCurrencyCode(options.getCurrencyCode());
				}
			}
			if(options.getLatLong()!=null){
				GeoPt point = new GeoPt((float)options.getLatLong().getLatitude(), (float)options.getLatLong().getLongitude());
				item.setPoint(point);				
				
				List<String> geoCells = geoCellsService.buildGeoCells(options.getLatLong());
				item.setGeoCells(geoCells);
			}
			if(!JDOHelper.isPersistent(item)){
				item = pm.makePersistent(item);
			}
			IItem result = convert(item);
			
			pm.currentTransaction().commit();
			
			return result; 
		}finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
		}
	}

	protected IItem convert(Item item){
		IItemImpl result = new IItemImpl();
		result.setItemId(item.getItemId());
		result.setItemTitle(item.getTitle());
		result.setDescription(item.getDescription());
		if(item.getTags()!=null){
			result.setTags(new ArrayList<String>(item.getTags()));
		}else{
			result.setTags(new ArrayList<String>());
		}
		result.setPostDate(item.getCreated());
		if(item.getPoint()!=null){
			result.setLatLong(new LatLong(item.getPoint().getLatitude(), item.getPoint().getLongitude()));
		}else{
			result.setLatLong(null);
		}
		result.setCurrencyCode(item.getCurrencyCode());
		result.setPrice(item.getPrice());
		if(item.getPhotoIds()!=null){
			result.setPhotoIds(new ArrayList<String>(item.getPhotoIds()));
			ArrayList<String> photoUrls = new ArrayList<String>();
			for(String photoId:item.getPhotoIds()){
				photoUrls.add(itemPhotoService.getPhotoUrl(photoId));
			}
			result.setPhotoUrls(photoUrls);
		}else{
			result.setPhotoUrls(new ArrayList<String>());
		}
		result.setOwnerName(item.getUserName());
		result.setPhoneNumber(item.getPhoneNumber());
		result.setEmail(item.getEmail());
		
		return result;
	}

	@Override
	public IItem getItem(long itemId) {
		pm.currentTransaction().begin();
		try{
			Item item = pm.getObjectById(Item.class, itemId);
			return convert(item);
		}finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
		}
	}

	@Override
	public QueryResult<IItem> listItems(ItemFilter filter) {
		Query q = pm.newQuery(Item.class);
		
		ArrayList<String> filters = new ArrayList<String>();
		Map<Object, Object> params = new HashMap<Object, Object>();
		if(filter.getText()!=null){
			List<String> terms = Item.buildTerms(filter.getText());
			String termCombo = Item.buildTermsCombo(terms);
			filters.add("this.termCombos:==termCombo");
			params.put("termCombo", termCombo);
		}
		if(filter.getUserId()!=null){
			filters.add("this.userId==:userId");
			params.put("userId", filter.getUserId());
		}
		if(filter.getBounds()!=null){
			List<String> cells = geoCellsService.buidlBoundingBoxCells(filter.getBounds());
			filters.add("this.geoCells==:geoCells");
			params.put("geoCells", cells);			
		}
		
		if(filter.getLimit()>0){
			q.setRange(0, filter.getLimit());
		}
		if(filter.getCursor()!=null){
			Map<Object, Object> ext = new HashMap<Object, Object>();
			Cursor cursor = Cursor.fromWebSafeString(filter.getCursor());
			ext.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
			q.setExtensions(ext);
		}
		if(!filters.isEmpty()){
			StringBuilder f = new StringBuilder();
			for(String f_:filters){
				if(f.length()>0){
					f.append(" & ");
				}
				f.append(f_);
			}
			q.setFilter(f.toString());
		}
		List<Item> items = (List<Item>)q.executeWithMap(params);
		List<IItem> iitems = new ArrayList<IItem>();
		for(Item item:items){
			iitems.add(convert(item));
		}
		Cursor c = JDOCursorHelper.getCursor(items);
		String cursor;
		if(c!=null){
			cursor = c.toWebSafeString();
		}else{
			cursor = null;
		}
		QueryResultImpl<IItem> result = new QueryResultImpl<IItem>();
		result.setCursor(cursor);
		result.setList(iitems);
		return result;
	}

}
