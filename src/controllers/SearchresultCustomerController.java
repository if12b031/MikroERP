package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import proxy.Proxy;
import contacts.Customer;
import contacts.CustomerSearchQuery;
import models.SearchCustomerModel;
import models.SearchresultCustomerTableModel;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SearchresultCustomerController implements Initializable {
	
	private ArrayList<Customer> searchresultList;
	private CustomerSearchQuery search;
	private Proxy proxy;
	
	@FXML private TableView<SearchCustomerModel> searchresultTable;
	@FXML private TabPane tabPane;
		
	public void initialize(URL url, ResourceBundle resources) {
		this.proxy = new Proxy();
		searchresultTable.setPlaceholder(new Text(""));
		searchresultTable.setEditable(false);
		applyBindings();
	}
	
	private void applyBindings() {
	
	}
	
	public void displaySearchresult() {
		SearchresultCustomerTableModel tableModel = new SearchresultCustomerTableModel(searchresultList);
		ObservableList<SearchCustomerModel> items = (ObservableList<SearchCustomerModel>) tableModel.getItems();
		
		searchresultTable.setItems(items);
		searchresultTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
			    if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)){
			    	int index = searchresultTable.getSelectionModel().getSelectedIndex();			    	
			    	if(index >= 0){
			    		openCustomerWindow(searchresultList.get(index));
			    	}
			    }
			}
		});
	}
	
	public void updateSearchresult() {
		searchresultList = proxy.searchCustomer(search.get_surname(), search.get_lastname(),
				search.get_name());
		displaySearchresult();
	}
	
	private void openCustomerWindow(Customer searchResult) {		
		if(searchResult == null){
			return;
		}		
		try {			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Customer.fxml"));
			TabPane root = (TabPane)fxmlLoader.load();
			
			Stage secondStage = new Stage(StageStyle.DECORATED);		
			Scene scene = new Scene(root, 550, 740);
			scene.getStylesheets().add(getClass().getResource("/main/application.css").toExternalForm());
			secondStage.setScene(scene);
			secondStage.setTitle("SWE 2 - MikroERP");
			
			CustomerController controller = fxmlLoader.<CustomerController>getController();
			controller.setSearchController(this);
			controller.setSearchResult(searchResult);
			controller.displaySearchresult();
			secondStage.show();
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<Customer> getSearchresultList() {
		return searchresultList;
	}

	public void setSearchResultList(ArrayList<Customer> searchresultList) {
		this.searchresultList = searchresultList;
	}

	public CustomerSearchQuery getSearch() {
		return search;
	}

	public void setSearch(CustomerSearchQuery search) {
		this.search = search;
	}
}
