package kidogod3.web.mapapp.client.edit;

import kidogod3.core.items.client.IItem;
import kidogod3.core.items.client.ItemOptionsImpl;
import kidogod3.core.items.client.ItemsService.ItemOptions;

import com.google.gwt.user.client.ui.IsWidget;

public interface IEditItemForm extends IsWidget{
	
	public interface Presenter{
		void save(ItemOptionsImpl options);
		void cancel();
	}
	
	void setItem(IItem item);

}
