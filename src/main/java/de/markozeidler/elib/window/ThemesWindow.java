package de.markozeidler.elib.window;

import java.awt.Dialog;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.markozeidler.elib.builder.UIBuilder;
import de.markozeidler.elib.entity.Theme;
import de.markozeidler.jpa.JPAHandler;

public class ThemesWindow extends Window implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private JPAHandler jpaHandler;
	
	private UIBuilder uiBuilder;
	
	private List<Theme> themes;
	
	private ListSelect<Theme> themesList;
	
	private TextField tName;
	
	private Label message;
	
	private EditMode editMode;
	
	private ThemesWindow themesWindow;
	
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
		this.uiBuilder = uiBuilder;
		this.themesWindow = themesWindow;
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
		editMode = EditMode.Clear;
		
        message = uiBuilder.label("message").build();
        message.addStyleName("smallFont");
        
        tName = uiBuilder.textField("Theme").caption("Theme").build();
        tName.setWidth(100, Unit.PERCENTAGE);
        
		themes = jpaHandler.findAllThemes();		 
        themesList = new ListSelect<>(null, themes);
        themesList.setSizeFull();
        themesList.addValueChangeListener(event -> { 
        	setEditMode(EditMode.Browse);
        });

        if (themes.size() > 0) {
        	themesList.select(themes.get(0));
        	themesList.focus();
        }
 		
	}
	
	private void refreshList() {
		themes = jpaHandler.findAllThemes();
		themesList.setItems(themes);
	}
	
	private void initButtons() {

		bRefresh = uiBuilder.button(VaadinIcons.REFRESH, "Refresh Themes").build();
		bRefresh.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				themesList.deselectAll();
				refreshList();
				setEditMode(EditMode.Clear);
			}
		});
		
		bAdd = uiBuilder.button(VaadinIcons.PLUS, "Add Theme").build();
		bAdd.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				themesList.deselectAll();
				setEditMode(EditMode.New);
			}
		});
		
		bRemove = uiBuilder.button(VaadinIcons.TRASH, "Remove Theme").build();
		bRemove.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				ConfirmationWindow confirmationWindow = new ConfirmationWindow("Are you sure that you want to delete this Theme?", "Yes", "No");
				UI.getCurrent().addWindow(confirmationWindow);
				confirmationWindow.addCloseListener(new CloseListener() {
					@Override
					public void windowClose(CloseEvent event) {
						System.out.println("CLOSED... Answer is " + confirmationWindow.getSelectedOption());
					}
				});
//				Theme theme = getSelectedTheme();
//				if (theme != null) {
//					jpaHandler.remove(theme);
//					refreshList(); //
//					setEditMode(EditMode.Browse);
//				} else {
//					Notification.show("No Theme is selected", Type.WARNING_MESSAGE);
//				}
			}
		});
		
		bEdit = uiBuilder.button(VaadinIcons.PENCIL, "Edit Theme").build();
		bEdit.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {			
				setEditMode(EditMode.Edit);		
			}
		});
		
		bCancel = uiBuilder.button("Cancel").build();
		bCancel.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				setEditMode(EditMode.Browse); //jj
			}
		});
		
		bSave = uiBuilder.button("Save").build();
		bSave.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Theme theme = getSelectedTheme();
				
				boolean isNew = false;
				if (theme == null) {
					theme = new Theme();
					isNew = true;
				}
				
				if (tName.getValue() == null || "".equals(tName.getValue())) {
					Notification.show("The Themename may not be empty", Type.WARNING_MESSAGE);
				} else {
					theme.setName(tName.getValue());
					if (isNew) {
						jpaHandler.save(theme);
					} else {
						jpaHandler.update(theme);
					}
					
					refreshList();
					setEditMode(EditMode.Browse);
				}
				
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
	
	private void setEditMode(EditMode mode) {
		editMode = mode;
		Theme theme = getSelectedTheme();
				
		// Save Button
		bSave.setVisible(mode == EditMode.Edit || mode == EditMode.New);
		bSave.setCaption(mode == EditMode.Edit ? "Update" : "Save");
		
		// Cancel Button
		bCancel.setVisible(mode == EditMode.Edit || mode == EditMode.New);

		// Theme name text field
		tName.setValue(theme == null ? "" : theme.getName());
		tName.setPlaceholder(mode == EditMode.New ? "Enter new theme name..." : "");
		tName.setReadOnly(mode != EditMode.Edit && mode != EditMode.New);
		
		// Remove Button
		bRemove.setEnabled(mode != EditMode.Edit && mode != EditMode.New);
		
		// Add Button
		bAdd.setEnabled(mode != EditMode.Edit && mode != EditMode.New);
		
		// Refresh Button
		bRefresh.setEnabled(mode != EditMode.Edit && mode != EditMode.New);
		
		// Edit Button
		bEdit.setEnabled(mode != EditMode.Edit && mode != EditMode.New);
		
		// Message
		if (mode == EditMode.New) {
			message.setValue("New theme");
		} else if (theme != null) {
			int count = theme.getDocuments().size();
			message.setValue("This theme has " + count + " document" + (count == 1 ? "" : "s") + " attached");
		} else {
			message.setValue("");
		}
		
	}
	
	private Theme getSelectedTheme() {
		Theme theme = null;
		Set<Theme> selectedThemes = themesList.getSelectedItems();
		if (selectedThemes.size() > 0) {
			theme = (Theme) selectedThemes.toArray()[0];
		}
		return theme;
	}
}

enum EditMode {
	Browse, New, Edit, Clear;
}