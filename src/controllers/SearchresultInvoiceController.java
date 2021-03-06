package controllers;

import invoice.Invoice;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import models.SearchInvoiceModel;
import models.SearchresultInvoiceTableModel;
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

public class SearchresultInvoiceController implements Initializable {
	
	private ArrayList<Invoice> searchresultList;
	
	@FXML private TableView<SearchInvoiceModel> searchresultTable;
	@FXML private TabPane tabPane;
		
	public void initialize(URL url, ResourceBundle resources) {
		searchresultTable.setPlaceholder(new Text(""));
		searchresultTable.setEditable(false);
		applyBindings();
	}
	
	private void applyBindings() {
	
	}
	
	public void displaySearchresult() {
		SearchresultInvoiceTableModel tableModel = new SearchresultInvoiceTableModel(searchresultList);
		ObservableList<SearchInvoiceModel> items = (ObservableList<SearchInvoiceModel>) tableModel.getItems();
		
		searchresultTable.setItems(items);
		searchresultTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
			    if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)){
			    	int index = searchresultTable.getSelectionModel().getSelectedIndex();
			    	if(index >= 0){
			    		openInvoiceWindow(searchresultList.get(index));
			    	}
			    }
			}
		});
	}
	
	private void openInvoiceWindow(Invoice searchResult) {		
		if(searchResult == null){
			return;
		}		
		try {			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Invoice.fxml"));
			TabPane root = (TabPane)fxmlLoader.load();
			
			Stage secondStage = new Stage(StageStyle.DECORATED);		
			Scene scene = new Scene(root, 550, 740);
			scene.getStylesheets().add(getClass().getResource("/main/application.css").toExternalForm());
			secondStage.setScene(scene);
			secondStage.setTitle("SWE 2 - MikroERP");
			
			InvoiceController<?> controller = fxmlLoader.<InvoiceController<?>>getController();
			controller.setSearchResult(searchResult);
			controller.displaySearchresult();
			secondStage.show();	
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<Invoice> getSearchresultList() {
		return searchresultList;
	}

	public void setSearchResultList(ArrayList<Invoice> searchresultList) {
		this.searchresultList = searchresultList;
	}
}
