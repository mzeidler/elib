package de.markozeidler.elib.window;

import java.io.Serializable;

import javax.inject.Inject;

import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.markozeidler.elib.entity.User;
import de.markozeidler.jpa.JPAHandler;

public class LoginWindow extends Window implements Serializable {

	private static final long serialVersionUID = 1L;

	private JPAHandler jpaHandler;
	
	private Label message;
	
	private Label loginMessage;
	
	private TextField username;
	
	private PasswordField password;
	
	private Button loginButton;
	
	@Inject
	public LoginWindow(JPAHandler jpaHandler) {
		this.jpaHandler = jpaHandler;
		init();
	}
	
	private void init() {		
		setWidth(400, Unit.PIXELS);
		setHeight(260, Unit.PIXELS);
		setClosable(false);
		setDraggable(false);
		setResizable(false);
		center();
		
		initComponents();
		
		FormLayout layout = new FormLayout();
		layout.setMargin(true);
		layout.setSizeFull();
		layout.addComponents(loginMessage, username, password, loginButton, message);
		setContent(layout);
	}
	
	private void initComponents() {
		loginMessage = new Label("<b>Login to eLib</b>", ContentMode.HTML);
		
		message = new Label("");
		message.setContentMode(ContentMode.HTML);
		
		username = new TextField("Username", "");
		username.setWidth(100.0f, Unit.PERCENTAGE);
		username.addValueChangeListener(event -> {
			message.setValue("");
		});
		
		password = new PasswordField("Password", "");
		password.setWidth(100.0f, Unit.PERCENTAGE);
		password.addValueChangeListener(event -> {
			message.setValue("");
		});
		
		loginButton = new Button("Login");
		loginButton.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {		    	
		    	User user = jpaHandler.checkUser(username.getValue(), password.getValue());
		    	if (user != null) {   		
					UI.getCurrent().getSession().setAttribute("user", user);
					Page.getCurrent().reload();
		    	} else {
			    	username.setValue("");
			    	password.setValue("");		    	
			    	message.setValue("<font color=\"red\">Invalid login</font>");
		    	}
		    }
		});
	}
	
}
