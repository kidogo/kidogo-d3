package kidogod3.core.geo;

import java.util.List;

import kidogod3.core.geo.client.LatLong;
import kidogod3.core.geo.client.LatLongBounds;

public interface GeoHashService {
	List<String> buildGeoCells(LatLong latLong);
	List<String> buidlBoundingBoxCells(LatLongBounds bounds);
}
