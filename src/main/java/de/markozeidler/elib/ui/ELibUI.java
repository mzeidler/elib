package de.markozeidler.elib.ui;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import de.markozeidler.elib.entity.User;
import de.markozeidler.elib.window.LoginWindow;
import de.markozeidler.elib.window.MainWindow;
import de.markozeidler.jpa.JPAHandler;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("eLibtheme")
@CDIUI("")
public class ELibUI extends UI {

	private boolean autoLogin = true;
	
	private static final long serialVersionUID = 1L;

	@Inject
	private LoginWindow loginWindow;
	
	@Inject
	private MainWindow mainWindow;
	
	@Inject
	private JPAHandler jpaHandler;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	User user = (User) UI.getCurrent().getSession().getAttribute("user");
    	
    	if (user == null && autoLogin) {
    		user = jpaHandler.checkUser("m", "m");
    		UI.getCurrent().getSession().setAttribute("user", user);
    	}
    	
    	if (user == null) {
    		UI.getCurrent().addWindow(loginWindow);
    	} else {
    		mainWindow.init();
    		UI.getCurrent().addWindow(mainWindow);
    	}
    }

    @WebServlet(urlPatterns = "/*", name = "ELibUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = ELibUI.class, productionMode = false)
    public static class ELibUIServlet extends VaadinCDIServlet {
    }
     
}
