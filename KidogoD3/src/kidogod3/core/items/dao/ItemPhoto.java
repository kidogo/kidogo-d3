package kidogod3.core.items.dao;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable
public class ItemPhoto {

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String photoKey;
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
	private Long photoId;
	
	private String name;
	private Blob photo;

	public String getPhotoKey() {
		return photoKey;
	}

	public void setPhotoKey(String photoKey) {
		this.photoKey = photoKey;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Blob getPhoto() {
		return photo;
	}

	public void setPhoto(Blob blob) {
		this.photo = blob;
	}
	
	
}
