package kidogod3.core.users;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;

import kidogod3.core.users.client.IUser;
import kidogod3.core.users.client.IUserImpl;
import kidogod3.core.users.client.UserService;
import kidogod3.core.users.dao.UserInfo;

public class UserServiceImpl implements UserService {

	com.google.appengine.api.users.UserService us = UserServiceFactory.getUserService();
	
	PersistenceManager pm;
	
	@Inject
	public UserServiceImpl(PersistenceManager pm) {
		this.pm = pm;
	}
	
	@Override
	public IUser getCurrentUser() {
		if(!us.isUserLoggedIn()){
			return null;
		}
		User user = us.getCurrentUser();
		IUserImpl result = new IUserImpl();
		result.setEmail(user.getEmail());
		result.setName(user.getNickname());
		result.setVerified(true);
		
		Query q = pm.newQuery(UserInfo.class);
		q.setOrdering("created");
		q.setFilter("this.email==:email");
		q.setRange(0, 1);
		List<UserInfo> list = (List<UserInfo>)q.execute(user.getEmail());
		
		if(!list.isEmpty()){
			result.setUserId(list.get(0).getUserId());
		}else{
			UserInfo info = new UserInfo();
			info.setEmail(user.getEmail());
			info.setName(user.getNickname());
			pm.currentTransaction().begin();
			try{
				info = pm.makePersistent(info);				
				result.setUserId(info.getUserId());
				pm.currentTransaction().commit();
			}finally{
				if(pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
			}
		}

		return result;
	}

	@Override
	public boolean isLoggedIn() {		
		return us.isUserLoggedIn();
	}

	@Override
	public String createLoginUrl(String destinationURL) {
		return us.createLoginURL(destinationURL);
	}

	@Override
	public String createLogoutUrl(String destinationURL) {
		return us.createLogoutURL(destinationURL);
	}

}
