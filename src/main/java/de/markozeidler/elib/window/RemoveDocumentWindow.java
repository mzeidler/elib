package de.markozeidler.elib.window;

import java.io.Serializable;
import java.util.Set;

import javax.inject.Inject;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import de.markozeidler.elib.entity.Document;
import de.markozeidler.jpa.DataRepository;

public class RemoveDocumentWindow extends Window implements Serializable {

	private DataRepository dataRepository;
	
	private RemoveDocumentWindow removeDocumentWindow;
	
	@Inject
	public RemoveDocumentWindow(DataRepository dataRepository) {
		this.dataRepository = dataRepository;
		this.removeDocumentWindow = this;
	}
	
	public void init(Set<Document> documents) {
		setCaption("Remove Documents");
		setHeight(150, Unit.PIXELS);
		setClosable(false);
		setDraggable(false);
		setResizable(false);
		setModal(true);
		center();
		
		Label label = new Label("<b>Are you sure that you want to delete " + (documents.size() == 1 ? "this Document" : "these Documents") + "?</b>",ContentMode.HTML);
		
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(label);
		
		HorizontalLayout hl = new HorizontalLayout();
		
		Button bYes = new Button("Yes");
		bYes.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				dataRepository.removeDocuments(documents);
				UI.getCurrent().removeWindow(removeDocumentWindow);
			}
		});
		
		Button bNo = new Button("No");
		bNo.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().removeWindow(removeDocumentWindow);
			}
		});
		
		hl.addComponents(bYes, bNo);
				
		layout.addComponent(hl);
		layout.setExpandRatio(label, 1);
		layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(hl, Alignment.BOTTOM_CENTER);
		setContent(layout);
	}
}
