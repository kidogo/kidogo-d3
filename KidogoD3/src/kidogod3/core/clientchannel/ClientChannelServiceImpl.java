package kidogod3.core.clientchannel;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.inject.Inject;

import kidogod3.core.clientchannel.client.ChannelToken;
import kidogod3.core.clientchannel.client.ClientChannelService;
import kidogod3.core.clientchannel.dao.Hub;
import kidogod3.core.clientchannel.dao.Subscription;

public class ClientChannelServiceImpl implements ClientChannelService {
	static final ChannelService channelService = ChannelServiceFactory.getChannelService();
	
	PersistenceManager pm;
	
	@Inject
	public ClientChannelServiceImpl(PersistenceManager pm) {
		this.pm = pm;
	}
	
	@Override
	public ChannelToken createClientChannel() {
		String clientId = UUID.randomUUID().toString();
		return createClientChannel(clientId);
	}

	@Override
	public ChannelToken createClientChannel(String clientId) {
		String token = channelService.createChannel(clientId);
		
		ChannelToken channelToken = new ChannelToken();
		channelToken.setClientId(clientId);
		channelToken.setToken(token);
		channelToken.setExpiery(new Date(System.currentTimeMillis()+(1000l*60*60*2)));

		return channelToken;
	}

	@Override
	public void subscribe(String clientId, String hubId) {
		Key hubKey = Hub.createKey(hubId);
		//Key subscriptionKey = Subscription.createKey(clientId, hubId);
		pm.currentTransaction().begin();
		try{
			Hub hub;
			try{
				hub = pm.getObjectById(Hub.class, hubKey);
			}catch(JDOObjectNotFoundException e){
				hub = new Hub(hubId);
				hub = pm.makePersistent(hub);
			}
			
			Subscription subscription = new Subscription(clientId);

			//subscription = pm.makePersistent(subscription);
			hub.addSubscription(subscription);
			
			pm.currentTransaction().commit();
			
		}finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
		}
	}

	@Override
	public void broadcast(String message, String hubId) {
		pm.currentTransaction().begin();
		try{
			
			Key hubKey = Hub.createKey(hubId);
			try{
				Hub hub = pm.getObjectById(Hub.class, hubKey); 
				
				Query q = pm.newQuery(Subscription.class);
				q.setFilter("this.hub==:hub");
				Date threshold = new Date(System.currentTimeMillis()-(1000l*60*60));
				List<Subscription> subscriptions = (List<Subscription>)q.execute(hub);
				
				ChannelService channelService = ChannelServiceFactory.getChannelService();
				
				for(Subscription subscription:subscriptions){
					if(subscription.getUpdated()==null || subscription.getUpdated().before(threshold)){
						continue;
					}
					ChannelMessage msg = new ChannelMessage(subscription.getClientId(), message);

					channelService.sendMessage(msg);
				}
			}catch(JDOObjectNotFoundException e){				
			}			
			pm.currentTransaction().commit();
		}finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
		}
	}

}
