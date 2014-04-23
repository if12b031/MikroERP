package models;

import invoice.InvoiceElement;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableModel {
		
	private ArrayList<InvoiceElement> a;
	
	public TableModel(ArrayList<InvoiceElement> a) {
		this.a = a;
	}	
	
	public ObservableList<InvoiceElementModel> getItems() {
		ObservableList<InvoiceElementModel> elements = FXCollections
				.observableArrayList();
		
		for(int i=0; i<a.size(); i++){
			InvoiceElement e = a.get(i);
			elements.add(new InvoiceElementModel(e.get_name(), e.get_price(), e.get_amount()));			
		}
		return elements;
	}
}
