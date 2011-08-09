package kidogod3.core.geo.client;

import java.io.Serializable;


public class LatLongBounds implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6781204811152513309L;
	private LatLong northEast;
	private LatLong southWest;
	
	public LatLongBounds(LatLong northEast, LatLong sounthWest) {
		this.northEast = northEast;
		this.southWest = sounthWest;
	}
	protected LatLongBounds() {
		// TODO Auto-generated constructor stub
	}

	public LatLong getNorthEast() {
		return northEast;
	}
	
	public LatLong getSouthWest() {
		return southWest;
	}
}