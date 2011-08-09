package kidogod3.core.clientchannel.dao;

import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class Subscription implements StoreCallback{

	public static Key createKey(String clientId, String hubId){
		Key parent = Hub.createKey(hubId);
		return KeyFactory.createKey(parent, Subscription.class.getSimpleName(), clientId+"-"+hubId);
	}
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String subscriptionKey;
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String subscriptionId;

	private Hub hub;
	
	private String clientId;
	
	private Date created;
	private Date updated;
	
	public Subscription(String clientId){
		this.clientId = clientId;
		this.subscriptionId = clientId;
	}
	public String getSubscriptionKey() {
		return subscriptionKey;
	}
	public void setSubscriptionKey(String subscriptionKey) {
		this.subscriptionKey = subscriptionKey;
	}
	public String getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	@Override
	public void jdoPreStore() {
		this.updated = new Date();
		if(JDOHelper.isNew(this)){
			this.created = this.updated;
		}
			
		
	}
	
	
}
