package de.markozeidler.elib.builder;

import java.util.Locale;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.DateField;

public class DateFieldBuilder extends BaseBuilder<DateFieldBuilder> {

	DateField dateField;

	public DateFieldBuilder(String id) {
		this.dateField = new DateField(id);
	}

	public DateField build() {
		return this.dateField;
	}

	public DateFieldBuilder dateFormat(String dateFormat) {
		this.dateField.setDateFormat(dateFormat);
		return this;
	}

	public DateFieldBuilder showISOWeekNumbers(boolean condition) {
		this.dateField.setShowISOWeekNumbers(condition);
		return this;
	}

	public DateFieldBuilder locale(Locale locale) {
		this.dateField.setLocale(locale);
		return this;
	}
	
	public DateFieldBuilder locale(String locale) {
		this.dateField.setLocale(new Locale(locale, locale));
		return this;
	}

	public DateFieldBuilder parseErrorMessage(String errorMessage) {
		this.dateField.setParseErrorMessage(errorMessage);
		return this;
	}
	@Override
	public AbstractComponent getComponent() {
		return dateField;
	}
	@Override
	public AbstractOrderedLayout getOrderedLayout() {
		throw new UnsupportedOperationException();
	}
	@Override
	public AbstractField getField() {
		return dateField;
	}
}
