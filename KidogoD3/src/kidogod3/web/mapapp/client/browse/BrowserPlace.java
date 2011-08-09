package kidogod3.web.mapapp.client.browse;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class BrowserPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<BrowserPlace> {
	        @Override
	        public String getToken(BrowserPlace place) {
	        	if(place.getUserId()==null){
	        		return null;
	        	}
	            return place.getUserId().toString();
	        }

	        @Override
	        public BrowserPlace getPlace(String token) {
	        	if(token==null || token.trim().length()==0){
	        		return new BrowserPlace(null);
	        	}
	            return new BrowserPlace(Long.parseLong(token));
	        }
	    }
	   
	private Long userId;

	public BrowserPlace(Long userId) {
		this.userId = userId;
	}
	public Long getUserId() {
		return userId;
	}
}
