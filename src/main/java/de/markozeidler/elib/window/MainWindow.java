package de.markozeidler.elib.window;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.SingleSelectionModel;
import com.vaadin.ui.themes.ValoTheme;

import de.markozeidler.elib.builder.UIBuilder;
import de.markozeidler.elib.entity.Document;
import de.markozeidler.elib.entity.Theme;
import de.markozeidler.elib.layouts.HeaderLayout;
import de.markozeidler.jpa.JPAHandler;

public class MainWindow extends Window implements Serializable {

	private static final long serialVersionUID = 1L;

	private JPAHandler jpaHandler;

	private HeaderLayout headerLayout;

	private VerticalSplitPanel splitPanel;

	private Grid<Document> grid;
	
	private ListDataProvider<Document> dataProvider;

	private TextArea textArea;

	private UIBuilder uiBuilder;
	
	private ThemesWindow themesWindow;
	
	private DocumentWindow documentWindow;
	
	private List<Document> documents;
	/**
	 * Buttons
	 */
	
	private Button bAdd;
	
	private Button bRemove;
	
	private Button bEdit;
	
	private Button bSave;
	
	private Button bAddCode;
	
	private Button bSelectionMode;
	
	private Button bThemes;
	
	@Inject
	public MainWindow(JPAHandler jpaHandler, HeaderLayout headerLayout, UIBuilder uiBuilder, ThemesWindow themesWindow, DocumentWindow documentWindow) {
		this.jpaHandler = jpaHandler;
		this.headerLayout = headerLayout;
		this.uiBuilder = uiBuilder;
		this.themesWindow = themesWindow;
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
		grid.addColumn(Document::getTheme).setCaption("Theme");
		grid.addColumn(Document::getTitle).setCaption("Title");
		grid.addColumn(Document::getCreated).setCaption("Created");
		grid.addColumn(Document::getUpdated).setCaption("Updated");
		
		// Data		
		documents = jpaHandler.findAll(new Document());
		dataProvider = new ListDataProvider<Document>(documents);
		grid.setDataProvider(dataProvider);
	}
	
	private void initGrid_old() {

		//**********************************************************************************
		// TODO: UNDER CONSTRUCTION
		
		grid.addSelectionListener(event -> {
			Set<Document> selected = event.getAllSelectedItems();

			if (selected.size() > 0) {

				Document doc = (Document) selected.toArray()[0];
				textArea.setValue(doc.getTitle());

				if (doc.getContent() != null) {
					//textArea.setValue(doc.getContent());
					textArea.setValue(doc.getTitle());
				}
			}

			Notification.show(selected.size() + " items selected");
		});
		//**********************************************************************************
	}
	
	private void initButtons() {
		
		bAdd = uiBuilder.button(VaadinIcons.FILE_ADD, "Add Document").build();
		bAdd.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				//**********************************************************************************
				// TODO: UNDER CONSTRUCTION
				
				documentWindow.init(null);
				UI.getCurrent().addWindow(documentWindow);
				documentWindow.addCloseListener(new CloseListener() {
					@Override
					public void windowClose(CloseEvent event) {
						if (documentWindow.isPersist()) {
							Document newDocument = documentWindow.getDocument();
							if (newDocument != null) {
								jpaHandler.save(newDocument);
								documents.add(newDocument);
								dataProvider.refreshAll();							
							}							
						}
					}
				});
				
				//**********************************************************************************
			}
		});
		
		bRemove = uiBuilder.button(VaadinIcons.TRASH, "Remove Documents").build();
		bRemove.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Set<Document> selectedDocuments = grid.getSelectedItems();
				System.out.println("MARKOTEST: selectedDocuments.size()=" + selectedDocuments.size());
				if (selectedDocuments.size() > 0) {
					String themeText = selectedDocuments.size() == 1 ? "this Theme" : "these Themes";
					ConfirmationWindow confirmationWindow = new ConfirmationWindow("Are you sure that you want to delete " + themeText + "?", "Yes", "No");
					UI.getCurrent().addWindow(confirmationWindow);
					confirmationWindow.addCloseListener(new CloseListener() {
						@Override
						public void windowClose(CloseEvent event) {
							if ("Yes".equals(confirmationWindow.getSelectedOption())) {
								selectedDocuments.forEach(action -> {
									jpaHandler.remove(action);
									documents.remove(action);
								});
								dataProvider.refreshAll();
							}
						}
					});					
				}
			}
		});
		
		bEdit = uiBuilder.button(VaadinIcons.EDIT, "Edit Document").build();
		bEdit.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Set<Document> selectedDocuments = grid.getSelectedItems();
				selectedDocuments.forEach(action -> {
					action.setUpdated(new Date());
				});
				
				dataProvider.refreshAll();
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
		upperToolbar.addComponents(bAdd, bRemove, bSelectionMode, bThemes);

		VerticalLayout lowerToolbar = uiBuilder.VL().undefined().build();
		lowerToolbar.addComponents(bEdit, bAddCode, bSave);		

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
