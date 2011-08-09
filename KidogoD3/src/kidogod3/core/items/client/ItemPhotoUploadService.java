package kidogod3.core.items.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ItemPhotoUploadService {
	public static final String PHOTO_URL = "/photo";
	
	public static class UploadRecord{
		private String photoId;
		private String photoUrl;
		public String getPhotoId() {
			return photoId;
		}
		public void setPhotoId(String photoId) {
			this.photoId = photoId;
		}
		public String getPhotoUrl() {
			return photoUrl;
		}
		public void setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
		}
		
	}
	
	void promptUpload(AsyncCallback<List<UploadRecord>> uploads);
}
