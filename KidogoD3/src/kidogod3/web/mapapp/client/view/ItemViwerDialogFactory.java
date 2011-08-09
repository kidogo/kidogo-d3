package kidogod3.web.mapapp.client.view;

import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.user.client.ui.DialogBox;

import kidogod3.core.items.client.IItem;

public class ItemViwerDialogFactory {

	IItem item;
	DialogBox dialog = new DialogBox(true, false);
	public ItemViwerDialogFactory(IItem item) {
		this.item = item;
	}

	public void show(){
		ItemViewer viewer = new ItemViewer(item);		
		dialog.setWidget(viewer);
		dialog.setText("View Item");
		dialog.center();
	}
	
	public void hide(){
		dialog.hide();
	}
}
