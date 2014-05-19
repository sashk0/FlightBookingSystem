package info.oleksandr.www.services;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import info.oleksandr.www.dao.AirportDao;
import info.oleksandr.www.entities.Airport;

@Named
public class AirportService {
	
	@Inject
	private AirportDao airportDao;

	 
	public Airport getAirportById(int id){ 
		return airportDao.getAirportById(id);
	}
	
	public List<Airport> getAirportByKeyword(String keyword) {
		List<Airport> airports = airportDao.getAirportByKeyword(keyword);
		return airports;
	}
	
	public Airport getAirportByCode(String code){
		Airport airport = airportDao.getAirportByCode(code);
		return airport;
	}
	
	public List<String> getCountriesList(){
		List<String> countries = airportDao.getCountriesList();
		return countries;
	}
	
	public List<Airport> getAirportsFromCountry(String country){
		List<Airport> airports=airportDao.getAirportsFromCountry(country);
		return airports;
	}
	
	public List<Airport> getAirportList(){
		List<Airport> airports = airportDao.getAirportList(); 
		return airports;
	}
	
	public List<Airport> getAvailableDepartures(){return airportDao.getAvailableDepartures();}
	public List<Airport> getAvailableArrivals(){return airportDao.getAvailableArrivals();}
	
	public List<Airport> getRoutesFrom(Airport departureAirport){return airportDao.getRoutesFrom(departureAirport);}
	public List<Airport> getRoutesTo(Airport arrivalAirport){return airportDao.getRoutesTo(arrivalAirport);}
}
