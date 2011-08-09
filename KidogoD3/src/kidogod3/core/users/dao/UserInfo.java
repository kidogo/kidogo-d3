package kidogod3.core.users.dao;

import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

@PersistenceCapable
public class UserInfo implements StoreCallback{
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String userKey;
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
	private Long userId;
	
	private Date created;
	private Date modified;
	
	private String email;

	private String name;
	
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void jdoPreStore() {
		this.modified = new Date();
		if(JDOHelper.isNew(this)){
			this.created = this.modified;
		}
	}
	
	
}
