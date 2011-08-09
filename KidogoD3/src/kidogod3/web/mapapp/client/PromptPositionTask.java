package kidogod3.web.mapapp.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.event.Event;
import com.google.gwt.maps.client.event.HasMapsEventListener;
import com.google.gwt.maps.client.event.HasMouseEvent;
import com.google.gwt.maps.client.event.MouseEventCallback;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.impl.MarkerImpl;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

public abstract class PromptPositionTask {
	
	private MapWidget mapWidget;
	private HasMapsEventListener mouseMoveListener;
	private HasMapsEventListener mouseClickListener;
	private Marker marker;
	private HandlerRegistration nativePreviewHandler;
	
	private MouseEventCallback mouseMove = new MouseEventCallback(){

		@Override
		public void callback(HasMouseEvent event) {
			if(marker==null){
				marker = new Marker();
				marker.setPosition(event.getLatLng());
				marker.setMap(mapWidget.getMap());
				mouseClickListener = Event.addListener(marker, "click", mouseClick);
			}else{
				marker.setPosition(event.getLatLng());
			}
		}
		
	};

	private MouseEventCallback mouseClick = new MouseEventCallback(){

		@Override
		public void callback(HasMouseEvent event) {
			finish(event.getLatLng());			
		}
		
	};
	
	public PromptPositionTask(MapWidget mapWidget) {
		this.mapWidget = mapWidget;
	}
	
	public void start(){
		mouseMoveListener = Event.addListener(mapWidget.getMap(), "mousemove", mouseMove);
		mouseClickListener = Event.addListener(mapWidget.getMap(), "click", mouseClick);
		nativePreviewHandler = com.google.gwt.user.client.Event.addNativePreviewHandler(new NativePreviewHandler() {
			
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				if(event.getNativeEvent().getKeyCode()==KeyCodes.KEY_ESCAPE){
					stop();
				}
				
			}
		});
	}
	
	public void stop(){
		Event.removeListener(mouseMoveListener);
		Event.removeListener(mouseClickListener);
		nativePreviewHandler.removeHandler();
		if(marker!=null){
			//marker.setMap(null);
			MarkerImpl.impl.setMap(marker.getJso(), null);			
		}
	}
	
	protected void finish(HasLatLng position){
		stop();		
		onFinish(position);
	}
	
	protected abstract void onFinish(HasLatLng position);
}
