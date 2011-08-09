package kidogod3.core.geo;

import java.util.List;

import kidogod3.core.geo.client.LatLong;
import kidogod3.core.geo.client.LatLongBounds;

import com.beoui.utils.BoundingBox;
import com.beoui.utils.DefaultCostFunction;
import com.beoui.utils.Geocell;
import com.beoui.utils.Point;

public class GeoHashServiceImpl implements GeoHashService {

	@Override
	public List<String> buildGeoCells(LatLong latLong){
		Point point = new Point(latLong.getLatitude(), latLong.getLongitude());
		return Geocell.generate_geo_cell(point);
	}

	@Override
	public List<String> buidlBoundingBoxCells(LatLongBounds bounds) {
		double north = bounds.getNorthEast().getLatitude();
		double east = bounds.getNorthEast().getLongitude();
		double south = bounds.getSouthWest().getLatitude();
		double west = bounds.getSouthWest().getLongitude();
		BoundingBox bbox = new BoundingBox(north, east, south, west);
		return Geocell.best_bbox_search_cells(bbox, new DefaultCostFunction());
	}

}
