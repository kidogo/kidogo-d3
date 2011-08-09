package kidogod3.core.items.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ItemPhotoServiceAsync {

	void getPhotoUrl(String photoId, AsyncCallback<String> callback);

	void getPhotoUrl(List<String> photoId, AsyncCallback<List<String>> callback);

}
