package kidogod3.web.mapapp.client;

import kidogod3.core.users.client.UserService;
import kidogod3.core.users.client.UserServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class GuestSideBar extends Composite implements HasText {
	static UserServiceAsync userServiceAsync = GWT.create(UserService.class);
	
	private static GuestSideBarUiBinder uiBinder = GWT
			.create(GuestSideBarUiBinder.class);

	interface GuestSideBarUiBinder extends UiBinder<Widget, GuestSideBar> {
	}

	@UiField
	Button button;
	SideBar.Presenter sideBarPresenter;
	
	public GuestSideBar(SideBar.Presenter sideBarPresenter) {
		initWidget(uiBinder.createAndBindUi(this));
		this.sideBarPresenter = sideBarPresenter;
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		sideBarPresenter.signIn();
	}

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}

}
