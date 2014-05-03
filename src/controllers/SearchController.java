package controllers;

import invoice.Invoice;

import java.util.List;

import contacts.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SearchController {
	
	private List<Customer> searchResultListCustom;
	private List<Invoice> searchResultListInv;
	
	@FXML VBox searchResults;
	
	public void printSearchResults() {
		if(searchResultListInv == null){
			for(int i=0; i<searchResultListCustom.size(); i++){
				Label label = new Label();
				StringBuilder strBuilder = new StringBuilder();
				
				strBuilder.append(searchResultListCustom.get(i).get_title());
				strBuilder.append(" ");
				strBuilder.append(searchResultListCustom.get(i).get_surname());
				strBuilder.append(" ");
				strBuilder.append(searchResultListCustom.get(i).get_lastname());
				strBuilder.append(" ");
				strBuilder.append(searchResultListCustom.get(i).get_dateOfBirth());
				strBuilder.append(" ");
				strBuilder.append(searchResultListCustom.get(i).get_address());
				strBuilder.append(" ");
				strBuilder.append(searchResultListCustom.get(i).get_plz());
				strBuilder.append(" ");
				strBuilder.append(searchResultListCustom.get(i).get_city());
				strBuilder.append(" ");
		
				label.setText(strBuilder.toString());
				searchResults.getChildren().add(label);					
			}
		} else{
			for(int i=0; i<searchResultListCustom.size(); i++){
				Label label = new Label();
				StringBuilder strBuilder = new StringBuilder();
				
				strBuilder.append(searchResultListInv.get(i).get_invoiceNumber());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).is_isOutgoing());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).get_creationDate());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).get_expirationDate());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).get_comment());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).get_message());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).get_customerName());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).get_shippingAddress());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).get_invoiceAddress());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).get_ust());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).get_gross());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).get_net());
				strBuilder.append(" ");
				strBuilder.append(searchResultListInv.get(i).get_articles());
				strBuilder.append(" ");
						
				label.setText(strBuilder.toString());
				searchResults.getChildren().add(label);
			}
		}
	}
	
	public List<Customer> getSearchResultListCustom() {
		return searchResultListCustom;
	}
	
	public List<Invoice> getSearchResultListInv() {
		return searchResultListInv;
	}

	public void setSearchResultList(List<Customer> searchResultListCustom, List<Invoice> searchResultListInv) {
		this.searchResultListCustom = searchResultListCustom;
		this.searchResultListInv = searchResultListInv;
	}
}
