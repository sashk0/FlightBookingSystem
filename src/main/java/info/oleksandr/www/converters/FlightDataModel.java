package info.oleksandr.www.converters;

import java.util.List;

import javax.faces.model.ListDataModel;

import info.oleksandr.www.entities.Flight;

import org.primefaces.model.SelectableDataModel;

public class FlightDataModel extends ListDataModel<Flight> implements SelectableDataModel<Flight> {

	public FlightDataModel(){}
	
	public FlightDataModel(List<Flight> data){
		super(data);
	}
	
	@Override
	public Object getRowKey(Flight f) {		 
		System.out.println("FlightDataModel: returning"+f.getFlightNumber());
		return f.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Flight getRowData(String rowKey) {
		List<Flight> flights = (List<Flight>) getWrappedData();
		for(Flight f:flights){
			if(String.valueOf(f.getId())==rowKey)
				System.out.println("FlightDataModel: returning"+f.getFlightNumber());
				return f;
		}
		return null;
	}

}
