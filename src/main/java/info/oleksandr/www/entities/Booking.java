package info.oleksandr.www.entities;

import java.io.Serializable;

public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;
private String customer, bookingDate, name, flight, status;

public Booking(String customer, String bookingDate, String name, String flight,
		String status) {
	super();
	this.customer = customer;
	this.bookingDate = bookingDate;
	this.name = name;
	this.flight = flight;
	this.status = status;
}

public String getCustomer() {
	return customer;
}

public void setCustomer(String customer) {
	this.customer = customer;
}

public String getBookingDate() {
	return bookingDate;
}

public void setBookingDate(String bookingDate) {
	this.bookingDate = bookingDate;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getFlight() {
	return flight;
}

public void setFlight(String flight) {
	this.flight = flight;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

}
