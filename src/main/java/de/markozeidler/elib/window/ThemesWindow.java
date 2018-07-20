package de.markozeidler.elib.window;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.markozeidler.elib.builder.UIBuilder;
import de.markozeidler.elib.entity.Theme;
import de.markozeidler.jpa.JPAHandler;

public class ThemesWindow extends Window implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private JPAHandler jpaHandler;
	
	private UIBuilder uiBuilder;
	
	private ThemesWindow themesWindow;
	
	private List<Theme> themes;
	
	private ListSelect<Theme> themesList;
	
	private TextField tName;
	
	private Label message;
	
	/**
	 * Buttons 
	 */
	private Button bRefresh;
	
	private Button bAdd;
	
	private Button bRemove;
	
	private Button bEdit;
	
	private Button bCancel;
	
	private Button bSave;

	@Inject
	public ThemesWindow(JPAHandler jpaHandler, UIBuilder uiBuilder) {
		this.jpaHandler = jpaHandler;
		this.themesWindow = this;
		this.uiBuilder = uiBuilder;
	}
	
	public void init() {
		setCaption("Manage Themes");
		setWidth(500, Unit.PIXELS);
		setHeight(400, Unit.PIXELS);
		setClosable(true);
		setDraggable(false);
		setResizable(false);
		setModal(true);
		center();
		
		initButtons();
		initComponents();
		setContent(initLayouts());
	}
	
	private void initComponents() {
		
		themes = jpaHandler.findAllThemes();
		 
        themesList = new ListSelect<>(null, themes);
        themesList.setSizeFull();
        if (themes.size() > 0) {
        	themesList.select(themes.get(0));
        	themesList.focus();
        	// addValueChangeListener not triggered
        }
        
 
        themesList.addValueChangeListener(event -> { 
        	System.out.println("MARKOTEST: Theme selected: " + event.getValue());
        	Notification.show("Value changed:", String.valueOf(event.getValue()), Type.TRAY_NOTIFICATION);
        });
		
        message = uiBuilder.label("message").build();
        message.addStyleName("smallFont");
        
        tName = uiBuilder.textField("Theme").caption("Theme").build();
        tName.setWidth(100, Unit.PERCENTAGE);
	}
	
	private void initButtons() {

		bRefresh = uiBuilder.button(VaadinIcons.REFRESH, "Refresh Themes").build();
		bRefresh.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//

			}
		});
		
		bAdd = uiBuilder.button(VaadinIcons.PLUS, "Add Theme").build();
		bAdd.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//
				themesList.deselectAll();
				tName.setPlaceholder("-- Theme name ---");
			}
		});
		
		bRemove = uiBuilder.button(VaadinIcons.TRASH, "Remove Theme").build();
		bRemove.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//

			}
		});
		
		bEdit = uiBuilder.button(VaadinIcons.PENCIL, "Edit Theme").build();
		bEdit.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//

			}
		});
		
		bCancel = uiBuilder.button("Cancel").build();
		bCancel.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//

			}
		});
		
		bSave = uiBuilder.button("Save").build();
		bSave.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//

			}
		});		
	}
	
	private HorizontalLayout initLayouts() {
		
		// LEFT
		VerticalLayout left = uiBuilder.VL().build();
		left.setHeight(100, Unit.PERCENTAGE);
		left.addComponents(themesList);
		
		// RIGHT		
		HorizontalLayout buttonsLayout = uiBuilder.HL().spacing(false).build();		
		buttonsLayout.addComponents(bRefresh, bAdd, bEdit, bRemove);
		
		VerticalLayout rightMiddle = uiBuilder.VL().full().build();
		rightMiddle.addStyleName("lightGrayBackground");
		rightMiddle.addStyleName("border");
		rightMiddle.addComponents(tName, message);
		
		HorizontalLayout rightBottom = uiBuilder.HL().build();
		rightBottom.addComponents(bCancel, bSave);
		
		VerticalLayout right = uiBuilder.VL().build();
		right.setHeight(100, Unit.PERCENTAGE);
		right.addComponents(buttonsLayout, rightMiddle, rightBottom);
		right.setExpandRatio(rightMiddle, 1);

		// LAYOUT
		HorizontalLayout layout = uiBuilder.HL().noMargin().noSpacing().full().build();
		layout.addComponents(left, right);
		layout.setExpandRatio(left, 1);
		layout.setExpandRatio(right, 2);
		return layout;
	}
}
