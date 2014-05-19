package info.oleksandr.www.beans;

import info.oleksandr.www.converters.TicketDataModel;
import info.oleksandr.www.entities.Customer;
import info.oleksandr.www.entities.Flight; 
import info.oleksandr.www.entities.Ticket;
import info.oleksandr.www.services.CustomerService;
import info.oleksandr.www.services.TicketService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean; 
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

@Named
@SessionScoped
@ManagedBean(eager = true)
public class TicketBean {
  
	private int id;
	private Customer customer, person;
	private Date bookingDate;
	private double price;
	private Flight flight, currentFlight;
	private int status;
 
	@Inject
	private TicketService ticketService;
	@Inject
	private CustomerService customerSerice;
	private List<Ticket> ticketList, personalTickets,selectedTickets, ticketBasket;
	private boolean actualOnly, termsAccepted, disableConfirmButton, disableConfirmButton2,disableCancelButton; 
	

	private TicketDataModel tdm;
	private Ticket selectedTicket, currentTicket;
 
	private int passengers; 

	 

	public TicketBean() {
		 
	} 
	public boolean getTermsAccepted() {
		return termsAccepted;
	}
	public boolean isDisableConfirmButton() {
		return disableConfirmButton;
	}
	public boolean isDisableCancelButton() {
		return disableCancelButton;
	}
	public boolean isDisableConfirmButton2() {
		return disableConfirmButton2;
	}
	public void setDisableConfirmButton(boolean disableConfirmButton) {
		this.disableConfirmButton = disableConfirmButton;
	}
	public void resetTerms(){
		this.termsAccepted = false;
		}
	public void setTermsAccepted(boolean termsAccepted) {
		this.termsAccepted = termsAccepted;
	}
	public List<Ticket> getSelectedTickets() {
		return selectedTickets;
	}

	public void setSelectedTickets(List<Ticket> selectedTickets) {
		this.selectedTickets = selectedTickets;
	}

	public List<Ticket> getTicketBasket() {
		return ticketBasket;
	}

	public void setTicketBasket(List<Ticket> ticketBasket) {
		this.ticketBasket = ticketBasket;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public Customer getPerson() {
		return person;
	}	
	
	public void setPerson(Customer person) {
		this.person = person;
	}

	public List<Ticket> getPersonalTickets() {
		return personalTickets;
	}

	public void setPersonalTickets(List<Ticket> personalTickets) {
		this.personalTickets = personalTickets;
	}

	public Flight getCurrentFlight() {
		return currentFlight;
	}

	public void setCurrentFlight(Flight currentFlight) {
		this.currentFlight = currentFlight;
	}

	public Ticket getCurrentTicket() {
		return currentTicket;
	}

	public void setCurrentTicket(Ticket currentTicket) {
		this.currentTicket = currentTicket;
	}

	/**
	 * @return the actualOnly
	 */
	public boolean isActualOnly() {
		return actualOnly;
	}

	/**
	 * @param actualOnly the actualOnly to set
	 */
	public void setActualOnly(boolean actualOnly) {
		this.actualOnly = actualOnly;
	}

	@PostConstruct
	public void init() {
		ticketList = null;
		bookingDate = new Date();
		actualOnly=true; 
		price = 0;
		status = 0;
		customer=null;
		refresh();
		ticketBasket=new ArrayList<Ticket>();
		this.disableConfirmButton=true;
		this.disableConfirmButton2=true;
		this.disableCancelButton=true;
	}

	public void refresh() {
		System.err.println("TicketBean: customer "+customer); 	
		 
		 passengers = 1;
		 
		ticketList = actualOnly ? ticketService.getActualTickets() : ticketService.getAllTickets();
		personalTickets = actualOnly ? ticketService.getActualPersonalBookings(this.customer) : ticketService.getPersonalBookings(this.customer);
		 
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public void setCustomerById(int id) {		
		this.customer = customerSerice.getCustomerById(id);
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

	public Ticket getSelectedTicket() {
		return selectedTicket;
	}

	public void setSelectedTicket(Ticket selectedTicket) {
		this.selectedTicket = selectedTicket;
	}

	 

	 
	public TicketDataModel getTdm() {
		return tdm;
	}

	public void setTdm(TicketDataModel tdm) {
		this.tdm = tdm;
	}

	public void removeSelectedTicket() {
		System.err.println("Removing ticket " + selectedTicket);
		ticketService.removeTicket(selectedTicket);
	}

	public void addTicketToBasket(){
		if (this.flight == null) {
			MessageBean.showAlert("Error:", "No flight selected!");
			return;
		}
		System.err.println("passengers:"+passengers);		
		for(int i=1; i<=passengers;i++){
			Ticket t = new Ticket(this.customer, new Date(), "",
				this.flight, 0, flight.getSeatPrice());
			ticketBasket.add(t);
			System.out.println("added to basket ticket:" + t);
		}
		
		refresh();
		
		
	}
	
	public String bookTicket() {
		if (this.customer == null) {
			System.err.println("No customer!");
			return "";
		}
		if (this.flight == null) {
			System.err.println("No flight!");
			return "";
		}
		if (!hasAllNames(ticketBasket)){
			System.err.println("Not all passengers are named!");
			return "";
		}
		System.err.println("Booked tickets:");	 
		for (Ticket t: ticketBasket){
			t.setCustomer(this.customer);			
			ticketService.addTicket(t);
			System.out.println(t);			
		}
		refresh();
		return "CustomerBooking";
		
	}
	
	
	
	public void loadPersonalBookings(SelectEvent e) {
		this.customer = (Customer) e.getObject();
		System.err.println("TicketBean: loading personal bookings for"
				+ this.customer);
		personalTickets = actualOnly ? ticketService.getActualPersonalBookings(this.customer) : ticketService.getPersonalBookings(this.customer);
		
		System.out.println(personalTickets);
	}
	
	 
	
	public void confirmSelectedTickets(){		
		 if (selectedTickets!=null){
			 for(Ticket t:selectedTickets){
				 if(t.getStatus()==0){
					 System.err.println("TicketBean: confirming ticket for"+ t.getCustomer());
					 t.setStatus(1);				
					 ticketService.updateTicket(t);
				 }
			 }
			 selectedTicket=null;
			 refresh();
			 
		 }
	}
	
	public void cancelSelectedTickets(){
		System.err.println("Cancelling");
		if (selectedTickets!=null){
			 for(Ticket t:selectedTickets){
				 System.err.println("TicketBean: cancelling ticket for"+ t.getCustomer());
				 t.setStatus(-1);				
				 ticketService.updateTicket(t);
				 
			 }
			 refresh();
			 
		 }
	}
	
	public void enableCancelButton(){
		if(this.selectedTickets!=null&&selectedTickets.size()>0){
			this.disableCancelButton=false;
		} else { this.disableCancelButton=true;}
	}
	
	public void enableConfirmButton(){
		if(this.ticketBasket!=null){
			if (hasAllNames(ticketBasket)){
				this.disableConfirmButton=false;
			}
		} else { this.disableConfirmButton=true;}
		
	}
	
	public void enableConfirmButton2(){
		if(this.selectedTickets!=null&selectedTickets.size()>0){
			 {
				this.disableConfirmButton2=false;
			};
		} else { this.disableConfirmButton2=true;}
		
	}
	
	
	
	public void onCellEdit(CellEditEvent event) {  
        Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue();
        if(newValue != null && !newValue.equals(oldValue)) {  
            
        }  
        enableConfirmButton();
    }  
	
	public void deleteFromBasket(Ticket t){
		System.err.println("TIcketBean: removing"+currentTicket+"from basket");
		ticketBasket.remove(t);
		
	}
	
	public String getBookingSum(){
		if (ticketBasket!=null) {
			double sum=0; int qnt=ticketBasket.size();
			for (Ticket t: ticketBasket){
				sum+=t.getPrice();				
			}
			return "Total: "+qnt+" tickets = $"+sum;
		}
		return "";
	}
	
	public boolean hasAllNames(List<Ticket> list){
		for (Ticket t:list){
			if (t.getName()=="") return false;
		}
		return true;
	}
	
	
	
	public List<Ticket> getAllExpiredTickets() {		
		return ticketService.getAllExpiredTickets();
	}
	
	public void updateAllExpiredTickets(){		
		List<Ticket> expired = ticketService.getAllExpiredTickets();
		if (expired!=null){
			 for(Ticket t:expired){
				 System.err.println("TicketBean: expiring ticket"+ t.getCustomer());
				 t.setStatus(-2);				
				 ticketService.updateTicket(t);	
				 
				 String msg=""+expired.size() +" tickets have become expired since last refresh.";
				 MessageBean.showAlert("Some updates!", msg);
				 
			 }
			
			 refresh();
			 
		 }
	}
}
