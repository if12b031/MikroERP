package models;

import invoice.Invoice;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchresultInvoiceTableModel {
		
	private ArrayList<Invoice> invoiceList;
	
	public SearchresultInvoiceTableModel(ArrayList<Invoice> i) {
		this.invoiceList = i;
	}	
	
	public ObservableList<SearchInvoiceModel> getItems() {
		ObservableList<SearchInvoiceModel> elements = FXCollections
				.observableArrayList();
		
		for(int i=0; i<invoiceList.size(); i++){
			Invoice invoice = invoiceList.get(i);
			elements.add(new SearchInvoiceModel(invoice.get_invoiceNumber(), invoice.get_customerName(), invoice.get_creationDate(),
					invoice.is_isOutgoing(), invoice.get_ust(), invoice.get_net(), invoice.get_gross()));			
		}
		return elements;
	}
}
