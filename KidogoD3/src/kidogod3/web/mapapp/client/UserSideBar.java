package kidogod3.web.mapapp.client;

import kidogod3.core.users.client.IUser;
import kidogod3.core.users.client.UserService;
import kidogod3.core.users.client.UserServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class UserSideBar extends Composite implements HasText {
	
	
	private static UserSideBarUiBinder uiBinder = GWT
			.create(UserSideBarUiBinder.class);

	interface UserSideBarUiBinder extends UiBinder<Widget, UserSideBar> {
	}

	@UiField
	Button button;
	@UiField
	Button postItemButton;
	@UiField
	CheckBox showMyItems;
	@UiField
	SpanElement userName;
	
	SideBar.Presenter presenter;
	
	public UserSideBar(SideBar.Presenter presenter) {
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		presenter.signOut();
	}
	@UiHandler("postItemButton")
	void onPostItemButtonClick(ClickEvent e) {
		presenter.promptAddItem();
	}
	
	@UiHandler("showMyItems")
	void onChecked(ClickEvent e){
		presenter.setShowOnlyUserItems(showMyItems.getValue());
	}
	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}
	
	public void setUser(IUser user){
		userName.setInnerText(user.getName());
	}
}
