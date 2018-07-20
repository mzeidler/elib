package de.markozeidler.elib.builder;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;

public abstract class BaseBuilder<T> {

	public abstract AbstractComponent getComponent();
	
	public abstract AbstractOrderedLayout getOrderedLayout();
	
	public abstract AbstractField getField();
	
	public T id(String id) {
		getComponent().setId(id);
		return (T) this;
	}
	
	public T full() {
		getComponent().setSizeFull();
		return (T) this;
	}

	public T undefined() {
		getComponent().setSizeUndefined();
		return (T) this;		
	}
	
	public T spacing(boolean value) {
		getOrderedLayout().setSpacing(value);
		return (T) this;
	}
	
	public T spacing() {
		getOrderedLayout().setSpacing(true);
		return (T) this;
	}
	
	public T noSpacing() {
		getOrderedLayout().setSpacing(false);
		return (T) this;
	}
	
	public T margin(boolean value) {
		getOrderedLayout().setMargin(value);
		return (T) this;
	}
	
	public T margin() {
		getOrderedLayout().setMargin(true);
		return (T) this;
	}
	
	public T noMargin() {
		getOrderedLayout().setMargin(false);
		return (T) this;
	}
	
	public T enabled(boolean value) {
		getComponent().setEnabled(value);
		return (T) this;
	}
	
	public T enabled() {
		getComponent().setEnabled(true);
		return (T) this;
	}

	public T disabled() {
		getComponent().setEnabled(false);
		return (T) this;
	}
	
	public T visible(boolean value) {
		getComponent().setVisible(value);
		return (T) this;
	}
	
	public T visible() {
		getComponent().setVisible(true);
		return (T) this;
	}

	public T notVisible() {
		getComponent().setVisible(false);
		return (T) this;
	}
	
	public T style(String style) {
		getComponent().setStyleName(style);
		return (T) this;
	}
	
	public T addStyle(String style) {
		getComponent().addStyleName(style);
		return (T) this;
	}
	
	public T width(float width) {
		getComponent().setWidth(width, Unit.PIXELS);
		return (T) this;
	}
	
	public T width(float width, Unit unit) {
		getComponent().setWidth(width, unit);
		return (T) this;
	}	
	
	public T height(float height) {
		getComponent().setHeight(height, Unit.PIXELS);
		return (T) this;
	}
	
	public T height(float height, Unit unit) {
		getComponent().setHeight(height, unit);
		return (T) this;
	}
	
	public T caption(String caption) {
		getComponent().setCaption(caption);
		return (T) this;
	}
}
