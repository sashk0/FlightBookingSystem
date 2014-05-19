package info.oleksandr.www.beans;

import info.oleksandr.www.converters.FlightDataModel;
import info.oleksandr.www.entities.Airport;
import info.oleksandr.www.entities.Flight;
 
import info.oleksandr.www.services.AirportService;
import info.oleksandr.www.services.FlightService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject; 
import javax.inject.Named;

import org.primefaces.event.RowEditEvent; 
import org.springframework.transaction.annotation.Transactional;
@Named 
@SessionScoped
@ManagedBean(eager = true)
public class FlightBean {
	@Inject 
	private FlightService flightService;
	@Inject AirportService airportService;
	private String flightNumber;		 
	private Date  departureTime,arrivalTime,fromTime, toTime,today;	 
	private Airport arrivalAirport,departureAirport,fromAirport,toAirport; 
	private int seatsAvailable,seatsRequired;
	private double seatPrice;
	private List<Flight> allFlights,foundFlights;	
	private List<Airport> arrivalList,departureList;
	private SelectItem selectedItem;
	private Flight selectedFlight;	
	private FlightDataModel flightModel;
	private boolean disableNextBtn, futureOnly, disableAddBtn;
 
	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}
	public List<Airport> getArrivalList() {
		return arrivalList;
	}
	/**
	 * @return the disableNextBtn
	 */
	public boolean getDisableNextBtn() {
		return disableNextBtn;
	}

	/**
	 * @param disableNextBtn the disableNextBtn to set
	 */
	public void setDisableNextBtn(boolean disableNextBtn) {
		this.disableNextBtn = disableNextBtn;
	}

	public boolean isDisableAddBtn() {
		return disableAddBtn;
	}

	public void setArrivalList(List<Airport> arrivalList) {
		this.arrivalList = arrivalList;
	}


	public Date getToTime() {
		return toTime;
	}


	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	public Airport getFromAirport() {
		return fromAirport;
	}


	public void setFromAirport(Airport fromAirport) {
		this.fromAirport = fromAirport;
	}


	public Airport getToAirport() {
		return toAirport;
	}


	public void setToAirport(Airport toAirport) {
		this.toAirport = toAirport;
	}


	public List<Flight> getFoundFlights() {
		return foundFlights;
	}


	public int getSeatsRequired() {
		return seatsRequired;
	}


	public void setSeatsRequired(int seatsRequired) {
		this.seatsRequired = seatsRequired;
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
 
	public Airport getArrivalAirport() {
		return arrivalAirport;
	}


	public void setArrivalAirport(Airport arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}
 

	public int getSeatsAvailable() {
		return seatsAvailable;
	}


	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}


	public double getSeatPrice() {
		return seatPrice;
	}


	public void setSeatPrice(double seatPrice) {
		this.seatPrice = seatPrice;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}


	public Date getArrivalTime() {
		return arrivalTime;
	}


	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}


	public Date getToday() {
		return today;
	}


	public void setToday(Date today) {
		this.today = today;
	}


	public List<Airport> getDepartureList() {
		return departureList;
	}
	public void setDepartureList(List<Airport> departureList) {
		this.departureList = departureList;
	}
	public FlightDataModel getFlightModel() {
		return flightModel;
	}


	public void setFlightModel(FlightDataModel flightModel) {
		this.flightModel = flightModel;
	}


	public SelectItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(SelectItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public List<Flight> getAllFlights() {
		return allFlights;
	}


	/**
	 * @return the futureOnly
	 */
	public boolean isFutureOnly() {
		return futureOnly;
	}

	/**
	 * @param futureOnly the futureOnly to set
	 */
	public void setFutureOnly(boolean futureOnly) {
		this.futureOnly = futureOnly;
	}

	@PostConstruct
	public void init() {
		//setting defaults
		today=new Date();
		flightNumber="";	 
		departureTime = new Date();
		arrivalTime= new Date(); 		 
		selectedFlight=null;		 
		this.allFlights=flightService.getAllFlights();
						
		reset();	
				 
	}
	
	public void reset(){
		departureList = airportService.getAvailableDepartures();
		arrivalList = airportService.getAvailableArrivals();
		foundFlights=null;		 
		fromAirport = null;
		toAirport=null;
		departureAirport=null;
		arrivalAirport=null;
		fromTime=null;
		toTime=null;
		seatsAvailable=10;
		seatPrice=99.9;
		disableNextButton();
		enableAddButton();
		this.allFlights= futureOnly ? flightService.getAllFutureFlights():flightService.getAllFlights();
		seatsRequired=1;
		
	}
	 
	
	@Transactional
	public void addFlight(){
		 
		Flight f = new Flight(this.flightNumber, this.departureAirport, this.departureTime, 
				this.arrivalAirport, this.arrivalTime, this.seatsAvailable, this.seatPrice);
		System.out.println(f);
		flightService.addFlight(f);
		reset();
	}


	public Flight getSelectedFlight() {
		return selectedFlight;
	}


	public void setSelectedFlight(Flight selectedFlight) {
		this.selectedFlight = selectedFlight;
	}	 
	
	 
	public void deleteSelectedFlight(){
		if (selectedFlight!=null){
			System.err.println("FlightBean: Selected flight is"+selectedFlight);
			flightService.deleteFlight(selectedFlight);
			System.err.println("FlightBean: removed sucessfully!");
			reset();
		}		
	}
	
	public void cancelSelectedFlight(){
		if (selectedFlight!=null){
			System.err.println("FlightBean: Selected flight is"+selectedFlight);
			selectedFlight.setCancelled(true);
			flightService.updateFlight(selectedFlight);
			System.err.println("FlightBean: cancelled sucessfully!");
			reset();
		}		
	}
	
	public void onEdit(RowEditEvent event){
		System.out.println("Edited: "+((Flight)event.getObject()).getFlightNumber());
	}
	
	public void onCancel(RowEditEvent event){
		System.out.println("Cancelled: "+((Flight)event.getObject()).getFlightNumber());
	}
	
	public void suggestArrivalTime(){		 
			if(this.departureTime!=null){ Calendar arr = Calendar.getInstance();
			arr.setTime(this.departureTime);
			arr.add(Calendar.HOUR_OF_DAY, 2);
			this.arrivalTime=arr.getTime();	
			generateFlightNumber();
			}
	}
	  

	 
	public void generateFlightNumber(){
		if (departureAirport!=null&&arrivalAirport!=null){
		String fn= (departureAirport.getCode()).substring(0, 2)+(arrivalAirport.getCode()).substring(0, 2)+"-";
		Calendar c = Calendar.getInstance();
		c.setTime(departureTime);
		fn+=c.get(Calendar.DATE)+""+(c.get(Calendar.MONTH)+1);//+""+(c.get(Calendar.YEAR)-2000
		flightNumber=fn;
		System.out.println("generated: "+flightNumber);} else {flightNumber="";};
		enableAddButton();
	
	} 
	
	public void refreshArrivals(){
		System.out.println("FlightBean: refreshing arrivals");
		System.out.println(fromAirport);
		if(airportService.getRoutesFrom(fromAirport)!=null){
		 this.arrivalList = new ArrayList<>(); 
		arrivalList = airportService.getRoutesFrom(fromAirport);
		}
	}
	
	public void findFlight(int seatsRequired){		
	 	selectedFlight=null;
		if(this.fromTime!=null){
	 		System.err.println("FlightBean: searching for flights with date");
	 		foundFlights = flightService.findFlightWithDate(fromAirport, fromTime, toAirport, seatsRequired);
	 	} else {
	 		System.err.println("FlightBean: searching for flights withou date");
			foundFlights = flightService.findFlightWithoutDate(fromAirport, toAirport, seatsRequired);	
	 	}
	 	 			
	 	System.out.println("Found: "+foundFlights);
		setFlightModel(new FlightDataModel(foundFlights));
		
	} 
	
	public void tellSelected(){	 
		System.out.println("Selected: "+selectedItem.getValue().toString());		 
	}
	
	public int getFreeSeatsCurrent(){
		if(selectedFlight!=null){
			int freeSeats=selectedFlight.getSeatsAvailable()-selectedFlight.getTickets().size();
			System.out.println(selectedFlight.getFlightNumber()+" has "+ freeSeats);		
			return freeSeats;
			
		}
		return 0;
	} 
	
	public int getFreeSeats(Flight f){
		System.out.println(flightService.getFreeSeats(f));
		int n = (int)(long)flightService.getFreeSeats(f);
		return n;
	}
	
	public List<Airport> completeDepartures(String keyword) {
		return departureList;
	}
	
	public List<Airport> completeArrivals(String keyword) {
		return arrivalList;
	}
	
	public void disableNextButton(){
		disableNextBtn = true;
	}
	
	public void enableNextButton(){
		disableNextBtn = false;
	}
	
	public void enableAddButton(){
		if(this.flightNumber!="" && this.departureAirport!=null && this.departureTime!=null && 
		this.arrivalAirport!=null && this.arrivalTime!=null && this.seatsAvailable>0 && this.seatPrice>0){
		disableAddBtn = false;
		} else {disableAddBtn=true;}
	}
	
}
