package de.markozeidler.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;

import de.markozeidler.elib.entity.Document;
import de.markozeidler.elib.entity.Theme;

@Singleton
public class DataRepository {

	private JPAHandler jpaHandler;
	
	private List<Document> documents;
	
	private ListDataProvider<Document> documentsDataProvider;
	
	private Grid<Document> grid;
	
	@Inject
	public DataRepository(JPAHandler jpaHandler) {
		this.jpaHandler = jpaHandler;
	}
	
	public Grid<Document> getGrid() {
		return grid;
	}

	public void setGrid(Grid<Document> grid) {
		this.grid = grid;
	}

	/**
	 * 
	 * Themes
	 * 
	 */	
	public List<Theme> findAllThemes() {
		return jpaHandler.findAll(new Theme());
	}
	
	public void saveTheme(Theme theme) {
		jpaHandler.save(theme);
	}
	
	public void removeTheme(Theme theme) {
		jpaHandler.remove(theme);
	}
	
	public void updateTheme(Theme theme) {
		jpaHandler.update(theme);
		// Update Grid
		documentsDataProvider.refreshAll();	
	}
	
	/**
	 * 
	 * Documents
	 * 
	 */
	public ListDataProvider<Document> getDocumentDataProvider() {
		if (documents == null) {
			documents = jpaHandler.findAll(new Document());
		}
		if (documentsDataProvider == null) {
			documentsDataProvider = new ListDataProvider<Document>(documents);
		}
		return documentsDataProvider;
	}
	
	public void saveDocument(Document entity) {
		jpaHandler.save(entity);
		documents.add(entity);
		documentsDataProvider.refreshAll();	
		grid.select(entity);
		grid.scrollTo(new ArrayList(((ListDataProvider) grid.getDataProvider()).getItems()).indexOf(entity));
	}
	
	public void removeDocument(Document entity) {
		jpaHandler.remove(entity);
		documents.remove(entity);
		documentsDataProvider.refreshAll();			
	}	
	
	public void removeDocuments(Set<Document> entities) {
		entities.forEach(entity -> {
			jpaHandler.remove(entity);
			documents.remove(entity);
		});		
		documentsDataProvider.refreshAll();	
	}		
	
	public void updateDocument(Document entity) {
		jpaHandler.update(entity);
		documentsDataProvider.refreshAll();	
		grid.select(entity);
		grid.scrollTo(new ArrayList(((ListDataProvider) grid.getDataProvider()).getItems()).indexOf(entity));
	}	
}
