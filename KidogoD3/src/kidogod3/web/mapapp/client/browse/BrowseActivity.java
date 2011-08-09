package kidogod3.web.mapapp.client.browse;

import kidogod3.core.geo.client.LatLong;
import kidogod3.core.geo.client.LatLongBounds;
import kidogod3.core.items.client.IItem;
import kidogod3.core.items.client.ItemsService;
import kidogod3.core.items.client.ItemsServiceAsync;
import kidogod3.core.persistence.client.QueryResult;
import kidogod3.web.mapapp.client.MapCanvas;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.maps.client.base.HasLatLngBounds;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class BrowseActivity extends AbstractActivity {
		
	MapCanvas mapCanvas;
	BrowseView browseView;
	
	public BrowseActivity(MapCanvas mapCanvas, BrowseView browseView) {
		this.mapCanvas = mapCanvas;
		this.browseView = browseView;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(browseView);
	}

	protected void search(){
		ItemsService.ItemFilter filter = new ItemsService.ItemFilter();
		
		
		updateList(filter);
	}

	public void updateList(ItemsService.ItemFilter filter){
		
	}
}
