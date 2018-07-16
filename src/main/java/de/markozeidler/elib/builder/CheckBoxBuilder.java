package de.markozeidler.elib.builder;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.CheckBox;

public class CheckBoxBuilder extends BaseBuilder<CheckBoxBuilder> {

	private CheckBox checkBox;
	
	public CheckBoxBuilder(String id, boolean showCaption) {
		this.checkBox = new CheckBox(id, showCaption);
	}
	
	public CheckBox build() {
		return this.checkBox;
	}

	@Override
	public AbstractComponent getComponent() {
		return checkBox;
	}

	@Override
	public AbstractOrderedLayout getOrderedLayout() {
		throw new UnsupportedOperationException();
	}

	@Override
	public AbstractField getField() {
		return checkBox;
	}
}
