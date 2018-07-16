package de.markozeidler.elib.layouts;

import javax.inject.Inject;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

import de.markozeidler.elib.entity.User;
import de.markozeidler.elib.window.ChangePasswordWindow;

public class HeaderLayout extends HorizontalLayout {

	private static final long serialVersionUID = 1L;

	private ChangePasswordWindow changePasswordWindow;
	
	@Inject
	public HeaderLayout(ChangePasswordWindow changePasswordWindow) {
		this.changePasswordWindow = changePasswordWindow;
		setMargin(true);
		addStyleName("elibHeader");		
		setWidth(100, Unit.PERCENTAGE);
		setHeight(-1.0f, Unit.PIXELS);
	}
	
	public void init() {
		User user = (User) UI.getCurrent().getSession().getAttribute("user");
		
		HorizontalLayout rightLayout = new HorizontalLayout();
		Label userLabel = getUserLabel(user);
		rightLayout.addComponents(userLabel, getMenuBar());
		rightLayout.setComponentAlignment(userLabel, Alignment.MIDDLE_CENTER);
		
		addComponents(getELibLabel(), rightLayout);
		setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
	}
	
	private Label getUserLabel(User user) {
		Label label = new Label("Welcome " + user.getFirstname() + " " + user.getLastname());
		return label;
	}
	
	private Label getELibLabel() {
		Label label = new Label("eLib v1.0");
		label.addStyleName("bigFont");
		return label;
	}
	
	private MenuBar getMenuBar() {		
		MenuBar.Command changePasswordCommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				changePasswordWindow.init();
				UI.getCurrent().addWindow(changePasswordWindow);
			}			
		};
		
		MenuBar.Command logoutCommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				VaadinService.getCurrentRequest().getWrappedSession().invalidate();
				Page.getCurrent().setLocation("");
			}
		};
		
		MenuBar menuBar = new MenuBar();
		MenuItem topMenuItem = menuBar.addItem("", VaadinIcons.USER, null);
		MenuItem changePasswordMenuItem = topMenuItem.addItem("Change Password", null, changePasswordCommand);
		MenuItem logoutMenuItem = topMenuItem.addItem("Logout", null, logoutCommand);
		return menuBar;
	}	
}
