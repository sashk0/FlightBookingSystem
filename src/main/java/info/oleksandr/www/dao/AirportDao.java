package info.oleksandr.www.dao;

import java.util.Date;
import java.util.List;

import info.oleksandr.www.entities.Airport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class AirportDao {
	@PersistenceContext
	private EntityManager em;
	
	public Airport getAirportById(int id){		
		Airport airport = em.createQuery("SELECT a FROM Airport a WHERE a.id=?1", Airport.class).setParameter(1, id).getSingleResult();
		return airport;
	}
	
	/** query to DB to find Airports, which contain definite keyword in their Name, Country or Code
	 * @param keyword String to look for
	 * @return  List of found airports
	 */
	public List<Airport> getAirportByKeyword(String keyword){
		keyword = keyword.toUpperCase();
		List<Airport> airports = em.createQuery("SELECT a FROM Airport a WHERE UPPER(a.name) LIKE ?1 or UPPER(a.code) LIKE ?1 or  UPPER(a.country) = ?2 ", Airport.class).setParameter(1, "%"+keyword+"%").setParameter(2, keyword).getResultList();
		return airports;
	}
	
	public Airport getAirportByCode(String code){
		Airport airport = em.createQuery("SELECT a FROM Airport a WHERE a.code=?1", Airport.class).setParameter(1, code).getSingleResult();
		return airport;
	}
	
	public List<String> getCountriesList(){
		List<String> countries = em.createQuery("SELECT DISTINCT a.country FROM Airport a",String.class).getResultList();
		return countries;
	}
	
	public List<Airport> getAirportsFromCountry(String country){
		List<Airport> airports=em.createQuery("SELECT a FROM Airport a WHERE country=:country",Airport.class).setParameter("country", country).getResultList();
		return airports;
	}
	
	public List<Airport> getAirportList(){
		List<Airport> airports=em.createQuery("SELECT a FROM Airport a",Airport.class).getResultList();
		return airports;
	}
	
	public List<Airport> getAvailableDepartures(){
		 List<Airport> departures = em.createQuery("SELECT DISTINCT f.departureAirport FROM Flight f WHERE (FUNC('Date', f.departureTime)) > :depDate",Airport.class)
					.setParameter("depDate", new Date())
				 .getResultList();
		 return departures;
	}
	
	public List<Airport> getAvailableArrivals(){
		 List<Airport> arrivals = em.createQuery("SELECT DISTINCT f.arrivalAirport FROM Flight f WHERE (FUNC('Date', f.departureTime)) > :depDate",Airport.class)
				 .setParameter("depDate", new Date())
				 .getResultList();
		 return arrivals;
	}
	
	public List<Airport> getRoutesFrom(Airport departureAirport){
		List<Airport> routes = em.createQuery("SELECT DISTINCT f.arrivalAirport FROM Flight f WHERE f.departureAirport.id=:departureAirport AND (FUNC('Date', f.departureTime)) > :depDate",Airport.class)
				.setParameter("departureAirport", departureAirport.getId())
				.setParameter("depDate", new Date())
				.getResultList();
		return routes;
	}
	
	public List<Airport> getRoutesTo(Airport arrivalAirport){
		List<Airport> routes = em.createQuery("SELECT DISTINCT f.departureAirport FROM Flight f WHERE f.arrivalAirport=:arrivalAirport AND (FUNC('Date', f.departureTime)) > :depDate",Airport.class)
				.setParameter("arrivalAirport", arrivalAirport)
				.setParameter("depDate", new Date())
				.getResultList();
		return routes;
	}
}
