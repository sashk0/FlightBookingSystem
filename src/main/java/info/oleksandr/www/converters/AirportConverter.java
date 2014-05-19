package info.oleksandr.www.converters;



import info.oleksandr.www.entities.Airport;
import info.oleksandr.www.services.AirportService;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import javax.inject.Inject;
import javax.inject.Named;


@Named
public class AirportConverter implements Converter {
	@Inject
	private AirportService airportService;	

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String value) {
		if (value == null || value.equals("")){
			return null;}
		 try{
			int id = Integer.parseInt(value);
			//System.out.println("Converter Looking for ID:"+id);
			Airport airport = airportService.getAirportById(id);
			//System.out.println(airport+" found");
			return airport;
			} catch (NumberFormatException e) {
				System.err.println("Converter: That was not a number! That was "+value);
				return null;
			}
		 
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		  return value.toString();
		}

	}


