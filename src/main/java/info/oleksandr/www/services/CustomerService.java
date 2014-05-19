package info.oleksandr.www.services;

import info.oleksandr.www.dao.CustomerDao;
import info.oleksandr.www.entities.Customer;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

@Named
public class CustomerService {
@Inject 
private CustomerDao customerDao;

@Transactional
public Customer getCustomerById(int id){
	return customerDao.getCustomerById(id);
	
}

@Transactional
public List<Customer> getAllCustomers(){
	return customerDao.getAllCustomers();
}
 

@Transactional
public int addCustomerGetId(Customer c){
	int id = customerDao.addCustomerGetId(c);
	return id;
}	

public Customer findCustomer(String passport){
	return customerDao.findCustomerByPassport(passport);
}

public Customer findCustomer(String passport, String phone){
	return customerDao.findExactCustomer(passport,phone);
}
@Transactional
public void updateCustomer(Customer c){	
	customerDao.updateCustomer(c);
}
}
