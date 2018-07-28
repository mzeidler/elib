package de.markozeidler.elib.window;

import java.io.Serializable;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.components.grid.SingleSelectionModel;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import de.markozeidler.elib.builder.UIBuilder;
import de.markozeidler.elib.entity.Document;
import de.markozeidler.elib.entity.Theme;
import de.markozeidler.elib.layouts.HeaderLayout;
import de.markozeidler.jpa.DataRepository;

public class MainWindow extends Window implements Serializable {

	private static final long serialVersionUID = 1L;

	private DataRepository dataRepository;

	private HeaderLayout headerLayout;

	private VerticalSplitPanel splitPanel;

	private Grid<Document> grid;

	private TextArea textArea;

	private UIBuilder uiBuilder;

	/**
	 * Windows
	 */
	private ThemesWindow themesWindow;

	private RemoveDocumentWindow removeDocumentWindow;

	private DocumentWindow documentWindow;

	private TextField titleFilter;
	
	private NativeSelect<Theme> themeFilter;
	
	/**
	 * Buttons
	 */

	private Button bAdd;

	private Button bRemove;

	private Button bEditDocument;

	private Button bEditDocumentContent;

	private Button bSave;

	private Button bAddCode;

	private Button bSelectionMode;

	private Button bThemes;

	@Inject
	public MainWindow(HeaderLayout headerLayout, UIBuilder uiBuilder, ThemesWindow themesWindow,
			DataRepository dataRepository, RemoveDocumentWindow removeDocumentWindow, DocumentWindow documentWindow) {
		this.headerLayout = headerLayout;
		this.uiBuilder = uiBuilder;
		this.themesWindow = themesWindow;
		this.dataRepository = dataRepository;
		this.removeDocumentWindow = removeDocumentWindow;
		this.documentWindow = documentWindow;
	}

	public void init() {
		initWindow();
		initComponents();
		initGrid();
		initButtons();
		headerLayout.init();
		setContent(initLayouts());
	}

	private void initWindow() {
		setWidth(100, Unit.PERCENTAGE);
		setHeight(100, Unit.PERCENTAGE);
		setClosable(false);
		setDraggable(false);
		setResizable(false);
		center();
	}

	private void initComponents() {
		textArea = new TextArea();
		textArea.setSizeFull();
	}

	private void initGrid() {

		// Initialize Grid
		grid = new Grid<Document>();
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.SINGLE);

		// Columns
		grid.addColumn(Document::getTheme).setCaption("Theme").setId("theme");
		grid.addColumn(Document::getTitle).setCaption("Title").setId("title");

		grid.addColumn(Document::getCreated, new DateRenderer("%1$td.%1$tm.%1$tY", Locale.ENGLISH))
				.setCaption("Created");
		grid.addColumn(Document::getUpdated, new DateRenderer("%1$td.%1$tm.%1$tY", Locale.ENGLISH))
				.setCaption("Updated");

		// Filter
		titleFilter = new TextField();
		titleFilter.setWidth("100%");
		titleFilter.addStyleName(ValoTheme.TEXTFIELD_TINY);
		titleFilter.setPlaceholder("Filter");
		titleFilter.addValueChangeListener(event -> {
			dataRepository.getDocumentDataProvider().refreshAll();
		});

		themeFilter = new NativeSelect<>(null, dataRepository.findAllThemes());
		themeFilter.setWidth("100%");
		themeFilter.addValueChangeListener(event -> {
			dataRepository.getDocumentDataProvider().refreshAll();
		});

		SerializablePredicate<Document> filter = new SerializablePredicate<Document>() {
			@Override
			public boolean test(Document document) {
				
				// Theme Filter
				boolean matchesTheme = true;
				if (themeFilter.getSelectedItem().isPresent()) {
					Integer themeId = document.getTheme() == null ? null : document.getTheme().getId();
					matchesTheme = themeFilter.getSelectedItem().get().getId().equals(themeId);
				}

				// Title Filter
				boolean matchesTitle = true;
				if (!"".equals(titleFilter.getValue())) {
					matchesTitle = document.getTitle().toLowerCase().contains(titleFilter.getValue().toLowerCase());					
				}
				
				return matchesTitle && matchesTheme;
			}
		};

		dataRepository.getDocumentDataProvider().setFilter(filter);


		HeaderRow filterRow = grid.appendHeaderRow();
		filterRow.getCell("title").setComponent(titleFilter);
		filterRow.getCell("theme").setComponent(themeFilter);

		grid.setDataProvider(dataRepository.getDocumentDataProvider());
		dataRepository.setGrid(grid);
	}

	private void initGrid_old() {

		// **********************************************************************************
		// TODO: UNDER CONSTRUCTION

		grid.addSelectionListener(event -> {
			Set<Document> selected = event.getAllSelectedItems();

			if (selected.size() > 0) {

				Document doc = (Document) selected.toArray()[0];
				textArea.setValue(doc.getTitle());

				if (doc.getContent() != null) {
					// textArea.setValue(doc.getContent());
					textArea.setValue(doc.getTitle());
				}
			}

			Notification.show(selected.size() + " items selected");
		});
		// **********************************************************************************
	}

	private void initButtons() {

		bAdd = uiBuilder.button(VaadinIcons.FILE_ADD, "Add Document").build();
		bAdd.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				documentWindow.init(null);
				UI.getCurrent().addWindow(documentWindow);
			}
		});

		bRemove = uiBuilder.button(VaadinIcons.TRASH, "Remove Documents").build();
		bRemove.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Set<Document> selectedDocuments = grid.getSelectedItems();
				if (selectedDocuments.size() > 0) {
					removeDocumentWindow.init(selectedDocuments);
					UI.getCurrent().addWindow(removeDocumentWindow);
				}
			}
		});

		bEditDocument = uiBuilder.button(VaadinIcons.EDIT, "Edit Document").build();
		bEditDocument.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Set<Document> selectedDocuments = grid.getSelectedItems();
				if (selectedDocuments.size() == 1) {
					selectedDocuments.forEach(action -> {
						documentWindow.init(action);
						UI.getCurrent().addWindow(documentWindow);
					});
				}
			}
		});

		bEditDocumentContent = uiBuilder.button(VaadinIcons.EDIT, "Edit Document Content").build();
		bEditDocumentContent.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//
			}
		});

		bSave = uiBuilder.button(VaadinIcons.DATABASE, "Save Document").build();
		bSave.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//

			}
		});

		bAddCode = uiBuilder.button(VaadinIcons.FILE_CODE, "Add Code Snippet").build();
		bAddCode.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//

			}
		});

		bSelectionMode = uiBuilder.button(VaadinIcons.TASKS, "Change Selection Mode").build();
		bSelectionMode.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (grid.getSelectionModel() instanceof SingleSelectionModel) {
					grid.setSelectionMode(SelectionMode.MULTI);
				} else {
					grid.setSelectionMode(SelectionMode.SINGLE);
				}
			}
		});

		bThemes = uiBuilder.button(VaadinIcons.FILE_TREE, "Manage Themes").build();
		bThemes.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				themesWindow.init();
				UI.getCurrent().addWindow(themesWindow);
			}
		});
	}

	private VerticalLayout initLayouts() {

		VerticalLayout upperToolbar = uiBuilder.VL().undefined().build();
		upperToolbar.addComponents(bAdd, bEditDocument, bRemove, bSelectionMode, bThemes);

		VerticalLayout lowerToolbar = uiBuilder.VL().undefined().build();
		lowerToolbar.addComponents(bEditDocumentContent, bAddCode, bSave);

		HorizontalLayout mainUpperLayout = uiBuilder.HL().margin().full().build();
		mainUpperLayout.addComponents(upperToolbar, grid);
		mainUpperLayout.setExpandRatio(grid, 1);

		HorizontalLayout mainLowerLayout = uiBuilder.HL().margin().full().build();
		mainLowerLayout.addComponents(lowerToolbar, textArea);
		mainLowerLayout.setExpandRatio(textArea, 1);

		splitPanel = new VerticalSplitPanel();
		splitPanel.setSizeFull();
		splitPanel.setSplitPosition(400, Unit.PIXELS);
		splitPanel.setFirstComponent(mainUpperLayout);
		splitPanel.setSecondComponent(mainLowerLayout);
		splitPanel.addStyleName(ValoTheme.SPLITPANEL_LARGE);

		VerticalLayout layout = uiBuilder.VL().noMargin().noSpacing().full().build();
		layout.addComponents(headerLayout, splitPanel);
		layout.setExpandRatio(splitPanel, 1);

		return layout;
	}
}
