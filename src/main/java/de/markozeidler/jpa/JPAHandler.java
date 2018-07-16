package de.markozeidler.jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.markozeidler.elib.entity.User;

@Singleton
public class JPAHandler {

	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = Persistence.createEntityManagerFactory("filerepo").createEntityManager();
		}
		return entityManager;
	}

	public <T> List<T> findAll(T entityType) {
		TypedQuery<T> q = (TypedQuery<T>) getEntityManager().createQuery("SELECT f FROM " + entityType.getClass().getSimpleName() + " f", entityType.getClass());
		List<T> entities = q.getResultList();
		return entities;
	}
	
	public <T> T find(T entityType, Integer id) {
		return (T) getEntityManager().find(entityType.getClass(), id);
	}
	
	public void save(Object entity) {
		getEntityManager().getTransaction().begin();
		getEntityManager().persist(entity);
		getEntityManager().getTransaction().commit();
	}
	
	public User checkUser(String username, String password) {
		User user = null;
		TypedQuery<User> q = (TypedQuery<User>) getEntityManager().createQuery("SELECT u FROM User u WHERE u.username=\"" + username + "\"", User.class);
		List<User> users = q.getResultList();
		if (users.size() == 1) {
			user = users.get(0);
			if (!encode(password).equals(user.getPwd())) {
				user = null;
			}
		}
		return user;
	}
	
	public boolean checkPassword(User user, String password) {
		return encode(password).equals(user.getPwd());
	}
	
	public String encode(String password) {
		String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
	}
}
