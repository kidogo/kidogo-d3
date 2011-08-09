package kidogod3.core.items.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

import kidogod3.core.clientchannel.client.ChannelListener;
import kidogod3.core.clientchannel.client.ChannelListeners;
import kidogod3.core.clientchannel.client.ClientChannelService;
import kidogod3.core.clientchannel.client.ClientChannelServiceAsync;

public class NewItemBroadcastService {
	static ItemsServiceAsync itemsService = GWT.create(ItemsService.class);
	
	public interface INewItemNotification{
		Long getItemId();
		void setItemId(Long itemId);
	}
	public interface NotificationFactory extends AutoBeanFactory{
		AutoBean<INewItemNotification> newItemNotification();
	}
	
	public static class NewItemMsg extends JavaScriptObject{
		protected NewItemMsg() {
		}
		public final native int getItemId()/*-{ return this.itemId; }-*/;
		public final native void setItemId(int itemId)/*-{ this.itemId = itemId; }-*/;
		
		public static native NewItemMsg fromJson(String json)/*-{
			return $wnd.eval(json);
		}-*/;
	}
	
	private static List<NewItemBroadcastListener> listeners = new ArrayList<NewItemBroadcastListener>();
	private static ChannelListener channelListener;
	
	public static void addListener(NewItemBroadcastListener listener){
		listeners.add(listener);
		if(channelListener==null){
			channelListener = new ChannelListener() {
				
				@Override
				public void onMessage(String message) {
					NotificationFactory factory = GWT.create(NotificationFactory.class);
					
					AutoBean<INewItemNotification> notification = AutoBeanCodex.decode(factory, INewItemNotification.class, message);
					handleMessage(notification.as());
				}
			};
			ChannelListeners.subscribe("newitem");
			ChannelListeners.addListener(channelListener);
		}
	}
	
	public static void broadcast(IItem item){
		NotificationFactory factory = GWT.create(NotificationFactory.class);
		AutoBean<INewItemNotification> notification = factory.newItemNotification();
		notification.as().setItemId(item.getItemId());
		
		String msg = AutoBeanCodex.encode(notification).getPayload();
		
		ClientChannelServiceAsync channel = GWT.create(ClientChannelService.class);
		channel.broadcast(msg, "newitem", new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to send new item broadcast\n\n"+caught.getClass().getName()+":\n"+caught.getMessage());
				
			}
		});
	}
	
	protected static void handleMessage(final INewItemNotification notification){
		itemsService.getItem(notification.getItemId(), new AsyncCallback<IItem>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Long itemId = notification.getItemId();
				Window.alert("Received item notification, but failed to fetch it from server (id="+itemId+"): \n\n"+caught.getClass().getName()+"\n"+caught.getMessage());
			}

			@Override
			public void onSuccess(IItem result) {
				for(NewItemBroadcastListener listner:listeners){
					listner.onNewItem(result);
				}
				
			}
		});

	}
}