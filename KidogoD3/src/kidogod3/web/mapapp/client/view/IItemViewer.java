package kidogod3.web.mapapp.client.view;

import kidogod3.core.items.client.IItem;

import com.google.gwt.user.client.ui.IsWidget;

public interface IItemViewer extends IsWidget{

	public interface Presenter{
		void close();
	}
	
	void setItem(IItem item);
}
