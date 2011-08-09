package kidogod3.core.geo.client;

import java.io.Serializable;

public class LatLong implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6402078438509427988L;

	private static int floatToIntBits(double f){
		return (int)Math.round((f*100000000));
	}
	private double latitude;
	private double longitude;
	
	protected LatLong() {
	}
	public LatLong(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + floatToIntBits(latitude);
		result = prime * result + floatToIntBits(longitude);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LatLong other = (LatLong) obj;
		if (floatToIntBits(latitude) != 
				floatToIntBits(other.latitude))
			return false;
		if (floatToIntBits(longitude) != 
				floatToIntBits(other.longitude))
			return false;
		return true;
	}
	
}