package kidogod3.web.mapapp.client;

import kidogod3.core.items.client.IItem;

import com.google.gwt.maps.client.MapWidget;

public interface MapCanvas {
	MapWidget getMapWidget();
	void addItem(IItem item);
}
