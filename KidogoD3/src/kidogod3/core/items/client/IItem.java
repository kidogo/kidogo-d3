package kidogod3.core.items.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import kidogod3.core.geo.client.LatLong;


public interface IItem extends Serializable{
	Long getItemId();
	String getTitle();
	String getDescription();
	List<String> getTags();
	Date getPostDate();
	
	LatLong getLatLong();
	
	String getCurrencyCode();
	BigDecimal getPrice();
	
	List<String> getPhotoIds();
	List<String> getPhotoUrls();
	
	String getOwnerName();
	String getPhoneNumber();
	String getEmail();
}