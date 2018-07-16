package de.markozeidler.elib.builder;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Label;

public class LabelBuilder extends BaseBuilder<LabelBuilder> {

	private Label label;
	
	public LabelBuilder() {
		this.label = new Label();
	}
	
	public LabelBuilder(String caption) {
		this.label = new Label(caption);
	}
	
	public Label build() {
		return this.label;
	}
	
	@Override
	public AbstractComponent getComponent() {
		return label;
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
