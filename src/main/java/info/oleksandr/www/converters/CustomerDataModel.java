package info.oleksandr.www.converters;

import java.util.List;

import javax.faces.model.ListDataModel;

import info.oleksandr.www.entities.Customer; 

import org.primefaces.model.SelectableDataModel;

public class CustomerDataModel extends ListDataModel<Customer> implements SelectableDataModel<Customer> {

	public CustomerDataModel(){}
	
	public CustomerDataModel(List<Customer> data){
		super(data);
	}
	
	@Override
	public Object getRowKey(Customer c) {		 
		System.out.println("CustomerDataModel: returning"+c.toString());
		return c.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Customer getRowData(String rowKey) {
		List<Customer> customers = (List<Customer>) getWrappedData();
		for(Customer c:customers){
			if(String.valueOf(c.getId())==rowKey)
				System.out.println("CustomerDataModel: returning"+c.toString());
				return c;
		}
		return null;
	}

}
