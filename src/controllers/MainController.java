package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import models.MainModel;
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
	
	private MainModel presentationModel;
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
		this.presentationModel = new MainModel();
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
		List<Customer> searchResultList = proxy.searchCustomer(searchCustomerName.getText(),
											searchCustomerLastName.getText(), searchCustomerCompany.getText());
		if(searchResultList == null) {
			messageLabelSuche.setText("No search results found!");
			return;
		}
		
		for(int i = 0; i < searchResultList.size(); i++) {
			openCustomerWindow(searchResultList.get(i));
		}
	}
	
	@FXML private void newCustomer() {
		Customer customer = new Customer();
		openCustomerWindow(customer);
	}
	
	@FXML private void searchForInvoice() {
		List<Invoice> searchResultList = proxy.searchInvoice(searchInvoiceDateFrom.getText(),
											searchInvoiceDateTo.getText(), searchInvoiceValueFrom.getText(), searchInvoiceValueTo.getText(),
											searchInvoiceCustomer.getText());
		if(searchResultList == null) {
			messageLabelSuche.setText("No search results found!");
			return;
		}
		
		for(int i = 0; i < searchResultList.size(); i++) {
			openInvoiceWindow(searchResultList.get(i));
		}
	}
	
	@FXML private void newInvoice() {
		Invoice invoice = new Invoice();		
		openInvoiceWindow(invoice);
	}
	
	private void openInvoiceWindow(Invoice searchResult) {		
		if(searchResult == null){
			messageLabelSuche.setText("No search results found!");
			return;
		}		
		try {			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/Invoice.fxml"));
			TabPane root = (TabPane)fxmlLoader.load();
			
			Stage secondStage = new Stage(StageStyle.DECORATED);		
			Scene scene = new Scene(root, 550, 700);
			scene.getStylesheets().add(getClass().getResource("../main/application.css").toExternalForm());
			secondStage.setScene(scene);
			secondStage.setTitle("SWE 2 - MikroERP");
			
			InvoiceController<?> controller = fxmlLoader.<InvoiceController<?>>getController();
			controller.setSearchResult(searchResult);
			controller.displaySearchResult();
			secondStage.show();	
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void openCustomerWindow(Customer searchResult) {		
		if(searchResult == null){
			messageLabelSuche.setText("No search results found!");
			return;
		}		
		try {			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/Contact.fxml"));
			TabPane root = (TabPane)fxmlLoader.load();
			
			Stage secondStage = new Stage(StageStyle.DECORATED);		
			Scene scene = new Scene(root, 550, 700);
			scene.getStylesheets().add(getClass().getResource("../main/application.css").toExternalForm());
			secondStage.setScene(scene);
			secondStage.setTitle("SWE 2 - MikroERP");
			
			ContactController<?> controller = fxmlLoader.<ContactController<?>>getController();
			controller.setSearchResult(searchResult);
			controller.displaySearchResult();
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