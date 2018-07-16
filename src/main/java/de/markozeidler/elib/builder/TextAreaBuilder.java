package de.markozeidler.elib.builder;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.TextArea;

public class TextAreaBuilder extends BaseBuilder<TextAreaBuilder> {

	private TextArea textArea;

	public TextAreaBuilder(String id) {
		this.textArea = new TextArea(id);
	}

	public TextArea build() {
		return this.textArea;
	}

	public TextAreaBuilder maxLength(int length) {
		this.textArea.setMaxLength(length);
		return this;
	}

	@Override
	public AbstractComponent getComponent() {
		return textArea;
	}

	@Override
	public AbstractOrderedLayout getOrderedLayout() {
		throw new UnsupportedOperationException();
	}

	@Override
	public AbstractField getField() {
		return textArea;
	}
}
