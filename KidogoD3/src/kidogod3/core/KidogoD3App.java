package kidogod3.core;

import kidogod3.core.clientchannel.ClientChannelModule;
import kidogod3.core.geo.GeoModule;
import kidogod3.core.items.ItemsModule;
import kidogod3.core.persistence.PersistenceModule;
import kidogod3.core.users.UsersModule;
import kidogod3.utils.gwt.GwtSupportModule;

import com.google.inject.AbstractModule;

public class KidogoD3App extends AbstractModule {

	@Override
	protected void configure() {

		binder().install(new PersistenceModule());
					
		binder().install(new GwtSupportModule());
		
		binder().install(new ClientChannelModule());
		
		binder().install(new GeoModule());		
		binder().install(new ItemsModule());
		binder().install(new UsersModule());
		
		//binder().install(new TestServletModule());
	}

}
