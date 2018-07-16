package de.markozeidler.elib.builder;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Panel;

public class PanelBuilder extends BaseBuilder<PanelBuilder> {

	private Panel panel;

	public PanelBuilder(String id) {
		this.panel = new Panel(id);
	}

	public Panel build() {
		return this.panel;
	}

	@Override
	public AbstractComponent getComponent() {
		return panel;
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
