package de.markozeidler.elib.builder;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Image;

public class ImageBuilder extends BaseBuilder<ImageBuilder> {

	private Image image;
	
	public ImageBuilder(String resource) {
		this.image = new Image(null, new ThemeResource(resource));
	}
	
	public Image build() {
		return this.image;
	}

	@Override
	public AbstractComponent getComponent() {
		return image;
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
