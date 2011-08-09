package kidogod3.core.clientchannel.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("gwt.rpc")
public interface ClientChannelService extends RemoteService{
	/**
	 * Creates a new client id and a new token
	 * 
	 * @return
	 */
	
	ChannelToken createClientChannel();
	/**
	 * Create a token for the existing client
	 * @param clientId
	 * @return
	 */
	ChannelToken createClientChannel(String clientId);
	
	/**
	 * Subscribes the client to a hub, so that messages sent to the hub are forwarded to the client 
	 * 
	 * @param clientId
	 * @param hubId
	 */
	void subscribe(String clientId, String hubId);
	
	/**
	 * Broadcast a message to the hub. The message should be forwarded to all clients subscribed
	 * 
	 * @param message
	 * @param hubId
	 */
	void broadcast(String message, String hubId);
}
