package info.oleksandr.www.converters;

import java.util.List;

import javax.faces.model.ListDataModel;

import info.oleksandr.www.entities.Customer; 
import info.oleksandr.www.entities.Ticket;

import org.primefaces.model.SelectableDataModel;

public class TicketDataModel extends ListDataModel<Ticket> implements SelectableDataModel<Ticket> {

	public TicketDataModel(){}
	
	public TicketDataModel(List<Ticket> data){
		super(data);
	}
	
	@Override
	public Object getRowKey(Ticket t) {		 
		//System.out.println("TicketDataModel: returning"+t.toString());
		return t.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Ticket getRowData(String rowKey) {
		List<Ticket> tickets = (List<Ticket>) getWrappedData();
		
		for(Ticket t:tickets){
			if(String.valueOf(t.getId())==rowKey)
				//System.out.println("TicketDataModel: returning"+t.toString());
				return t;
		}
		return null;
	 
	}

}
