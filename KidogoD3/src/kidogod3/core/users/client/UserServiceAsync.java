package kidogod3.core.users.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void createLoginUrl(String redirectUrl, AsyncCallback<String> callback);

	void createLogoutUrl(String redirectUrl, AsyncCallback<String> callback);

	void getCurrentUser(AsyncCallback<IUser> callback);

	void isLoggedIn(AsyncCallback<Boolean> callback);

}
