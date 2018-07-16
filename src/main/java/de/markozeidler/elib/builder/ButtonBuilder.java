package de.markozeidler.elib.builder;

import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;

public class ButtonBuilder extends BaseBuilder<ButtonBuilder> {

	private Button button;	

	public ButtonBuilder(Resource icon, String description) {
		this.button = new Button(icon);
		this.button.setDescription(description);
	}
	
	public Button build() {
		return this.button;
	}

	@Override
	public AbstractComponent getComponent() {
		return button;
	}

	@Override
	public AbstractOrderedLayout getOrderedLayout() {
		throw new UnsupportedOperationException();
	}

	@Override
	public AbstractField getField() {
		throw new UnsupportedOperationException();
	}
}
