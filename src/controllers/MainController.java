package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import models.MainPresentationModel;
import proxy.Proxy;
import utils.Utils;
import invoice.Invoice;
import contacts.Customer;
import contacts.CustomerSearchQuery;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
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
	@FXML private Button searchInvoice;
	@FXML private Button searchCustomer;
	
	public void initialize(URL url, ResourceBundle resources) {
		this.presentationModel = new MainPresentationModel();
		this.proxy = new Proxy();
		tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		        clearMessages();
		    }
		});
		applyBindings();
		applyEventHandlers();
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
	
	private void applyEventHandlers() {
		/* Events for Textfields related to Invoice search */
		searchInvoiceDateFrom.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
		    public void handle(KeyEvent t) {
		        if (combo.match(t)) {
		            searchForInvoice();
		        }
		    }
		});
		
		searchInvoiceDateTo.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
		    public void handle(KeyEvent t) {
		        if (combo.match(t)) {
		            searchForInvoice();
		        }
		    }
		});
		
		searchInvoiceValueFrom.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
		    public void handle(KeyEvent t) {
		        if (combo.match(t)) {
		            searchForInvoice();
		        }
		    }
		});
		
		searchInvoiceValueTo.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
		    public void handle(KeyEvent t) {
		        if (combo.match(t)) {
		            searchForInvoice();
		        }
		    }
		});
		
		searchInvoiceCustomer.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
		    public void handle(KeyEvent t) {
		        if (combo.match(t)) {
		            searchForInvoice();
		        }
		    }
		});
		
		/* Events for Textfields related to Customer search */
		searchCustomerName.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
		    public void handle(KeyEvent t) {
		        if (combo.match(t)) {
		            searchForCustomer();
		        }
		    }
		});
		
		searchCustomerLastName.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
		    public void handle(KeyEvent t) {
		        if (combo.match(t)) {
		            searchForCustomer();
		        }
		    }
		});
		
		searchCustomerCompany.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
		    public void handle(KeyEvent t) {
		        if (combo.match(t)) {
		            searchForCustomer();
		        }
		    }
		});
	}
	
	@FXML private void searchForCustomer() {	
		CustomerSearchQuery search = new CustomerSearchQuery();
		search.set_name(searchCustomerCompany.getText());
		search.set_surname(searchCustomerName.getText());
		search.set_lastname(searchCustomerLastName.getText());
		ArrayList<Customer> searchResultList = proxy.searchCustomer(search.get_surname(), search.get_lastname(),
				search.get_name());
		if(searchResultList == null) {
			messageLabelSuche.setText("No search results found!");
			return;
		}		
		openCustomerWindow(searchResultList, search);
		clearMessages();
	}
	
	@FXML private void newCustomer() {
		Customer customer = new Customer();
		openEmptyCustomerWindow(customer);
		clearMessages();
	}
	
	@FXML private void searchForInvoice() {
		try{
			@SuppressWarnings("unused")
			Date convertedDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			if(!Utils.isNullOrEmpty(searchInvoiceDateFrom.getText())){
				convertedDate = sdf.parse(searchInvoiceDateFrom.getText());
			}
			if(!Utils.isNullOrEmpty(searchInvoiceDateTo.getText())){
				convertedDate = sdf.parse(searchInvoiceDateTo.getText());
			}
		} catch (ParseException e) {
			messageLabelSuche.setText("Field \"Datum von\" or \"Datum bis\" is not a valid date!");
			System.out.println("Field \"Datum von\" or \"Datum bis\" in TabPane \"Suche\" is not a valid date!");
			return;
		}
		
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
	
	private void openCustomerWindow(ArrayList<Customer> searchResultList, CustomerSearchQuery search) {		
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
			controller.setSearch(search);
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
