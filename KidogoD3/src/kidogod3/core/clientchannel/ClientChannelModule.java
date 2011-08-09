package kidogod3.core.clientchannel;

import kidogod3.core.clientchannel.client.ClientChannelService;
import kidogod3.utils.gwt.RemoteServiceHandler;

import com.google.inject.AbstractModule;

public class ClientChannelModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ClientChannelService.class).to(ClientChannelServiceImpl.class);
		bind(ClientChannelService.class).annotatedWith(RemoteServiceHandler.class).to(ClientChannelServiceImpl.class);

	}

}
