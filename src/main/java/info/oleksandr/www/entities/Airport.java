package info.oleksandr.www.entities;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name="airports")
public class Airport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String code;   
	private String name, country;
	@OneToMany(mappedBy="arrivalAirport")
	private Collection<Flight> arrivalFlights;
	@OneToMany(mappedBy="departureAirport")
	private Collection<Flight> departureFlights;
	public Airport() {	}

	 

	public Airport(String code, String name, String country) {
		super();
		this.code = code;
		this.name = name;		 
		this.country = country;
	}



// 	@Override
// 	public String toString() {
// 		return name +" ("+ code+")";
// }



	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}


	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public void setId(String code) {
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	 
	 
}
