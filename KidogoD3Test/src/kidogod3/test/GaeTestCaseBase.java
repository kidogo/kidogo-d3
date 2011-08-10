package kidogod3.test;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;

public class GaeTestCaseBase {

	protected Environment env;
    private LocalServiceTestHelper helper;
    private boolean autoTaskExecutionEnabled;
    
    public GaeTestCaseBase(){
    }
    public GaeTestCaseBase(boolean enableAutoTaskExecution){
    	this.autoTaskExecutionEnabled = enableAutoTaskExecution;
    }

    @Before
	public void gaeTestCaseBaseSetUp(){

        // TaskQueue setup
        LocalTaskQueueTestConfig tqConfig = new LocalTaskQueueTestConfig();
        tqConfig.setDisableAutoTaskExecution(!autoTaskExecutionEnabled);

        //tqConfig.setQueueXmlPath("war/WEB-INF/queue.xml");
        tqConfig.setQueueXmlPath("C:/git/DarhackAthonD3/KidogoD3/war/WEB-INF/queue.xml");
        
        // memcache setup
        LocalMemcacheServiceTestConfig mcConfig = new LocalMemcacheServiceTestConfig();


        // DataStore setup
        LocalDatastoreServiceTestConfig dsConfig = new LocalDatastoreServiceTestConfig();
        dsConfig.setNoIndexAutoGen(false);
        //dsConfig.setBackingStoreLocation("C:/code/eclipse/taxman/SafariYetu/war/WEB-INF/appengine-generated/local_db_test.bin");
        //dsConfig.setBackingStoreLocation("C:\\local_db.bin");
        // this needs to be false to persist, but in unit tests, why do so?
        dsConfig.setNoStorage(false);

        helper = new LocalServiceTestHelper(dsConfig, tqConfig, mcConfig);

        helper.setUp();
        this.env = ApiProxy.getCurrentEnvironment();
        
        //PMF.get().removeInstanceLifecycleListener(EntityHistoryManager.getInstance());
        
	}
	
	@After
	public void gaeTestCaseBaseTearDown(){
        helper.tearDown();
	}

}
