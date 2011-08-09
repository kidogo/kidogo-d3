
package kidogod3.core.items.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import kidogod3.core.geo.client.LatLong;
import kidogod3.core.geo.client.LatLongBounds;
import kidogod3.core.persistence.client.QueryFilter;
import kidogod3.core.persistence.client.QueryResult;

@RemoteServiceRelativePath("gwt.rpc")
public interface ItemsService extends RemoteService {

	/**
	 * Options used to create or update an item
	 * 
	 * @author Arnold P. Minde
	 *
	 */
	public interface ItemOptions extends Serializable{
		Long getItemId();
		String getTitle();
		String getDescription();
		List<String> getTagsToRemove();
		List<String> getTagsToAdd();
		LatLong getLatLong();
		String getCurrencyCode();
		BigDecimal getPrice();
		List<String> getPhotoIdsToRemove();
		List<String> getPhotoIdsToAdd();
	}
	
	/**
	 * Filter used when searching for items
	 * 
	 * @author Arnold P. Minde
	 *
	 */
	public static class ItemFilter extends QueryFilter{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2204570715383542830L;
		private String text;
		private Long userId;
		private LatLongBounds bounds;

		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public LatLongBounds getBounds() {
			return bounds;
		}
		public void setBounds(LatLongBounds bounds) {
			this.bounds = bounds;
		}
		
	}

	IItem saveItem(long userId, ItemOptions options);
	IItem getItem(long itemId);

	QueryResult<IItem> listItems(ItemFilter filter);
}