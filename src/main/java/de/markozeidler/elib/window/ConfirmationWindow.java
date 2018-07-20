package de.markozeidler.elib.window;

import java.io.Serializable;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ConfirmationWindow extends Window implements Serializable {

	private String question;
	
	private String[] options;
	
	private String selectedOption;
	
	private ConfirmationWindow confirmationWindow;
	
	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	public ConfirmationWindow(String question, String...options) {
		this.question = question;
		this.options = options;
		this.confirmationWindow = this; 
		init();
	}
	
	public void init() {
		setCaption("Confirmation");
		setHeight(150, Unit.PIXELS);
		setClosable(false);
		setDraggable(false);
		setResizable(false);
		setModal(true);
		center();
		
		Label label = new Label("<b>" + question + "</b>",ContentMode.HTML);
		
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(label);
		
		HorizontalLayout hl = new HorizontalLayout();
		for (String option : options) {
			Button bOption = new Button(option);
			bOption.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					selectedOption = option;
					UI.getCurrent().removeWindow(confirmationWindow);
				}
			});
			
			hl.addComponent(bOption);
		}
		
		layout.addComponent(hl);
		layout.setExpandRatio(label, 1);
		layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(hl, Alignment.BOTTOM_CENTER);
		setContent(layout);
	}
}
