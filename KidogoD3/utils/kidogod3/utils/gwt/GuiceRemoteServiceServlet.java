package kidogod3.utils.gwt;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class GuiceRemoteServiceServlet extends DelegatingRemoteServiceServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6904222490652730486L;

	Provider<Injector> injectorProvider;

	@Inject
	public GuiceRemoteServiceServlet(Provider<Injector> injectorProvider){
		this.injectorProvider = injectorProvider;
	}
	
	@Override
	protected Object getDelegate(Class<?> serviceClass){
		Key<?> key = Key.get(serviceClass, RemoteServiceHandler.class);
		Object delegate = injectorProvider.get().getInstance(key);
		return delegate;
	}

}
