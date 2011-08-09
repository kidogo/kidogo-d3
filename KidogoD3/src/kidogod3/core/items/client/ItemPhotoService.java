package kidogod3.core.items.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwt.rpc")
public interface ItemPhotoService extends RemoteService{
	
	String getPhotoUrl(String photoId);
	List<String> getPhotoUrl(List<String> photoId);
}
