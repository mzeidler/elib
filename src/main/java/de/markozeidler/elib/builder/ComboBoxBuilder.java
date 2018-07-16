package de.markozeidler.elib.builder;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.ComboBox;

public class ComboBoxBuilder extends BaseBuilder<ComboBoxBuilder> {

	private ComboBox comboBox;

	public ComboBoxBuilder(String id) {
		this.comboBox = new ComboBox(id);
	}
	
	public ComboBox build() {
		return this.comboBox;
	}

	@Override
	public AbstractComponent getComponent() {
		return comboBox;
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
