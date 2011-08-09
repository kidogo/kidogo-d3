package kidogod3.web.mapapp.client.edit;

import kidogod3.core.items.client.IItem;
import kidogod3.core.items.client.ItemOptionsImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditItemForm extends Composite implements HasText, IEditItemForm {

	private static EditItemFormUiBinder uiBinder = GWT
			.create(EditItemFormUiBinder.class);

	interface EditItemFormUiBinder extends UiBinder<Widget, EditItemForm> {
	}

	@UiField
	Button save;
	@UiField
	Button cancel;
	@UiField
	TextBox title;
	@UiField
	TextArea description;
	
	IItem item;
	IEditItemForm.Presenter presenter;
	
	public EditItemForm(IEditItemForm.Presenter presenter) {
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
	}
	
	@UiHandler("save")
	void onClick(ClickEvent e) {
		ItemOptionsImpl options = new ItemOptionsImpl();
		if(item!=null){
			options.setItemId(item.getItemId());
		}
		options.setTitle(title.getValue());
		options.setDescription(description.getValue());
		presenter.save(options);
	}
	@UiHandler("cancel")
	void onCancelClick(ClickEvent e) {
		presenter.cancel();
	}

	public void setText(String text) {
		save.setText(text);
	}

	public String getText() {
		return save.getText();
	}

	@Override
	public void setItem(IItem item) {
		title.setValue(item.getTitle());
		description.setValue(item.getDescription());
		this.item = item;
	}

}
