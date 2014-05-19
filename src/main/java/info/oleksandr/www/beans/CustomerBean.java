package info.oleksandr.www.beans;

import java.util.List;

import info.oleksandr.www.converters.CustomerDataModel;
import info.oleksandr.www.entities.Customer;
import info.oleksandr.www.services.CustomerService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped; 
import javax.inject.Inject;
import javax.inject.Named;
 

@Named
@SessionScoped
@ManagedBean(eager = true)
public class CustomerBean {
	private String passport;
	private String name;
	private String surname;
	private String phone;
	private String email;
	private int id;
	
	@Inject
	private CustomerService customerService;
	private Customer selectedCustomer, currentCustomer;
	private List<Customer> customers, filteredCustomers;
	private CustomerDataModel customerModel;
	private boolean disableBuyBtn, disableCheckButton;
	
	public boolean isDisableCheckButton() {
		return disableCheckButton;
	}
	public void setDisableCheckButton(boolean disableCheckButton) {
		this.disableCheckButton = disableCheckButton;
	}
	public boolean getDisableBuyBtn() {
		return disableBuyBtn;
	}
	public String getPassport() {
		return passport;
	}

	/**
	 * @return the filteredCustomers
	 */
	public List<Customer> getFilteredCustomers() {
		return filteredCustomers;
	}
	/**
	 * @param filteredCustomers the filteredCustomers to set
	 */
	public void setFilteredCustomers(List<Customer> filteredCustomers) {
		this.filteredCustomers = filteredCustomers;
	}
	public CustomerService getCustomerService() {
		return customerService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public CustomerDataModel getCustomerModel() {
		return customerModel;
	}

	public void setCustomerModel(CustomerDataModel customerModel) {
		this.customerModel = customerModel;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Customer getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;		 
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	} 

	@PostConstruct
	public void init(){
		customers = null;
	 
		setCustomerModel(new CustomerDataModel(customers));
		currentCustomer=null;
		reset();
	}

	public void reset(){
		 passport="";
		 name="";
		 surname="";
		 phone="";
		 email="";
		 customers=customerService.getAllCustomers();
		 disableBuyBtn = true;
		 disableCheckButton = true;
		 selectedCustomer=null;
	}
	
	public void suggestCusomer(){
		System.err.println("CustomerBean: Suggesting customer...");
		if (this.passport.length()>4 && customerService.findCustomer(this.passport)!=null){
			Customer c = customerService.findCustomer(this.passport);
			this.name=c.getName();
			this.surname=c.getSurname();
		}
		
	}
	
	public void findExactCustomer(){
		if (passport!=null && phone!=null){
			System.err.println("CustomerBean: Looking for "+this.passport+" "+this.phone);
			this.selectedCustomer = customerService.findCustomer(this.passport, this.phone);			
			System.err.println("CustomerBean: Found "+this.selectedCustomer);
		} 			 
	}	
	
	public String goToCustomerBookings(){
		if (selectedCustomer!=null){
			 return "/pages/CustomerBooking";
		} 
		MessageBean.showError("Error:", "No such customer found!");
		reset();
		return "";
	}	
	 
	
	public void addCustomer(){		
		int id;
		if(customerService.findCustomer(this.passport)==null){
			Customer c = new Customer(passport, name, surname, phone, email);
			 id =customerService.addCustomerGetId(c);
			 System.err.println("CustomerBean: created new customer at "+id);
		} else {
			Customer c = customerService.findCustomer(this.passport);
			 id = c.getId();			 
			 c.setEmail(this.email);
			 c.setPhone(this.phone);
			 customerService.updateCustomer(c);
			 System.err.println("CustomerBean: customer updated at "+id);
		}
		this.setCurrentCustomer(customerService.getCustomerById(id));	
		reset();		
	}
	
	public void enableBuyButton() {		 
		if (this.name != "" && this.passport != ""
				&& this.phone != ""
				&& this.surname != ""
				&& this.email != "") {
			disableBuyBtn = false;
		} else {
			disableBuyBtn = true;
		}
		 
	}
	
	public void enableCheckButton() {		 
		if (this.passport != ""
				&& this.phone != "") {
			disableCheckButton = false;
		} else {
			disableCheckButton = true;
		}
		 
	}
	
	public void test(){
		System.err.println("test!");
	}
}
