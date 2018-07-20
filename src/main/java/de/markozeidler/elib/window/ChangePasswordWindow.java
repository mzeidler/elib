package de.markozeidler.elib.window;

import java.io.Serializable;

import javax.inject.Inject;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.markozeidler.elib.entity.User;
import de.markozeidler.jpa.JPAHandler;

public class ChangePasswordWindow extends Window implements Serializable {

	private static final long serialVersionUID = 1L;

	private JPAHandler jpaHandler;
	
	private Label message;
	
	private Label changePasswordMessage;
	
	private PasswordField oldPassword;
	
	private PasswordField newPassword;
	
	private PasswordField newPasswordRepeat;
	
	private Button changePasswordButton;
	
	private Button cancelButton;
	
	private ChangePasswordWindow changePasswordWindow;
	
	@Inject
	public ChangePasswordWindow(JPAHandler jpaHandler) {
		this.jpaHandler = jpaHandler;
		this.changePasswordWindow = this;
	}
	
	public void init() {
		setCaption("Change Password");
		setWidth(450, Unit.PIXELS);
		setHeight(290, Unit.PIXELS);
		setClosable(true);
		setDraggable(false);
		setResizable(false);
		setModal(true);
		center();
		
		initComponents();
		
		FormLayout layout = new FormLayout();
		layout.setMargin(true);
		layout.setSizeFull();
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addComponents(changePasswordButton, cancelButton);
		
		layout.addComponents(oldPassword, newPassword, newPasswordRepeat, buttonLayout , message);
		setContent(layout);
	}	
	
	private void initComponents() {
		//changePasswordMessage = new Label("<b>Change Password</b>", ContentMode.HTML);
		
		message = new Label("");
		message.setContentMode(ContentMode.HTML);
		
		oldPassword = new PasswordField("Old Password", "");
		oldPassword.setWidth(100.0f, Unit.PERCENTAGE);
		oldPassword.addValueChangeListener(event -> {
			message.setValue("");
		});

		newPassword = new PasswordField("New Password", "");
		newPassword.setWidth(100.0f, Unit.PERCENTAGE);
		newPassword.addValueChangeListener(event -> {
			message.setValue("");
		});

		newPasswordRepeat = new PasswordField("New Password Repeat", "");
		newPasswordRepeat.setWidth(100.0f, Unit.PERCENTAGE);
		newPasswordRepeat.addValueChangeListener(event -> {
			message.setValue("");
		});

		changePasswordButton = new Button("Change");
		changePasswordButton.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		    	User user = (User) UI.getCurrent().getSession().getAttribute("user");		    	
		    	if (!jpaHandler.checkPassword(user, oldPassword.getValue())) {
		    		clearFields();
			    	message.setValue("<font color=\"red\">Invalid Password</font>");
		    	} else if (newPassword.getValue() == null || "".equals(newPassword.getValue())) {
		    		clearFields();
			    	message.setValue("<font color=\"red\">Passwords may not be empty</font>");		    		
		    	} else if (!newPassword.getValue().equals(newPasswordRepeat.getValue())) {
		    		clearFields();
			    	message.setValue("<font color=\"red\">The Passwords are not equal</font>");		
		    	} else {
		    		message.setValue("<font color=\"red\">Change Password</font>");	
		    		user.setPwd(jpaHandler.encode(newPassword.getValue()));
		    		jpaHandler.save(user);
		    		UI.getCurrent().removeWindow(changePasswordWindow);
		    	}
		    	
		    }
		});
		
		cancelButton = new Button("Cancel");
		cancelButton.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {		    	
		    	UI.getCurrent().removeWindow(changePasswordWindow);
		    }
		});		
	}	
	
	private void clearFields() {
    	oldPassword.setValue("");
    	newPassword.setValue("");		    	
    	newPasswordRepeat.setValue("");
	}
}
