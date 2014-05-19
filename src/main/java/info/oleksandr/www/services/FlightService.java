package info.oleksandr.www.services;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import info.oleksandr.www.dao.FlightDao;
import info.oleksandr.www.dao.TicketDao;
import info.oleksandr.www.entities.Airport;
import info.oleksandr.www.entities.Flight;

@Named
public class FlightService {

	@Inject
	private FlightDao flightDao;
	@Inject
	private TicketDao ticketDao;

	public Flight getFlightById(int id) {
		return flightDao.getFlightById(id);
	}

	@Transactional
	public void addFlight(Flight f) {
		if (f.getDepartureAirport() != null && f.getArrivalAirport() != null) {
			flightDao.addFlight(f);
		} else {
			System.err
					.println("FlightService: You want to add flight without airports!");
		}
	}

	public List<Flight> getAllFlights() {
		return flightDao.getAllFlights();
	}
	
	public List<Flight> getAllFutureFlights() {
		return flightDao.getAllFutureFlights();
	}
	
	public List<Flight> getAllFutureFlights(int passengers) {
		return flightDao.getAllFutureFlights(passengers);
	}
	

	@Transactional
	public void deleteFlight(Flight f) {
		flightDao.deleteFlight(f);
	}


	public List<Flight> findFlightWithoutDate(Airport departure, Airport arrival, int passengers) {
		if (departure==null &&arrival==null){ 
			return flightDao.getAllFutureFlights(passengers);
		} else {
			return flightDao.findFlightWithoutDate(departure, arrival,passengers);
		}

	}
	
	public List<Flight> findFlightByCodes(String departure, String arrival) {
		return flightDao.findFlightByCodes(departure, arrival);

	}
	
	public List<Flight> findFlightWithDate(Airport departure, Date depDate, Airport arrival, int passengers ){
		return flightDao.findFlightWithDate(departure, depDate, arrival, passengers);
	}
	
	public long getFreeSeats(Flight f){
		System.out.println("FlightService: trying to find flight"+f);
		long n =  ticketDao.getBookingsForFlight(f);
		return n;
	}
	
	public long getFreeSeatsById(int id){
		System.out.println("FlightService: trying to find flight "+id);
		long n=  ticketDao.getBookingsForFlightById(id);
		return n;
	}
	
	public void updateFlight(Flight f){
		flightDao.updateFlight(f);
	}
}
