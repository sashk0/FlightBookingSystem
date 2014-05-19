package info.oleksandr.www.entities;
 
 
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="tickets")

public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne 
	@JoinColumn(name="customer")
	private Customer customer;
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookingDate;	 
	private double price; 
	@ManyToOne 
	@JoinColumn(name="flight")
	private Flight flight;			
	private int status; 
	private String name;
	
	
	public Ticket() {	}
	public int getId() {
		return id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
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
	public Ticket(Customer customer, Date bookingDate,
			String name, Flight flight, int status, double price) {
		 
		this.price=price;
		this.customer = customer;
		this.bookingDate = bookingDate;
		this.name=name; 
		this.flight = flight;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", customer=" + customer + ", bookingDate="
				+ bookingDate + ", price=" + price + ", flight=" + flight
				+ ", status=" + status + ", name=" + name + "]";
	}
	
	public String getTimeLeft(){
		Date today = new Date();		
		long diff= today.getTime()-bookingDate.getTime();		
		long day = 1000*60*60*24;
		diff=day*3-diff;
		if (diff>0){
			long minutes = diff/ 60000 % 60;
			long hours = diff / (60*1000*60) %60;
			long days = diff/day;
			return (String.valueOf(days)+" days "+String.valueOf(hours)+" h"+String.valueOf(minutes)+" min");
		}		
		return "expired";
	} 
	
	public String getDetailedStatus(){
		if(this.status==0) return "pending";
		if(this.status==1) return "confirmed";
		if(this.status==-1) return "cancelled";
		if(this.status==-2) return "expired";
		return "";
	}
}
