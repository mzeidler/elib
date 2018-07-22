package de.markozeidler.elib.window;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.markozeidler.elib.entity.Document;
import de.markozeidler.elib.entity.Theme;
import de.markozeidler.jpa.JPAHandler;

public class DocumentWindow extends Window implements Serializable {

	private JPAHandler jpaHandler;
	
	private TextField tTitle;
	
	private DocumentWindow documentWindow;
	
	private NativeSelect<Theme> cmbThemes;
	
	private Button bSave;
	
	private Button bCancel;
	
	private Document document;
		
	private boolean persist;
	
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public boolean isPersist() {
		return persist;
	}

	@Inject
	public DocumentWindow(JPAHandler jpaHandler) {
		this.jpaHandler = jpaHandler;
		this.documentWindow = this;
	}
	
	public void init(Document document) {
		persist = false;
		this.document = document;
		setCaption(document == null ? "Add Document" : "Edit Document");
		setWidth(500, Unit.PIXELS);
		setClosable(true);
		setDraggable(false);
		setResizable(false);
		setModal(true);
		center();
		
		initComponents();
		setContent(initLayouts());
	}
	
	private void initComponents() {
		
		tTitle = new TextField();
		tTitle.setCaption("Title");
		tTitle.setWidth(100, Unit.PERCENTAGE);
				
		List<Theme> themes =jpaHandler.findAll(new Theme());
		
		cmbThemes = new NativeSelect<>(null, themes);
		cmbThemes.setCaption("Theme");
		cmbThemes.setWidth(50, Unit.PERCENTAGE);
		
		bSave = new Button("Save");
		bSave.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				if (tTitle.getValue() == null || "".equals(tTitle.getValue())) {
					Notification.show("The Document name may not be empty", Type.WARNING_MESSAGE);
				} else {
					if (document == null) {
						document = new Document();
						document.setCreated(new Date());
					}
					
					if (cmbThemes.getSelectedItem().isPresent()) {
						document.setTheme(cmbThemes.getSelectedItem().get());
					} else {
						document.setTheme(null);
					}
					
					document.setTitle(tTitle.getValue());					
					document.setUpdated(new Date());
					persist = true;
					UI.getCurrent().removeWindow(documentWindow);					
				}
			}
		});
		
		bCancel = new Button("Cancel");
		bCancel.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				document = null;
				UI.getCurrent().removeWindow(documentWindow);
			}
		});
	}
	
	private VerticalLayout initLayouts() {
		VerticalLayout layout = new VerticalLayout();
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponents(bSave, bCancel);
		
		FormLayout form = new FormLayout();
		form.addComponents(tTitle, cmbThemes, buttons);
		
		layout.addComponents(form);
		
		return layout;
	}
}
