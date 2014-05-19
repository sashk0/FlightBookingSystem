package info.oleksandr.www.beans;
 
import info.oleksandr.www.entities.Airport;
import info.oleksandr.www.services.AirportService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped; 
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.springframework.stereotype.Component;
@Component
@ManagedBean(eager = true)
@SessionScoped
public class AirportBean implements Serializable {
	private static final long serialVersionUID = 1L;	

	private Airport selectedDeparture, selectedArrival;
	private List<Airport> airportDB;
	private List<Airport> departureList, arrivalList;
	@Inject
	private AirportService airportService;
	
	@ManagedProperty(value="#{flightBean}")
	private FlightBean flightBean;
	
	public FlightBean getFlightBean() {
		return flightBean;
	}

	public void setFlightBean(FlightBean flightBean) {
		this.flightBean = flightBean;
	}

	public AirportBean() {

	}

	@PostConstruct
	public void refreshList() {
		airportDB = new ArrayList<Airport>();
		airportDB = airportService.getAirportList();
		
	}	

	public List<Airport> getDepartureList() {
		return departureList;
	}

	public List<Airport> getArrivalList() {
		return arrivalList;
	}

	public List<Airport> getAirportDB() {
		return airportDB;
	}

	public void setAirportDB(List<Airport> airportDB) {
		this.airportDB = airportDB;
	}

	public Airport getSelectedDeparture() {
		return selectedDeparture;
	}

	public void setSelectedDeparture(Airport selectedDeparture) {
		this.selectedDeparture = selectedDeparture;
	}

	public Airport getSelectedArrival() {
		return selectedArrival;
	}

	public void setSelectedArrival(Airport selectedArrival) {
		this.selectedArrival = selectedArrival;
	}

	public List<Airport> completeAirport(String keyword) {
		List<Airport> suggestions =  airportService.getAirportByKeyword(keyword);

		return suggestions;
	}
	
	
	
	public void handleSelect(SelectEvent event) {  	   
	  System.out.println("selected:"+selectedDeparture);
	           
	}  
 
	
}
