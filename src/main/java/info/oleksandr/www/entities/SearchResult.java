package info.oleksandr.www.entities;

import java.io.Serializable;
 
 
public class SearchResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private String flightNumber;
	private String from;
	private String departure;
	private String to;
	private String arrival;
	private double price;
	private int available;

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public SearchResult(String flightNumber, String from, String departure,
			String to, String arrival, double price, int available) {
		super();
		this.flightNumber = flightNumber;
		this.from = from;
		this.departure = departure;
		this.to = to;
		this.arrival = arrival;
		this.price = price;
		this.available = available;
	}

}
