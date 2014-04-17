package main;

import java.util.List;

import contacts.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SearchController {
	
	private List<Customer> searchResultList;
	
	@FXML VBox searchResults;
	
	public void printSearchResults() {	
		for(int i=0; i<searchResultList.size(); i++) {
			Label label = new Label();
			StringBuilder strBuilder = new StringBuilder();
			
			strBuilder.append(searchResultList.get(i).get_title());
			strBuilder.append(" ");
			strBuilder.append(searchResultList.get(i).get_surname());
			strBuilder.append(" ");
			strBuilder.append(searchResultList.get(i).get_lastname());
			strBuilder.append(" ");
			strBuilder.append(searchResultList.get(i).get_dateOfBirth());
			strBuilder.append(" ");
			strBuilder.append(searchResultList.get(i).get_address());
			strBuilder.append(" ");
			strBuilder.append(searchResultList.get(i).get_plz());
			strBuilder.append(" ");
			strBuilder.append(searchResultList.get(i).get_city());
			strBuilder.append(" ");
	
			label.setText(strBuilder.toString());
			searchResults.getChildren().add(label);					
		}
	}
	
	public List<Customer> getSearchResultList() {
		return searchResultList;
	}

	public void setSearchResultList(List<Customer> searchResultList) {
		this.searchResultList = searchResultList;
	}
}
