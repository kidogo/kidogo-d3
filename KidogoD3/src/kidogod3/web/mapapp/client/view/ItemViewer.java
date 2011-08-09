package kidogod3.web.mapapp.client.view;

import kidogod3.core.items.client.IItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class ItemViewer extends UIObject implements IsWidget {

	private static ItemViewerUiBinder uiBinder = GWT
			.create(ItemViewerUiBinder.class);

	interface ItemViewerUiBinder extends UiBinder<Element, ItemViewer> {
	}

	@UiField
	SpanElement title;
	@UiField
	DivElement description;

	IItem item;
	
	public ItemViewer(IItem item) {
		setElement(uiBinder.createAndBindUi(this));
		title.setInnerText(item.getTitle());
		description.setInnerText(item.getDescription());
	}

	@Override
	public Widget asWidget() {		
		return new Widget(){
			@Override
			public com.google.gwt.user.client.Element getElement() {
				// TODO Auto-generated method stub
				return ItemViewer.this.getElement();
			}
		};
	}

}
