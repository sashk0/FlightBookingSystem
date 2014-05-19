package info.oleksandr.www.dao;

import java.util.List;

import info.oleksandr.www.entities.Customer; 

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomerDao {
	@PersistenceContext
	private EntityManager em;
	
	public Customer getCustomerById(int id){
		try {
		Customer customer = em.createQuery("SELECT c FROM Customer c WHERE c.id=?1", Customer.class).setParameter(1, id).getSingleResult();
		return customer;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Customer findCustomerByPassport(String passport){		
		if (passport.length()>=4){
			try {
			Customer c = em.createQuery("SELECT c FROM Customer c WHERE c.passport=?1", Customer.class).setParameter(1, passport).getSingleResult();
			return c;
			} catch (NoResultException e) {
				return null;
			}
		}
		return null;		
	}
	
	public Customer findExactCustomer(String passport, String phone){
			try {
			Customer c = em.createQuery("SELECT c FROM Customer c WHERE c.passport=?1 AND c.phone=?2", Customer.class)
					.setParameter(1, passport).setParameter(2, phone).getSingleResult();
			return c;
			} catch (NoResultException e) {
				 
				return null;
			}		
	}
	
	@Transactional
	public void updateCustomer(Customer c){
		em.merge(c);
	}
	
	public List<Customer> getAllCustomers(){
		List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
		return customers;
	}
	
	public int addCustomerGetId(Customer c){
		em.persist(c);
		int id = em.createQuery("SELECT MAX(c.id) FROM Customer c", Integer.class).getSingleResult();
		return id;
	}
}
