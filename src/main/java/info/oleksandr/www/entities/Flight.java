package info.oleksandr.www.entities;

 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
 

@Entity
@Table(name="flights")
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	private String flightNumber;
	@ManyToOne 
	@JoinColumn(name="departureAirport")
	private Airport departureAirport;
	@Temporal(TemporalType.TIMESTAMP)
	private Date departureTime;
	@ManyToOne 
	@JoinColumn(name="arrivalAirport" )
	private Airport arrivalAirport;
	 @Temporal(TemporalType.TIMESTAMP)
	private  Date arrivalTime;
	private int seatsAvailable;
	private double seatPrice;
	private boolean cancelled; 
	@OneToMany(mappedBy="flight")
	private List<Ticket> tickets;
	
	public double getSeatPrice() {
		
		return seatPrice;
	}
	
	public void setSeatPrice(double seatPrice) {
		this.seatPrice = seatPrice;
	}

	public Flight(){}
	
	public Flight(String flightNumber, Airport departureAirport,
			Date departureTime, Airport arrivalAirport, Date arrivalTime,
			int seatsAvailable, double seatPrice) {
 
		this.flightNumber = flightNumber;
		this.departureAirport = departureAirport;
		this.departureTime = departureTime;
		this.arrivalAirport = arrivalAirport;
		this.arrivalTime = arrivalTime;
		this.seatsAvailable = seatsAvailable;
		this.seatPrice = seatPrice;
	}
	public Flight(Flight updFlight){
		this.flightNumber = updFlight.flightNumber;
		this.departureAirport = updFlight.departureAirport;
		this.departureTime = updFlight.departureTime;
		this.arrivalAirport = updFlight.arrivalAirport;
		this.arrivalTime = updFlight.arrivalTime;
		this.seatsAvailable = updFlight.seatsAvailable;
		this.seatPrice = updFlight.seatPrice;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public boolean isCancelled() {
//		return cancelled;
//	}
	
	public String getCancelled(){
		if (cancelled) return "Cancelled";
		return "On time";
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Airport getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(Airport departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getDepartureTimeF() {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		String dt=df.format(this.departureTime);
		return dt;
		 
	}
	
	public String getArrivalTimeF() {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		String dt=df.format(this.arrivalTime);
		return dt;
		 
	}
	

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
	
		this.departureTime = departureTime;
	}

	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(Airport arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public Date getArrivalTime() {
		return  arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	 public int getTicketsN(){
		 int n=0;
		 for (Ticket t:tickets){
			 if (t.getStatus()>=0) {
				 n++;
			 }
		 }
		 return seatsAvailable-n;
	 }

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return  flightNumber;
	}
	
	public String getDuration(){
		long diff= arrivalTime.getTime()-departureTime.getTime();		
		long minutes = diff/ 60000 % 60;
		long hours = diff / (60*1000*60) %60;
		
		return (String.valueOf(hours)+"h "+String.valueOf(minutes))+"m";
	}
	
}
