package models;

import java.util.ArrayList;

import contacts.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchresultCustomerTableModel {
		
	private ArrayList<Customer> customerList;
	
	public SearchresultCustomerTableModel(ArrayList<Customer> i) {
		this.customerList = i;
	}	
	
	public ObservableList<SearchCustomerModel> getItems() {
		if(customerList == null){
			return null;
		}
		
		ObservableList<SearchCustomerModel> elements = FXCollections
				.observableArrayList();
		
		for(int i=0; i<customerList.size(); i++){
			Customer customer = customerList.get(i);
			elements.add(new SearchCustomerModel(customer.get_uid(), customer.get_title(), customer.get_surname(),
					customer.get_lastname(), customer.get_suffix(), customer.get_dateOfBirth(), customer.get_address(), 
					Integer.toString(customer.get_plz()), customer.get_city()));			
		}
		return elements;
	}
}
