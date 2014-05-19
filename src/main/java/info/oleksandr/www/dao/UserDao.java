package info.oleksandr.www.dao;

import info.oleksandr.www.entities.User;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public User findById(int id){
		User u = null;		
		u = em.find(User.class, id);
		return u;
	}
	
	@Transactional
	public void addUser(User u){
		em.persist(u);
	}
	@Transactional
	public List<String> getRoles(){
		List<String> roles= em.createQuery("SELECT DISTINCT u.role FROM User u",String.class).getResultList();
		return roles;
	}

	@Transactional
	public List<User> getAllUsersList(){
		List<User> list = null;
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
		list = query.getResultList();
		return list;
	}	 
	
	@Transactional
	public void deleteUser(User u){
		em.remove(em.merge(u));
	}
	
	@Transactional
	public void updateUser(User u){
		em.merge(u);
	}
	
	public User getUserForLogin(String username, String password){
		try{
		User u = em.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:password",User.class)
				.setParameter("username", username)
				.setParameter("password", password)
				.getSingleResult();
		return u;
		} catch (NoResultException e) {
			return null;
		}
	}
}
