package kidogod3.core.clientchannel.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.appengine.channel.client.Channel;
import com.google.gwt.appengine.channel.client.ChannelFactory;
import com.google.gwt.appengine.channel.client.SocketError;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ChannelListeners {
	
	public static class ChannelCreatedCallbackImpl implements ChannelFactory.ChannelCreatedCallback{

		@Override
		public void onChannelCreated(Channel channel) {
			// TODO Auto-generated method stub
			channel.open(new SocketListener() {
				
				@Override
				public void onOpen() {
					// TODO Auto-generated method stub
					//Window.alert("Channel onOpen");
				}
				
				@Override
				public void onMessage(final String message) {
					// TODO Auto-generated method stub
					for(final ChannelListener listener:listeners){					
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
							
							@Override
							public void execute() {
								listener.onMessage(message);
							}
						});
						
					}

				}
				
				@Override
				public void onError(SocketError error) {
					// TODO Auto-generated method stub
					Window.alert("Channel onError: ("+error.getCode()+") "+error.getDescription());
				}
				
				@Override
				public void onClose() {
					// TODO Auto-generated method stub
					Window.alert("Channel onClose");
				}
			});


		}
		
	}
	private static List<ChannelListener> listeners;
	private static ChannelToken token;
	private static Channel channel;
	private static List<String> hubs = new ArrayList<String>();
	private static void initSocket(String token){
		String t = token;
		ChannelFactory.createChannel(t, new ChannelCreatedCallbackImpl());
	};

	private static void init(){
		
		ClientChannelServiceAsync service = GWT.create(ClientChannelService.class);
		service.createClientChannel(new AsyncCallback<ChannelToken>() {
			
			@Override
			public void onSuccess(ChannelToken result) {				
				initSocket(result.getToken());
				ChannelListeners.token = result;
				doSubscribe();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to create channel: \n\n"+caught.getClass().getName()+"\n"+caught.getMessage());				
			}
		});
	}
	protected static void doSubscribe(){
		ClientChannelServiceAsync service = GWT.create(ClientChannelService.class);
		for(String hubId:hubs){
			service.subscribe(token.getClientId(), hubId, new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Failed to subscribe to a channel\n\n"+caught.getClass().getName()+":\n"+caught.getMessage());
				}
			});			
		}
	}
	public static void subscribe(String hubId){
		hubs.add(hubId);
		if(channel!=null){
			doSubscribe();
		}
	}
	public static void addListener(ChannelListener listener){
		if(listeners==null){
			ChannelListeners.listeners = new ArrayList<ChannelListener>();
		}
		ChannelListeners.listeners.add(listener);
		if(token==null){
			init();
		}
	}
}
