package de.markozeidler.elib.builder;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.TextField;

public class TextFieldBuilder extends BaseBuilder<TextFieldBuilder> {

	private TextField textField;

	public TextFieldBuilder(String id) {
		this.textField = new TextField(id);
	}

	public TextField build() {
		return this.textField;
	}

	@Override
	public AbstractComponent getComponent() {
		return textField;
	}

	@Override
	public AbstractOrderedLayout getOrderedLayout() {
		throw new UnsupportedOperationException();
	}

	@Override
	public AbstractField getField() {
		return textField;
	}

}
