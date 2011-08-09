package kidogod3.web.mapapp.client.edit;

import kidogod3.core.geo.client.LatLong;
import kidogod3.core.items.client.IItem;
import kidogod3.core.items.client.ItemOptionsImpl;
import kidogod3.core.items.client.ItemsService;
import kidogod3.core.items.client.ItemsServiceAsync;
import kidogod3.core.items.client.NewItemBroadcastService;
import kidogod3.web.mapapp.client.MapCanvas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;

public class EditItemFormDialogFactory implements IEditItemForm.Presenter{
	static ItemsServiceAsync service = GWT.create(ItemsService.class);
	
	MapCanvas canvas;
	Long userId;
	HasLatLng position;
	
	final DialogBox dialog = new DialogBox();
	
	public EditItemFormDialogFactory(final MapCanvas canvas, final Long userId, final HasLatLng position) {
		this.canvas = canvas;
		this.userId = userId;
		this.position = position;
	}

	/**
	 * Show dialog for editing a new item
	 */
	public void show(){
		EditItemForm form = new EditItemForm(this);
		dialog.setWidget(form);
		dialog.setText("New Item");
		dialog.center();
	}
	/**
	 * Show dialog for editing an existing item
	 */
	public void show(IItem item){
		EditItemForm form = new EditItemForm(this);
		form.setItem(item);
		dialog.setText("Edit Item: "+item.getItemId());
		dialog.setWidget(form);
		dialog.center();
	}

	@Override
	public void save(ItemOptionsImpl options) {
		options.setLatLong(new LatLong(position.getLatitude(), position.getLongitude()));
		service.saveItem(userId, options, new AsyncCallback<IItem>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getClass().getName()+":"+caught.getMessage());						
			}

			@Override
			public void onSuccess(IItem result) {
				dialog.hide();
				canvas.addItem(result);
				
				NewItemBroadcastService.broadcast(result);
			}
		});
		
	}

	@Override
	public void cancel() {
		dialog.hide();		
	}
}
