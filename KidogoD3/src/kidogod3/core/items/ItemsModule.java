package kidogod3.core.items;

import kidogod3.core.items.client.ItemPhotoService;
import kidogod3.core.items.client.ItemsService;
import kidogod3.utils.gwt.RemoteServiceHandler;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;

public class ItemsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ItemsService.class).to(ItemsServiceImpl.class);
		bind(ItemsService.class).annotatedWith(RemoteServiceHandler.class).to(ItemsServiceImpl.class);
		bind(ItemPhotoService.class).to(ItemPhotoServiceImpl.class);
		bind(ItemPhotoService.class).annotatedWith(RemoteServiceHandler.class).to(ItemPhotoServiceImpl.class);
		binder().install(new ServletModule(){
			@Override
			protected void configureServlets() {
				serve(ItemPictureServlet.PHOTO_URL).with(ItemPictureServlet.class);
			}
			
		});
	}

}
