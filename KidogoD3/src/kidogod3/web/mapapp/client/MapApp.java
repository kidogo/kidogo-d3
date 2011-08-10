package kidogod3.web.mapapp.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import kidogod3.core.geo.client.LatLong;
import kidogod3.core.geo.client.LatLongBounds;
import kidogod3.core.items.client.IItem;
import kidogod3.core.items.client.ItemsService;
import kidogod3.core.items.client.ItemsServiceAsync;
import kidogod3.core.items.client.NewItemBroadcastListener;
import kidogod3.core.items.client.NewItemBroadcastService;
import kidogod3.core.persistence.client.QueryResult;
import kidogod3.core.users.client.IUser;
import kidogod3.core.users.client.UserService;
import kidogod3.core.users.client.UserServiceAsync;
import kidogod3.web.mapapp.client.edit.EditItemForm;
import kidogod3.web.mapapp.client.edit.EditItemFormDialogFactory;
import kidogod3.web.mapapp.client.view.ItemViwerDialogFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.HasLatLngBounds;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.event.Event;
import com.google.gwt.maps.client.event.HasMouseEvent;
import com.google.gwt.maps.client.event.MouseEventCallback;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.impl.MarkerImpl;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
*  GWT entry point for the application.
*/
public class MapApp implements EntryPoint, MapCanvas {

	/** A record or an item with its Marker on the map */
	public class ItemRecord{
		IItem item;
		Marker marker;
		public ItemRecord(IItem item, Marker marker) {
			this.item = item;
			this.marker = marker;
			/*
			Event.addListener(this.marker, "click", new MouseEventCallback() {
				
				@Override
				public void callback(HasMouseEvent event) {
					ItemViwerDialogFactory factory = new ItemViwerDialogFactory(ItemRecord.this.item);
					factory.show();
				}
			});
			*/
			final ItemViwerDialogFactory factory = new ItemViwerDialogFactory(ItemRecord.this.item);
			Event.addListener(this.marker, "mouseover", new MouseEventCallback() {
				
				@Override
				public void callback(HasMouseEvent event) {					
					factory.show();
				}
			});
			Event.addListener(this.marker, "mouseout", new MouseEventCallback() {
				
				@Override
				public void callback(HasMouseEvent event) {					
					factory.hide();
				}
			});			
		}
	}
	
	ItemsServiceAsync service = GWT.create(ItemsService.class);
	static UserServiceAsync userServiceAsync = GWT.create(UserService.class);
	
	private MapWidget mapWidget;
	private FlowPanel sidePanel;
	private IUser currentUser;
	private ItemsService.ItemFilter filter;
	//We keep track of items that have been added already to avoid puting up multiple markers for the same item
	private Map<Long, ItemRecord> displayedItems = new HashMap<Long, MapApp.ItemRecord>();
	private boolean showOnlyMyItems;
	
	private SideBar.Presenter sideBarPresenter = new SideBar.Presenter() {
		
		@Override
		public void signOut() {
			String redirectUrl = Window.Location.createUrlBuilder().buildString();
			userServiceAsync.createLogoutUrl(redirectUrl, new AsyncCallback<String>() {
				
				@Override
				public void onSuccess(String result) {
					Window.Location.assign(result);				
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});		
		}
		
		@Override
		public void signIn() {
			String redirectUrl = Window.Location.getHref();
			userServiceAsync.createLoginUrl(redirectUrl, new AsyncCallback<String>() {
				
				@Override
				public void onSuccess(String result) {
					Window.Location.assign(result);				
				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});

		}
		
		@Override
		public void setShowOnlyUserItems(boolean showOnlyUserItems) {
			MapApp.this.showOnlyMyItems = showOnlyUserItems;
			updateDisplayed();
		}
		
		@Override
		public void promptAddItem() {
			PromptPositionTask task = new PromptPositionTask(mapWidget){

				@Override
				protected void onFinish(HasLatLng position) {
					//final Marker marker = new Marker();
					//marker.setPosition(position);
					//marker.setDraggable(true);
					//marker.setMap(mapWidget.getMap());
					EditItemFormDialogFactory dialogFactory = new EditItemFormDialogFactory(MapApp.this, currentUser.getUserId(), position);
					dialogFactory.show();
				}
				
			};
			task.start();
		}
		
	};

	@Override
	public void onModuleLoad() {
		final MapOptions options = new MapOptions();
	    options.setZoom(6);
	    options.setCenter(new LatLng(-6, 36));
	    options.setMapTypeId(new MapTypeId().getRoadmap());
	    
	    // Enable maps drag feature. Disabled by default.
	    options.setDraggable(true);
	    // Enable and add default navigation control. Disabled by default.
	    options.setNavigationControl(true);
	    // Enable and add map type control. Disabled by default.
	    options.setMapTypeControl(true);
	    options.setScrollwheel(true);

	    int height = 5;
	    int width = 5;
	    
	    sidePanel = new FlowPanel();
	    sidePanel.addStyleName("SidePanel");
	    sidePanel.getElement().getStyle().setPosition(Position.ABSOLUTE);
	    sidePanel.getElement().getStyle().setRight(0, Unit.PX);
	    sidePanel.getElement().getStyle().setTop(0, Unit.PX);
	    sidePanel.getElement().getStyle().setWidth(200, Unit.PX);
	    sidePanel.getElement().getStyle().setBottom(0, Unit.PX);
	    
	    mapWidget = new MapWidget(options);	    
	    //mapWidget.setSize("800px", "600px");
	    mapWidget.getElement().getStyle().setPosition(Position.ABSOLUTE);
	    mapWidget.getElement().getStyle().setLeft(0, Unit.PX);
	    mapWidget.getElement().getStyle().setTop(0, Unit.PX);
	    mapWidget.getElement().getStyle().setRight(200, Unit.PX);
	    mapWidget.getElement().getStyle().setBottom(0, Unit.PX);

	    RootPanel.get().add(mapWidget);
	    RootPanel.get().add(sidePanel);
		
	    
	   	Event.addListener(mapWidget.getMap(), "tilesloaded", new MouseEventCallback() {

			@Override
			public void callback(HasMouseEvent event) {
				//Window.alert(event.getLatLng().toString());
				rebuildMap();
			}
		});
	   	Event.addListener(mapWidget.getMap(), "bounds_changed", new MouseEventCallback() {

			@Override
			public void callback(HasMouseEvent event) {
				//Window.alert(event.getLatLng().toString());
				rebuildMap();
			}
		});
	   	
	   	UserServiceAsync userService = GWT.create(UserService.class);
	   	sidePanel.add(new HTML("Loading ..."));
	   	userService.getCurrentUser(new AsyncCallback<IUser>() {
			
			@Override
			public void onSuccess(IUser result) {
				// TODO Auto-generated method stub
				currentUser = result;
				sidePanel.clear();
				if(result==null){
					sidePanel.add(new GuestSideBar(sideBarPresenter));					
				}else{
					UserSideBar sideBar = new UserSideBar(sideBarPresenter);
					sideBar.setUser(result);
					sidePanel.add(sideBar);
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				sidePanel.clear();
				sidePanel.add(new HTML("Loading failed: "+caught.getClass().getName()+": "+caught.getMessage()));
			}
		});
	   	
	   	NewItemBroadcastService.addListener(new NewItemBroadcastListener() {
			
			@Override
			public void onNewItem(final IItem item) {
				final FlowPanel p = new FlowPanel();
				RootPanel.get().add(p);
				p.getElement().getStyle().setBackgroundColor("red");
				p.getElement().getStyle().setPosition(Position.ABSOLUTE);
				p.getElement().getStyle().setTop(0, Unit.PX);
				p.getElement().getStyle().setLeft(0, Unit.PX);
				p.getElement().getStyle().setRight(0, Unit.PX);
				p.getElement().getStyle().setBottom(0, Unit.PX);
				
				Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand() {

					@Override
					public boolean execute() {
						int left = p.getAbsoluteLeft();
						int top = p.getAbsoluteTop();
						
						left--;
						top--;
						if(left<0) left = 0;
						if(top<0) top = 0;
						
						p.getElement().getStyle().setTop(top, Unit.PX);
						p.getElement().getStyle().setLeft(left, Unit.PX);
						p.getElement().getStyle().setRight(left, Unit.PX);
						p.getElement().getStyle().setBottom(top, Unit.PX);						
						
						if(left<=0 || top <= 0){
							p.removeFromParent();
							return false;
						}
						
						return true;
					}
				});
				addItem(item);
			}
		});
	}
	protected boolean canDisplay(IItem item){
		if(!showOnlyMyItems){
			return true;
		}
		if(item.getEmail()==null){
			return true;					
		}
		if(this.currentUser==null){
			return true;
		}
		return item.getEmail().equals(this.currentUser.getEmail());
	}
	protected void updateDisplayed(){
		for(Entry<Long, ItemRecord> item:displayedItems.entrySet()){
			if(canDisplay(item.getValue().item)){
				item.getValue().marker.setMap(mapWidget.getMap());	
			}else{
				MarkerImpl.impl.setMap(item.getValue().marker.getJso(), null);
			}
		}
	}
	@Override
	public void addItem(IItem item){
		ItemRecord record = displayedItems.get(item.getItemId());
		if(record!=null){
			MarkerImpl.impl.setMap(record.marker.getJso(), null);			
		}
		
		Marker marker = new Marker();
		LatLng position = new LatLng(item.getLatLong().getLatitude(), item.getLatLong().getLongitude());
		
		record = new ItemRecord(item, marker);
		
		record.marker.setPosition(position);
		displayedItems.put(item.getItemId(), record);
		
		if(canDisplay(item)){
			record.marker.setMap(mapWidget.getMap());	
		}		
	}
	
	
	
	protected void rebuildMap(){
		if(filter==null){
			filter = new ItemsService.ItemFilter();
		}
		HasLatLngBounds mapBounds = this.getMapWidget().getMap().getBounds();
		
		LatLong northEast = new LatLong(mapBounds.getNorthEast().getLatitude(), mapBounds.getNorthEast().getLongitude());
		LatLong southWest = new LatLong(mapBounds.getSouthWest().getLatitude(), mapBounds.getSouthWest().getLongitude());
		filter.setBounds(new LatLongBounds(northEast, southWest));

		service.listItems(filter, new AsyncCallback<QueryResult<IItem>>() {
			
			@Override
			public void onSuccess(QueryResult<IItem> result) {
				for(IItem item:result.getList()){
					addItem(item);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getClass().getName()+": "+caught.getMessage());
			}
		});
	}
	public void setFilter(ItemsService.ItemFilter filter) {
		this.filter = filter;
		rebuildMap();
	}
	public MapWidget getMapWidget() {
		return mapWidget;
	}

}
