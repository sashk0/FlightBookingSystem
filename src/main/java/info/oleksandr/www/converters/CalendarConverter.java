package info.oleksandr.www.converters;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter; 
import javax.inject.Named;


@Named
public class CalendarConverter implements Converter {
  
	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		String dt=df.format(value);
		return dt;
		   
		}



	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		Date cal;
		try {
			cal = df.parse(value);
			return cal;
		} catch (ParseException e) {
			 
			e.printStackTrace();
		}
		return null;
	}

	}


