package kidogod3.core.items;

import java.math.BigDecimal;
import java.util.Date;
import kidogod3.core.items.client.ItemPhotoService;
import kidogod3.core.items.client.ItemsService.ItemFilter;
import kidogod3.core.items.client.ItemsService.ItemOptions;
import kidogod3.core.users.UserServiceImpl;
import kidogod3.core.persistence.client.QueryResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.jdo.PersistenceManager;
import org.datanucleus.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.jdo.JDOPersistenceManagerProxy;
import org.easymock.EasyMock;

import kidogod3.core.geo.GeoHashServiceImpl;
import kidogod3.core.items.client.IItem;
import kidogod3.core.users.client.IUser;
import kidogod3.core.users.client.UserService;
import kidogod3.core.geo.GeoHashService;
import kidogod3.core.geo.client.LatLong;
import kidogod3.core.geo.client.LatLongBounds;

import org.junit.*;
import static org.junit.Assert.*;
import com.google.appengine.api.datastore.GeoPt;
import kidogod3.core.items.dao.Item;
import kidogod3.core.items.client.ItemsService;
import kidogod3.test.GaeTestCaseBase;
import kidogod3.test.PMF;

/**
 * The class <code>ItemsServiceImplTest</code> contains tests for the class <code>{@link ItemsServiceImpl}</code>.
 *
 * @generatedBy CodePro at 07/08/11 12:58
 * @author Arnold P. Minde
 * @version $Revision: 1.0 $
 */
public class ItemsServiceImplTest extends GaeTestCaseBase{
	
	PersistenceManager pm;
	
	protected ItemsServiceImpl createFixture(){
		UserService userService = EasyMock.createMock(UserService.class);
		GeoHashService geoCellsService = EasyMock.createMock(GeoHashService.class);
		ItemPhotoService itemPhotoService = EasyMock.createMock(ItemPhotoService.class);
		ItemsServiceImpl fixture = new ItemsServiceImpl(this.pm, userService, geoCellsService, itemPhotoService);
		return fixture;
	}
	protected Item createItem(){
		Long itemId = 1l;		
		String title = "title";
		String description = "title";
		GeoPt point = new GeoPt(1.0f, 1.0f);
		String email = "email";
		String currencyCode = "TZS";
		BigDecimal price = new BigDecimal(1.0);
		String phoneNumebr = "+255...";
		List<String> photoIds = Arrays.asList("1","2");
		Date created = new Date();
		String userName = "dddd";
		List<String> tags = Arrays.asList("computer","used");
		
		Item item = new Item();
		item.setItemId(itemId);
		item.setTitle(title);
		item.setDescription(description);
		item.setEmail(email);
		item.setPoint(point);
		item.setCurrencyCode(currencyCode);
		item.setPrice(price);
		item.setPhoneNumber(phoneNumebr);
		item.setPhotoIds(photoIds);
		item.setItemId(new Long(1L));
		item.setCreated(created);
		item.setUserName(userName);
		item.setTags(tags);

		return item;
	}
	
	/**
	 * Run the IItem convert(Item) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 07/08/11 12:58
	 */
	@Test
	public void testConvert_1()
		throws Exception {
		ItemsServiceImpl fixture = createFixture();
		fixture.geoCellsService = new GeoHashServiceImpl();
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("1")).andReturn("/photo?id=1");
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("2")).andReturn("/photo?id=2");
		EasyMock.replay(fixture.itemPhotoService);

		Long itemId = 1l;		
		String title = "title";
		String description = "title";
		GeoPt point = new GeoPt(1.0f, 1.0f);
		String email = "email";
		String currencyCode = "TZS";
		BigDecimal price = new BigDecimal(1.0);
		String phoneNumebr = "+255...";
		List<String> photoIds = Arrays.asList("1","2");
		Date created = new Date();
		String userName = "dddd";
		List<String> tags = Arrays.asList("computer","used");
		
		Item item = new Item();
		item.setTitle(title);
		item.setDescription(description);
		item.setEmail(email);
		item.setPoint(point);
		item.setCurrencyCode(currencyCode);
		item.setPrice(price);
		item.setPhoneNumber(phoneNumebr);
		item.setPhotoIds(photoIds);
		item.setItemId(new Long(1L));
		item.setCreated(created);
		item.setUserName(userName);
		item.setTags(tags);

		IItem result = fixture.convert(item);

		// add additional test code here
		assertNotNull(result);
		assertEquals(description, result.getDescription());
		assertEquals(title, result.getTitle());
		assertEquals(currencyCode, result.getCurrencyCode());
		assertEquals(email, result.getEmail());
		assertEquals(itemId, result.getItemId());
		assertEquals(phoneNumebr, result.getPhoneNumber());
		assertEquals(userName, result.getOwnerName());
		assertEquals(photoIds, result.getPhotoIds());
		assertEquals(Arrays.asList("/photo?id=1", "/photo?id=2"), result.getPhotoUrls());
		assertEquals(tags, result.getTags());
	}

	/**
	 * Run the IItem getItem(long) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 07/08/11 12:58
	 */
	@Test
	public void testGetItem_1()
		throws Exception {
		ItemsServiceImpl fixture = new ItemsServiceImpl(null, null, null, null);
		fixture.userService = new UserServiceImpl(null);
		fixture.pm = this.pm;
		fixture.geoCellsService = new GeoHashServiceImpl();
		fixture.itemPhotoService = EasyMock.createMock(ItemPhotoService.class);
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("1")).andReturn("/photo?id=1");
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("2")).andReturn("/photo?id=2");
		EasyMock.replay(fixture.itemPhotoService);

		Long itemId = 1L;
		Item item = createItem();
		item.setItemId(itemId);
		item = pm.makePersistent(item);

		IItem result = fixture.getItem(itemId);
		assertNotNull(result);
		assertEquals(itemId, result.getItemId());
	}

	@Test
	public void saveItem(){
		long userId = 1l;
		
		IUser user = EasyMock.createMock(IUser.class);
		EasyMock.expect(user.getUserId()).andReturn(1l);
		EasyMock.expect(user.getEmail()).andReturn("email@domain").anyTimes();
		EasyMock.expect(user.getName()).andReturn("Name Name").anyTimes();
		
		ItemsServiceImpl fixture = new ItemsServiceImpl(null, null, null, null);
		fixture.userService = EasyMock.createMock(UserService.class);
		EasyMock.expect(fixture.userService.isLoggedIn()).andReturn(true).anyTimes();
		EasyMock.expect(fixture.userService.getCurrentUser()).andReturn(user).anyTimes();
		EasyMock.replay(fixture.userService, user);
		
		fixture.pm = this.pm;
		fixture.geoCellsService = new GeoHashServiceImpl();
		fixture.itemPhotoService = EasyMock.createMock(ItemPhotoService.class);
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("1")).andReturn("/photo?id=1");
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("2")).andReturn("/photo?id=2");
		EasyMock.replay(fixture.itemPhotoService);
		
		final long itemId = 1l;
		final String itemTitle = "title";
		final String description = "description";
		String currencyCode = "TZS";
		BigDecimal price = new BigDecimal("1");
		
		ItemOptions options = EasyMock.createMock(ItemOptions.class);
		EasyMock.expect(options.getItemId()).andReturn(null).anyTimes();
		EasyMock.expect(options.getTitle()).andReturn(itemTitle).anyTimes();
		EasyMock.expect(options.getCurrencyCode()).andReturn(currencyCode).anyTimes();
		EasyMock.expect(options.getDescription()).andReturn(description).anyTimes();
		EasyMock.expect(options.getLatLong()).andReturn(new LatLong(0, 0)).anyTimes();
		EasyMock.expect(options.getPhotoIdsToAdd()).andReturn(Arrays.asList("1","2")).anyTimes();
		EasyMock.expect(options.getPhotoIdsToRemove()).andReturn(null).anyTimes();
		EasyMock.expect(options.getPrice()).andReturn(price).anyTimes();
		EasyMock.expect(options.getTagsToAdd()).andReturn(Arrays.asList("computer","used")).anyTimes();
		EasyMock.expect(options.getTagsToRemove()).andReturn(null).anyTimes();
		EasyMock.replay(options);

		IItem result = fixture.saveItem(userId, options);
		assertNotNull(result);
		assertEquals(options.getTitle(), result.getTitle());
		assertEquals(options.getCurrencyCode(), result.getCurrencyCode());
		assertEquals(options.getDescription(), result.getDescription());
		assertEquals(options.getLatLong(), result.getLatLong());
		assertEquals(options.getPhotoIdsToAdd(), result.getPhotoIds());
		assertEquals(options.getPrice(), result.getPrice());
		assertEquals(options.getTagsToAdd(), result.getTags());
	}
	
	@Test
	public void saveItem_UPDATE(){
		long userId = 1l;
		
		IUser user = EasyMock.createMock(IUser.class);
		EasyMock.expect(user.getUserId()).andReturn(1l).anyTimes();
		EasyMock.expect(user.getEmail()).andReturn("email@domain").anyTimes();
		EasyMock.expect(user.getName()).andReturn("Name Name").anyTimes();
		
		ItemsServiceImpl fixture = new ItemsServiceImpl(null, null, null, null);
		fixture.userService = EasyMock.createMock(UserService.class);
		EasyMock.expect(fixture.userService.isLoggedIn()).andReturn(true).anyTimes();
		EasyMock.expect(fixture.userService.getCurrentUser()).andReturn(user).anyTimes();
		EasyMock.replay(fixture.userService, user);
		
		fixture.pm = this.pm;
		fixture.geoCellsService = new GeoHashServiceImpl();
		fixture.itemPhotoService = EasyMock.createMock(ItemPhotoService.class);
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("1")).andReturn("/photo?id=1").anyTimes();
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("2")).andReturn("/photo?id=2").anyTimes();
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("3")).andReturn("/photo?id=3").anyTimes();
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("4")).andReturn("/photo?id=4").anyTimes();
		EasyMock.replay(fixture.itemPhotoService);
		
		Item item = new Item();
		item.setUserId(userId);
		pm.currentTransaction().begin();
		pm.makePersistent(item);
		pm.currentTransaction().commit();
		
		final long itemId = item.getItemId();
		final String itemTitle = "title";
		final String description = "description";
		String currencyCode = "TZS";
		BigDecimal price = new BigDecimal("1");
		
		ItemOptions options = EasyMock.createMock(ItemOptions.class);
		EasyMock.expect(options.getItemId()).andReturn(itemId).anyTimes();
		EasyMock.expect(options.getTitle()).andReturn(itemTitle).anyTimes();
		EasyMock.expect(options.getCurrencyCode()).andReturn(currencyCode).anyTimes();
		EasyMock.expect(options.getDescription()).andReturn(description).anyTimes();
		EasyMock.expect(options.getLatLong()).andReturn(new LatLong(0, 0)).anyTimes();
		EasyMock.expect(options.getPhotoIdsToRemove()).andReturn(Arrays.asList("1","2")).anyTimes();
		EasyMock.expect(options.getPhotoIdsToAdd()).andReturn(Arrays.asList("3","4")).anyTimes();		
		EasyMock.expect(options.getPrice()).andReturn(price).anyTimes();
		EasyMock.expect(options.getTagsToRemove()).andReturn(Arrays.asList("computer","used")).anyTimes();
		EasyMock.expect(options.getTagsToAdd()).andReturn(Arrays.asList("calculator","new")).anyTimes();
		EasyMock.replay(options);

		IItem result = fixture.saveItem(userId, options);
		assertNotNull(result);
		assertEquals(options.getTitle(), result.getTitle());
		assertEquals(options.getCurrencyCode(), result.getCurrencyCode());
		assertEquals(options.getDescription(), result.getDescription());
		assertEquals(options.getLatLong(), result.getLatLong());
		assertEquals(options.getPhotoIdsToAdd(), result.getPhotoIds());
		assertEquals(options.getPrice(), result.getPrice());
		assertEquals(options.getTagsToAdd(), result.getTags());
	}

	@Test
	public void testListItems(){
		ItemsServiceImpl fixture = new ItemsServiceImpl(null, null, null, null);
		fixture.userService = new UserServiceImpl(null);
		fixture.pm = this.pm;
		fixture.geoCellsService = new GeoHashServiceImpl();
		fixture.itemPhotoService = EasyMock.createMock(ItemPhotoService.class);
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("1")).andReturn("/photo?id=1");
		EasyMock.expect(fixture.itemPhotoService.getPhotoUrl("2")).andReturn("/photo?id=2");
		EasyMock.replay(fixture.itemPhotoService);
		
		Item item = new Item();
		item.setPoint(new GeoPt(0, 0));
		List<String> geoCells = fixture.geoCellsService.buildGeoCells(new LatLong(0, 0));
		item.setGeoCells(geoCells);

		pm.currentTransaction().begin();
		pm.makePersistent(item);
		pm.currentTransaction().commit();
		
		item = new Item();
		item.setPoint(new GeoPt(50, 50));
		geoCells = fixture.geoCellsService.buildGeoCells(new LatLong(50, 50));
		item.setGeoCells(geoCells);
		pm.currentTransaction().begin();
		pm.makePersistent(item);
		pm.currentTransaction().commit();
		
		ItemFilter filter = new ItemFilter();
		LatLong northEast = new LatLong(10, 10);
		LatLong sourthWest = new LatLong(-10, -10);		
		LatLongBounds bounds = new LatLongBounds(northEast, sourthWest);

		filter.setBounds(bounds);
		QueryResult<IItem> result = fixture.listItems(filter);
		assertNotNull(result);
		assertNotNull(result.getList());
		assertEquals(1, result.getList().size());
		assertNull(result.getNextCursor());
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 07/08/11 12:58
	 */
	@Before
	public void setUp()
		throws Exception {
		this.pm = PMF.getPersistenceManager();
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 07/08/11 12:58
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 07/08/11 12:58
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ItemsServiceImplTest.class);
	}
}