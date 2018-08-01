package de.markozeidler.elib.window;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import org.w3c.tidy.Tidy;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.markozeidler.elib.entity.Document;
import de.markozeidler.jpa.DataRepository;

public class HtmlCodeEditor extends Window implements Serializable {

	private Document document;
	
	private Button bSave;
	
	private Button bCancel;
	
	private TextArea textArea;
	
	private HtmlCodeEditor htmlCodeEditor;
	
	private DataRepository dataRepository;
	
	private RichTextArea richtTextArea;
	
	@Inject
	public HtmlCodeEditor(DataRepository dataRepository) {
		this.dataRepository = dataRepository;
		this.htmlCodeEditor = this;
	}
	
	public void init(Document document, RichTextArea richtTextArea) {
		this.document = document;
		this.richtTextArea = richtTextArea;
		setCaption("Edit Content");
		setWidth(800, Unit.PIXELS);
		setHeight(600,  Unit.PIXELS);
		setClosable(true);
		setDraggable(false);
		setResizable(false);
		setModal(true);
		center();
		
		initComponents();
		setContent(initLayouts());
	}
	
	private void initComponents() {
		
		textArea = new TextArea();
		textArea.setSizeFull();	
		textArea.setWidth(100, Unit.PERCENTAGE);
		textArea.setHeight(100,  Unit.PERCENTAGE);
		textArea.setValue(prettyPrintHTML(document.getContent()));
		
		bSave = new Button("Save");
		bSave.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				document.setContent(textArea.getValue());
				document.setUpdated(new Date());
				dataRepository.updateDocument(document);
				richtTextArea.setValue(textArea.getValue());
				UI.getCurrent().removeWindow(htmlCodeEditor);
				

			}
		});
		
		bCancel = new Button("Cancel");
		bCancel.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				document = null;
				UI.getCurrent().removeWindow(htmlCodeEditor);
			}
		});
	}
	
	private VerticalLayout initLayouts() {
		VerticalLayout layout = new VerticalLayout();
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponents(bSave, bCancel);
		
		HorizontalLayout textLayout = new HorizontalLayout();
		textLayout.setSizeFull();
		textLayout.addComponent(textArea);
		textLayout.setExpandRatio(textArea, 1);
		
		layout.addComponents(textLayout, buttons);
		layout.setSizeFull();
		layout.setExpandRatio(textLayout, 1);
		
		return layout;
	}
	
	public String prettyPrintHTML(String rawHTML) {    
	    Tidy tidy = new Tidy();
	    tidy.setXHTML(true);
	    tidy.setIndentContent(true);
	    tidy.setPrintBodyOnly(true);
	    tidy.setTidyMark(false);

	    // HTML to DOM
	    org.w3c.dom.Document  htmlDOM = tidy.parseDOM(new ByteArrayInputStream(rawHTML.getBytes()), null);

	    // Pretty Print
	    OutputStream out = new ByteArrayOutputStream();
	    tidy.pprint(htmlDOM, out);

	    return out.toString();
	}
}
