package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import models.MainPresentationModel;
import proxy.Proxy;
import invoice.Invoice;
import contacts.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController<T> implements Initializable{
	
	private MainPresentationModel presentationModel;
	private Proxy proxy;
		
	@FXML TabPane tabPane;
	
	/* Variables related to TabPane Start */
	@FXML private Text totalCustomers;
	@FXML private Text totalInvoices;
	@FXML private Label messageLabelStart;
	
	/* Variables related to TabPane Suche */
	@FXML private TextField searchInvoiceDateFrom;
	@FXML private TextField searchInvoiceDateTo;
	@FXML private TextField searchInvoiceValueFrom;
	@FXML private TextField searchInvoiceValueTo;
	@FXML private TextField searchInvoiceCustomer;
	@FXML private TextField searchCustomerName;
	@FXML private TextField searchCustomerLastName;
	@FXML private TextField searchCustomerCompany;
	@FXML private Label messageLabelSuche;
	
	public void initialize(URL url, ResourceBundle resources) {
		this.presentationModel = new MainPresentationModel();
		this.proxy = new Proxy();
		tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		        clearMessages();
		    }
		});
		applyBindings();		
	}

	private void applyBindings() {
		searchCustomerName.textProperty().bindBidirectional(presentationModel.customerNameProperty());
		searchCustomerLastName.textProperty().bindBidirectional(presentationModel.customerLastnameProperty());
		searchCustomerCompany.textProperty().bindBidirectional(presentationModel.companyNameProperty());
		
		searchCustomerCompany.disableProperty().bind(
				presentationModel.disableEditCompanyBinding());
		searchCustomerName.disableProperty().bind(
				presentationModel.disableEditPersonBinding());
		searchCustomerLastName.disableProperty().bind(
				presentationModel.disableEditPersonBinding());
	}
	
	@FXML private void searchForCustomer() {	
		ArrayList<Customer> searchResultList = proxy.searchCustomer(searchCustomerName.getText(),
											searchCustomerLastName.getText(), searchCustomerCompany.getText());
		if(searchResultList == null) {
			messageLabelSuche.setText("No search results found!");
			return;
		}		
		openCustomerWindow(searchResultList);
		clearMessages();
	}
	
	@FXML private void newCustomer() {
		Customer customer = new Customer();
		openEmptyCustomerWindow(customer);
		clearMessages();
	}
	
	@FXML private void searchForInvoice() {
		ArrayList<Invoice> searchResultList = proxy.searchInvoice(searchInvoiceCustomer.getText(), 
				searchInvoiceDateFrom.getText(), searchInvoiceDateTo.getText(), searchInvoiceValueFrom.getText(), 
				searchInvoiceValueTo.getText());
		if(searchResultList == null) {
			messageLabelSuche.setText("No search results found!");
			return;
		}
		openInvoiceWindow(searchResultList);
		clearMessages();
	}
	
	@FXML private void newInvoice() {
		Invoice invoice = new Invoice();		
		openEmptyInvoiceWindow(invoice);
		clearMessages();
	}
	
	private void openInvoiceWindow(ArrayList<Invoice> searchResultList) {		
		if(searchResultList.size() < 1){
			messageLabelSuche.setText("No search results found!");
			return;
		}		
		try {			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SearchresultInvoice.fxml"));
			TabPane root = (TabPane)fxmlLoader.load();
			
			Stage secondStage = new Stage(StageStyle.DECORATED);		
			Scene scene = new Scene(root, 666, 530);
			scene.getStylesheets().add(getClass().getResource("/main/application.css").toExternalForm());
			secondStage.setScene(scene);
			secondStage.setTitle("SWE 2 - MikroERP");
			
			SearchresultInvoiceController controller = fxmlLoader.<SearchresultInvoiceController>getController();
			controller.setSearchResultList(searchResultList);
			controller.displaySearchresult();
			secondStage.show();	
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void openEmptyInvoiceWindow(Invoice searchResult) {		
		if(searchResult == null){
			searchResult = new Invoice();
		}		
		try {			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Invoice.fxml"));
			TabPane root = (TabPane)fxmlLoader.load();
			
			Stage secondStage = new Stage(StageStyle.DECORATED);		
			Scene scene = new Scene(root, 550, 730);
			scene.getStylesheets().add(getClass().getResource("/main/application.css").toExternalForm());
			secondStage.setScene(scene);
			secondStage.setTitle("SWE 2 - MikroERP");
			
			InvoiceController<?> controller = fxmlLoader.<InvoiceController<?>>getController();
			controller.setSearchResult(searchResult);
			secondStage.show();	
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void openCustomerWindow(ArrayList<Customer> searchResultList) {		
		if(searchResultList.size() < 1){
			messageLabelSuche.setText("No search results found!");
			return;
		}		
		try {			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SearchresultCustomer.fxml"));
			TabPane root = (TabPane)fxmlLoader.load();
			
			Stage secondStage = new Stage(StageStyle.DECORATED);		
			Scene scene = new Scene(root, 666, 530);
			scene.getStylesheets().add(getClass().getResource("/main/application.css").toExternalForm());
			secondStage.setScene(scene);
			secondStage.setTitle("SWE 2 - MikroERP");
			
			SearchresultCustomerController controller = fxmlLoader.<SearchresultCustomerController>getController();
			controller.setSearchResultList(searchResultList);
			controller.displaySearchresult();
			secondStage.show();			
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void openEmptyCustomerWindow(Customer searchResult) {		
		if(searchResult == null){
			searchResult = new Customer();
		}		
		try {			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Customer.fxml"));
			TabPane root = (TabPane)fxmlLoader.load();
			
			Stage secondStage = new Stage(StageStyle.DECORATED);		
			Scene scene = new Scene(root, 550, 730);
			scene.getStylesheets().add(getClass().getResource("/main/application.css").toExternalForm());
			secondStage.setScene(scene);
			secondStage.setTitle("SWE 2 - MikroERP");
			
			CustomerController controller = fxmlLoader.<CustomerController>getController();
			controller.setSearchResult(searchResult);
			secondStage.show();			
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void clearMessages() {
		messageLabelStart.setText("");
		messageLabelSuche.setText("");
	}
}
