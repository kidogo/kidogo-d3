package kidogod3.core.clientchannel.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class Hub {
	
	public static Key createKey(String hubId){
		return KeyFactory.createKey(Hub.class.getSimpleName(), hubId);
	}
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String hubKey;
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String hubId;
	
	@Persistent(mappedBy="hub")
	private List<Subscription> subscriptions;
	
	public Hub(String hubId){
		this.hubId =  hubId;
	}

	public String getHubKey() {
		return hubKey;
	}

	public void setHubKey(String hubKey) {
		this.hubKey = hubKey;
	}

	public String getHubId() {
		return hubId;
	}

	public void setHubId(String hubId) {
		this.hubId = hubId;
	}

	public List<Subscription> getSubscriptions() {
		if(this.subscriptions==null){
			this.subscriptions = new ArrayList<Subscription>();
		}
		return subscriptions;
	}
	public void addSubscription(Subscription subscription){
		if(this.subscriptions==null){
			this.subscriptions = new ArrayList<Subscription>();
		}
		this.subscriptions.add(subscription);
	}
	
}
