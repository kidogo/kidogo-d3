package kidogod3.core.users;

import kidogod3.core.users.client.UserService;
import kidogod3.utils.gwt.RemoteServiceHandler;

import com.google.inject.AbstractModule;

public class UsersModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserService.class).to(UserServiceImpl.class);
		bind(UserService.class).annotatedWith(RemoteServiceHandler.class).to(UserServiceImpl.class);
	}

}
