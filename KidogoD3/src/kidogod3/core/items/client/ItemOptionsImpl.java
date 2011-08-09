package kidogod3.core.items.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import kidogod3.core.geo.client.LatLong;
import kidogod3.core.items.client.ItemsService.ItemOptions;

public class ItemOptionsImpl implements ItemOptions {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4515791696804087741L;
	
	private Long itemId;
	private String title;
	private String description;
	private List<String> tagsToRemove;
	private List<String> tagsToAdd;
	private LatLong latLong;
	private String currencyCode;
	private BigDecimal price;
	private List<String> photoIdsToRemove;
	private List<String> photoIdsToAdd;
	
	@Override
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	@Override
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public List<String> getTagsToRemove() {
		return tagsToRemove;
	}
	public void removeTag(String tag){
		if(this.tagsToRemove==null){
			this.tagsToRemove = new ArrayList<String>();
		}
		this.tagsToRemove.add(tag);
		if(this.tagsToAdd!=null){
			this.tagsToAdd.remove(tag);
		}		
	}
	@Override
	public List<String> getTagsToAdd() {
		return this.tagsToAdd;
	}
	public void addTag(String tag){
		if(this.tagsToAdd==null){
			this.tagsToAdd = new ArrayList<String>();
		}
		this.tagsToAdd.add(tag);
		if(this.tagsToRemove!=null){
			this.tagsToRemove.remove(tag);
		}
	}

	@Override
	public LatLong getLatLong() {
		return latLong;
	}
	public void setLatLong(LatLong latLong) {
		this.latLong = latLong;
	}
	@Override
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	@Override
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	@Override
	public List<String> getPhotoIdsToRemove() {		
		return this.photoIdsToRemove;
	}
	public void removePhotoId(String photoId){
		if(this.photoIdsToRemove==null){
			this.photoIdsToRemove = new ArrayList<String>();
		}
		this.photoIdsToRemove.add(photoId);
		if(this.photoIdsToAdd!=null){
			this.photoIdsToAdd.remove(photoId);
		}		
	}

	@Override
	public List<String> getPhotoIdsToAdd() {
		return this.photoIdsToAdd;
	}
	public void addPhotoId(String photoId){
		if(this.photoIdsToAdd==null){
			this.photoIdsToAdd = new ArrayList<String>();
		}
		this.photoIdsToAdd.add(photoId);
		if(this.photoIdsToRemove!=null){
			this.photoIdsToRemove.remove(photoId);
		}		
	}

}
