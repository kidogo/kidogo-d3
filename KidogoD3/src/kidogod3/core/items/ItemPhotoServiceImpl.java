package kidogod3.core.items;

import java.util.ArrayList;
import java.util.List;

import kidogod3.core.items.client.ItemPhotoService;

public class ItemPhotoServiceImpl implements ItemPhotoService {

	@Override
	public String getPhotoUrl(String photoId) {		
		return ItemPictureServlet.PHOTO_URL+"?id="+photoId;
	}

	@Override
	public List<String> getPhotoUrl(List<String> photoId) {
		List<String> result = new ArrayList<String>();
		for(String id:photoId){
			result.add(getPhotoUrl(id));
		}
		return result;
	}

}
