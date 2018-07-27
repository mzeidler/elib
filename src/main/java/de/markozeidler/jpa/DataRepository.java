package de.markozeidler.jpa;

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
	
	/**
	 * 
	 * Documents
	 * 
	 */
	public ListDataProvider<Document> getDocumentDataProvider() {
		if (documents == null) {
			System.out.println("MARKOTEST: updating documents");
			documents = jpaHandler.findAll(new Document());
		}
		if (documentsDataProvider == null) {
			System.out.println("MARKOTEST: initializing documentsDataProvider");
			documentsDataProvider = new ListDataProvider<Document>(documents);
		}
		return documentsDataProvider;
	}
	
	public void saveDocument(Document entity) {
		jpaHandler.save(entity);
		documents.add(entity);
		documentsDataProvider.refreshAll();	
		grid.select(entity);
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
	}	
}
