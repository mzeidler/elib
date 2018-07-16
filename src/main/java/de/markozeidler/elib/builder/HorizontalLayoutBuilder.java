package de.markozeidler.elib.builder;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.HorizontalLayout;

public class HorizontalLayoutBuilder extends BaseBuilder<HorizontalLayoutBuilder> {

	private HorizontalLayout horizontalLayout;

	public HorizontalLayoutBuilder() {
		this.horizontalLayout = new HorizontalLayout();
	}

	public HorizontalLayout build() {
		return this.horizontalLayout;
	}

	@Override
	public AbstractComponent getComponent() {
		return horizontalLayout;
	}

	@Override
	public AbstractOrderedLayout getOrderedLayout() {
		return horizontalLayout;
	}

	@Override
	public AbstractField getField() {
		throw new UnsupportedOperationException();
	}

}
