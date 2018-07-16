package de.markozeidler.elib.builder;

import java.util.Date;

import com.vaadin.server.Resource;

public class UIBuilder {

	public VerticalLayoutBuilder VL() {
		return new VerticalLayoutBuilder();
	}

	public HorizontalLayoutBuilder HL() {
		return new HorizontalLayoutBuilder();
	}

	public ImageBuilder image(String resource) {
		return new ImageBuilder(resource);
	}

	public ButtonBuilder button(Resource icon, String description) {
		return new ButtonBuilder(icon, description);
	}
	
	public LabelBuilder label() {
		return new LabelBuilder();
	}
	
	public LabelBuilder label(String caption) {
		return new LabelBuilder(caption);
	}

	public CheckBoxBuilder checkBox(String id, boolean showCaption) {
		return new CheckBoxBuilder(id, showCaption);
	}

	public TextAreaBuilder textArea(String id) {
		return new TextAreaBuilder(id);
	}

	public DateFieldBuilder dateField(String id) {
		return new DateFieldBuilder(id);
	}

	public DateFieldBuilder dateField(String id, Date date) {
		return new DateFieldBuilder(id);
	}

	public ComboBoxBuilder comboBox(String id) {
		return new ComboBoxBuilder(id);
	}
	
	public PanelBuilder panel(String id) {
		return new PanelBuilder(id);
	}

	public TextFieldBuilder textField(String id) {
		return new TextFieldBuilder(id);
	}

}
