package kidogod3.utils.gwt;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;

public class GwtSupportModule extends AbstractModule{

	@Override
	protected void configure() {
		binder().install(new ServletModule(){
			@Override
			protected void configureServlets() {
				//serveRegex("(.)*gwt\\.rpc").with(GuiceRemoteServiceServlet.class);				
				//serveRegex("/(.)*gwt\\.rpc").with(GuiceRemoteServiceServlet.class);
				//serve("/MapApp/gwt.rpc").with(GuiceRemoteServiceServlet.class);
				serve("*.rpc").with(GuiceRemoteServiceServlet.class);
			}
		});
		
	}

}
