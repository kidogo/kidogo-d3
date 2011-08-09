package kidogod3.web.mapapp.client;

public interface SideBar {

	public interface Presenter{
		void signOut();
		void signIn();
		void promptAddItem();
		void setShowOnlyUserItems(boolean showOnlyUserItems);
	}
}
