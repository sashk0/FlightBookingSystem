package info.oleksandr.www.beans;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import info.oleksandr.www.entities.Flight;
import info.oleksandr.www.entities.Ticket;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

 

import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.springframework.stereotype.Component;
@Component
@ManagedBean(eager=true)
@SessionScoped
public class MessageBean {
	private String msg;
	private Flight flight;
	private  List<Ticket> ticketBasket;
	/**
	 * @return the ticketBasket
	 */
	public List<Ticket> getTicketBasket() {
		return ticketBasket;
	}


	/**
	 * @param ticketBasket the ticketBasket to set
	 */
	public void setTicketBasket(List<Ticket> ticketBasket) {
		this.ticketBasket = ticketBasket;
	}


	/**
	 * @return the flight
	 */
	public Flight getFlight() {
		return flight;
	}


	/**
	 * @param flight the flight to set
	 */
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	@PostConstruct
	public void init(){
		ticketBasket=new ArrayList<Ticket>();
	}

	public MessageBean(){}
	
	 
	public void putMessage(Object o){
		this.msg=o.toString(); 
	}
	
	public void showFlight() {  
		
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		msg=params.get("selectedFlight");
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO,"You have selected:", msg));  
      
	}
	
	public void showConfirmation(){
		String msg="You've just booked "+ticketBasket.size()+" tickets:";
		if (ticketBasket.size()>0){
			for (Ticket t:ticketBasket){
				msg+=t.getName()+": "+t.getFlight()+" "+t.getPrice()+"\n";
			}
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Booking complete!", msg));  
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Be attentive!", "To complete booking procedure you should make a necessary payment during three days and inform Booking Account Manager of our company."));  
		}
	}
	
	public static void showError(String head, String msg){
		FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_ERROR,
					head, msg));
	}
	
	public static void showAlert(String head, String msg){

		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_WARN,
					head, msg));
	}
}
