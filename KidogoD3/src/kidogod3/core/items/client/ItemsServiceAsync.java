package kidogod3.core.items.client;

import kidogod3.core.items.client.ItemsService.ItemFilter;
import kidogod3.core.items.client.ItemsService.ItemOptions;
import kidogod3.core.persistence.client.QueryResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ItemsServiceAsync {

	void getItem(long itemId, AsyncCallback<IItem> callback);

	void listItems(ItemFilter filter, AsyncCallback<QueryResult<IItem>> callback);

	void saveItem(long userId, ItemOptions options, AsyncCallback<IItem> callback);

}
