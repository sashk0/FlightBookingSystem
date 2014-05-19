package info.oleksandr.www.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import info.oleksandr.www.entities.Airport;
import info.oleksandr.www.entities.Flight; 
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FlightDao {
	 
	@PersistenceContext
	private EntityManager em;
	
	public Flight getFlightById(int id){
		Flight flight = em.createQuery("SELECT f FROM Flight f WHERE f.id=?1", Flight.class).setParameter(1, id).getSingleResult();
		return flight;
	}
	 
	public void addFlight(Flight f){
		em.persist(f);		
	}
	
	@Transactional
	public void deleteFlight(Flight f){
		em.remove(em.merge(f));
	}
	
	public List<Flight> getAllFlights(){
		List<Flight> flights = em.createQuery("SELECT f FROM Flight f", Flight.class).getResultList();
		return flights; 
	}
	
	public List<Flight> getAllFutureFlights(){
		List<Flight> flights = em.createQuery("SELECT f FROM Flight f WHERE (FUNC('Date', f.departureTime)) > :depDate", Flight.class).setParameter("depDate", new Date()).getResultList();
		return flights; 
	}
	
	public List<Flight> getAllFutureFlights(int passengers){
		List<Flight> flights = em.createQuery("SELECT f FROM Flight f WHERE (FUNC('Date', f.departureTime)) > :depDate", Flight.class).setParameter("depDate", new Date()).getResultList();
		List<Flight> result2 = new ArrayList<Flight>();
		for (Flight f:flights){
			if (f.getTicketsN()>=passengers) result2.add(f);
		}			
	return result2;	 
	}
	
	public List<Flight> findFlightWithoutDate(Airport departure, Airport arrival, int passengers){
		if(departure!=null&&arrival!=null){
			 Date d = new Date();
			List<Flight> result = em.createQuery("SELECT f FROM Flight f WHERE f.departureAirport=:departureId AND f.arrivalAirport=:arrivalId AND (FUNC('Date', f.departureTime)) > :depDate", Flight.class)
				.setParameter("departureId", departure)
				.setParameter("arrivalId", arrival)
				.setParameter("depDate",d)
				.getResultList();			
			List<Flight> result2 = new ArrayList<Flight>();
			for (Flight f:result){
				if (f.getTicketsN()>=passengers) result2.add(f);
			}			
			return result2;	 
		}		 
		//find flight if arrival and date unknown
		if(departure!=null&&arrival==null){
			 Date d = new Date();
			List<Flight> result = em.createQuery("SELECT f FROM Flight f WHERE f.departureAirport=:departureId AND (FUNC('Date', f.departureTime)) > :depDate", Flight.class)
				.setParameter("departureId", departure) 
				.setParameter("depDate",d)
				.getResultList();			
			List<Flight> result2 = new ArrayList<Flight>();
			for (Flight f:result){
				if (f.getTicketsN()>=passengers) result2.add(f);
			}			
			return result2;	 
		}
		
		return null;
	}
	
	public List<Flight> findFlightByCodes(String departure, String arrival){
		if(departure!=null&&arrival!=null){			 
			List<Flight> result = em.createQuery("SELECT f FROM Flight f WHERE f.departureAirport.code=?1 AND f.arrivalAirport.code=?2", Flight.class)
				.setParameter(1, departure)
				.setParameter(2, arrival)
				.getResultList();		
		return result;
		}
		return null;
	}
	
	public List<Flight> findFlightWithDate(Airport departure, Date depDate, Airport arrival, int passengers){
		// finding exact flight for exact date 
		if(depDate!=null&&departure!=null&&arrival!=null){			
			List<Flight> result = em.createQuery("SELECT f FROM Flight f WHERE f.departureAirport=:departureId AND f.arrivalAirport=:arrivalId AND (FUNC('Date', f.departureTime))=:depDate", Flight.class)
				.setParameter("departureId", departure)
				.setParameter("arrivalId", arrival)
				.setParameter("depDate", depDate)				
				.getResultList();	
			List<Flight> result2 = new ArrayList<Flight>();
			for (Flight f:result){
				if (f.getTicketsN()>=passengers) result2.add(f);
			}			
			return result2;
		}		
		//finding all flights for exact date
		if(depDate!=null&&departure==null&&arrival==null){			
			List<Flight> result = em.createQuery("SELECT f FROM Flight f WHERE (FUNC('Date', f.departureTime))=:depDate", Flight.class)				
				.setParameter("depDate", depDate)				
				.getResultList();	
			List<Flight> result2 = new ArrayList<Flight>();
			for (Flight f:result){
				if (f.getTicketsN()>=passengers) result2.add(f);
			}			
			return result2;
		}
		//finding flights for exact date by arrival airport
			if(depDate!=null&&departure==null&&arrival!=null){				
					List<Flight> result = em.createQuery("SELECT f FROM Flight f WHERE f.arrivalAirport=:arrivalId AND (FUNC('Date', f.departureTime)) = :depDate", Flight.class) 
						.setParameter("arrivalId", arrival)
						.setParameter("depDate",depDate)
						.getResultList();			
					List<Flight> result2 = new ArrayList<Flight>();
					for (Flight f:result){
						if (f.getTicketsN()>=passengers) result2.add(f);
					}			
					return result2;	
					} 
		//finding flights for exact date by departure airport
			if(depDate!=null&&departure!=null&&arrival==null){					
				List<Flight> result = em.createQuery("SELECT f FROM Flight f WHERE f.departureAirport=:departureId AND (FUNC('Date', f.departureTime)) = :depDate", Flight.class)
					.setParameter("departureId", departure) 
					.setParameter("depDate",depDate)
					.getResultList();			
				List<Flight> result2 = new ArrayList<Flight>();
				for (Flight f:result){
					if (f.getTicketsN()>=passengers) result2.add(f);
				}			
				return result2;	 
			}
		return null;
	}
	
	@Transactional
	public void updateFlight(Flight f){
		em.merge(f);
	}
}

