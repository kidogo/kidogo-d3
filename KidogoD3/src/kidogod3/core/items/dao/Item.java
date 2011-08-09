package kidogod3.core.items.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class Item implements StoreCallback{

	public static List<String> buildTerms(String text){
		ArrayList<String> result = new ArrayList<String>();
		if(text!=null){
			String[] parts = text.split(" ");
			for(String part:parts){
				part = part.trim().toLowerCase();
				if(part.length()>0){
					result.add(part);
				}
			}
		}
		
		return result;
		
	}
	
	protected static String combine(List<String> list, int from, int to){
		StringBuilder b = new StringBuilder();
		for(int i=from; i<=to; i++){
			if(b.length()>0){
				b.append("-");
			}
			b.append(list.get(i));
		}
		return b.toString();
	}
	
	public static String buildTermsCombo(List<String> terms){
		return combine(terms, 0, terms.size());
	}
	
	public static List<String> buildTermsPossibleCombos(List<String> terms){
		List<String> result = new ArrayList<String>();
		List<String> terms0 = new ArrayList<String>(terms);
		Collections.sort(terms0);
		for(int i=0; i<terms0.size(); i++){
			for(int j=i; j<terms0.size(); j++){
				String combo = combine(terms0, i, j);
				result.add(combo);
			}
		}
		return result;
	}
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String itemKey;
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
	private Long itemId;
	
	private String title;
	private Text description;
	
	private List<String> tags;
	
	private List<String> photoIds;
	
	private Date created;
	private Date modified;
	
	private GeoPt point;
	private List<String> geoCells;
	
	private String currencyCode;
	private BigDecimal price;
	
	private Long userId;
	private String userName;
	private String phoneNumber;
	private String email;
	
	private List<String> terms;
	private List<String> termCombos;
	
	public String getItemKey() {
		return itemKey;
	}
	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description!=null?description.getValue():null;
	}
	public void setDescription(String description) {
		this.description = description!=null?new Text(description):null;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public void removeTag(String tag){
		if(this.tags!=null){
			this.tags.remove(tag);
		}
	}
	public void addTag(String tag){
		if(this.tags==null){
			this.tags = new ArrayList<String>();
		}
		this.tags.add(tag);
	}
	public List<String> getPhotoIds() {
		return photoIds;
	}
	public void setPhotoIds(List<String> photoIds) {
		this.photoIds = photoIds;
	}
	public void removePhotoId(String photoId){
		if(this.photoIds!=null){
			this.photoIds.remove(photoId);
		}
	}
	public void addPhotoId(String photoId){
		if(this.photoIds==null){
			this.photoIds = new ArrayList<String>();
		}
		this.photoIds.add(photoId);
	}
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public GeoPt getPoint() {
		return point;
	}
	public void setPoint(GeoPt point) {
		this.point = point;
	}
	public List<String> getGeoCells() {
		return geoCells;
	}
	public void setGeoCells(List<String> geoCells) {
		this.geoCells = geoCells;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<String> getTerms() {
		return terms;
	}

	public void setTerms(List<String> terms) {
		this.terms = terms;
	}

	public List<String> getTermCombos() {
		return termCombos;
	}

	public void setTermCombos(List<String> termCombos) {
		this.termCombos = termCombos;
	}

	@Override
	public void jdoPreStore() {
		this.modified = new Date();
		if(JDOHelper.isNew(this)){
			this.created = this.modified;
		}
		
		Set<String> terms = new HashSet<String>();
		if(this.getTags()!=null){
			terms.addAll(this.getTags());
		}
		if(title!=null){
			List<String> t = Item.buildTerms(title);
			terms.addAll(t);
		}
		this.terms = new ArrayList<String>(terms);
		this.termCombos = Item.buildTermsPossibleCombos(this.terms);
	}
	
}
