package kidogod3.core.clientchannel.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ClientChannelServiceAsync {

	void broadcast(String message, String hubId, AsyncCallback<Void> callback);

	void createClientChannel(AsyncCallback<ChannelToken> callback);

	void createClientChannel(String clientId,
			AsyncCallback<ChannelToken> callback);

	void subscribe(String clientId, String hubId, AsyncCallback<Void> callback);

}
