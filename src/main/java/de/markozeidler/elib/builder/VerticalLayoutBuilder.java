package de.markozeidler.elib.builder;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.VerticalLayout;

public class VerticalLayoutBuilder extends BaseBuilder<VerticalLayoutBuilder>{

	private VerticalLayout verticalLayout;
	
	public VerticalLayoutBuilder() {
		this.verticalLayout = new VerticalLayout();
	}
	
	public VerticalLayout build() {
		return this.verticalLayout;
	}

	@Override
	public AbstractComponent getComponent() {
		return verticalLayout;
	}

	@Override
	public AbstractOrderedLayout getOrderedLayout() {
		return verticalLayout;
	}

	@Override
	public AbstractField getField() {
		// TODO Auto-generated method stub
		return null;
	}

}
