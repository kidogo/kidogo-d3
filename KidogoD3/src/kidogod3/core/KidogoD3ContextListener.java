package kidogod3.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class KidogoD3ContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		KidogoD3App module = new KidogoD3App();
		
		Injector injector = Guice.createInjector(module);
		return injector;
	}

}
