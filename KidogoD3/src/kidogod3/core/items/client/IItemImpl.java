package kidogod3.core.items.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kidogod3.core.geo.client.LatLong;

public class IItemImpl implements IItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4647258478584253646L;
	private Long itemId;
	private String itemTitle;
	private String description;
	private List<String> tags;
	private Date postDate;
	private LatLong latLong;
	private String currencyCode;
	private BigDecimal price;
	private List<String> photoIds;
	private List<String> photoUrls;
	private String ownerName;
	private String phoneNumber;
	private String email;
	
	@Override
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	@Override
	public String getTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	@Override
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public List<String> getTags() {
		if(this.tags==null){
			this.tags = new ArrayList<String>();
		}	
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
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
	public List<String> getPhotoIds() {
		return photoIds;
	}
	public void setPhotoIds(List<String> photoIds) {
		if(this.photoIds==null){
			this.photoIds = new ArrayList<String>();
		}
		this.photoIds = photoIds;
	}
	@Override
	public List<String> getPhotoUrls() {
		if(this.photoUrls==null){
			this.photoUrls = new ArrayList<String>();
		}
		return photoUrls;
	}
	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}
	@Override
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
