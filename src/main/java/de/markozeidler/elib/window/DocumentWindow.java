package de.markozeidler.elib.window;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.markozeidler.elib.entity.Document;
import de.markozeidler.elib.entity.Theme;
import de.markozeidler.jpa.DataRepository;

public class DocumentWindow extends Window implements Serializable {

	private DataRepository dataRepository;
	
	private TextField tTitle;
	
	private DocumentWindow documentWindow;
	
	private NativeSelect<Theme> cmbThemes;
	
	private Button bSave;
	
	private Button bCancel;
	
	private Document document;
	
	private Theme filteredTheme;
	
	@Inject
	public DocumentWindow(DataRepository dataRepository) {
		this.dataRepository = dataRepository;
		this.documentWindow = this;
	}
	
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
	public void init(Document document, Theme filteredTheme) {
		this.document = document;
		this.filteredTheme = filteredTheme;
		setCaption(document == null ? "Add Document" : "Edit Document");
		setWidth(500, Unit.PIXELS);
		setClosable(true);
		setDraggable(false);
		setResizable(false);
		setModal(true);
		center();
		
		initComponents();
		fillFields();
		setContent(initLayouts());
	}
	
	private void fillFields() {
		if (document != null) {
			tTitle.setValue(document.getTitle());
			cmbThemes.setValue(document.getTheme());
		} else if (filteredTheme != null) {
			cmbThemes.setValue(filteredTheme);
		}
	}
	
	private void initComponents() {
		
		tTitle = new TextField();
		tTitle.setCaption("Title");
		tTitle.setWidth(100, Unit.PERCENTAGE);		
						
		cmbThemes = new NativeSelect<>(null, dataRepository.findAllThemes());
		cmbThemes.setCaption("Theme");
		cmbThemes.setWidth(50, Unit.PERCENTAGE);
		
		bSave = new Button("Save");
		bSave.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				if (tTitle.getValue() == null || "".equals(tTitle.getValue())) {
					Notification.show("The Document name may not be empty", Type.WARNING_MESSAGE);
				} else {
					boolean isNew = document == null;
					if (isNew) {
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
					
					// Save Document
					if (isNew) {
						dataRepository.saveDocument(document);
					} else {
						dataRepository.updateDocument(document);
					}
					
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
