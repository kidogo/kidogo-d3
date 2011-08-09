package kidogod3.core.users.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwt.rpc")
public interface UserService extends RemoteService {
	IUser getCurrentUser();
	boolean isLoggedIn();
	String createLoginUrl(String redirectUrl);
	String createLogoutUrl(String redirectUrl);
}