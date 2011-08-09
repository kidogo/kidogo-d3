package kidogod3.core.geo;

import com.google.inject.AbstractModule;

public class GeoModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GeoHashService.class).to(GeoHashServiceImpl.class);		
	}

}
