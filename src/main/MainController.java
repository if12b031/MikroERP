package main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import proxy.Proxy;
import utils.Utils;
import invoice.Invoice;
import contacts.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainController<T> implements Initializable{
	
	private MainModel presentationModel;
	private Proxy proxy;
	
	/* Variables related to TabPane Kunde */
	@FXML private Text totalCustomers;
	@FXML private Text totalInvoices;
	
	/* Variables related to TabPane Kunde */
	@FXML private TextField customerName;
	@FXML private TextField customerUID;
	@FXML private TextField customerTitle;
	@FXML private TextField customerSuffix;
	@FXML private TextField customerSurname;
	@FXML private TextField customerLastname;
	@FXML private TextField customerWorksAt;
	@FXML private TextField customerBirthday;
	@FXML private TextField customerAddress;
	@FXML private TextField customerPLZ;
	@FXML private TextField customerCity;
	@FXML private VBox companyPane;
	@FXML private VBox personPane;
	@FXML private Button createCustomer;
	@FXML private Button clearCustomer;
	
	/* Variables related to TabPane Rechnung */
	@FXML private TextField invoiceID;
	@FXML private TextField invoiceDate;
	@FXML private TextField invoiceCustomer;
	@FXML private TextField invoiceShippingAddress;
	@FXML private TextField invoiceAddress;
	@FXML private TextField invoiceComment;
	@FXML private TextField invoiceMessage;
	@FXML private TextField invoiceElement;
	@FXML private Button createInvoice;
	@FXML private Button clearInvoice;
	@FXML private ComboBox<T> invoiceDirection;
	
	/* Variables related to TabPane Suche */
	@FXML private TextField searchInvoiceID;
	@FXML private TextField searchInvoiceDate;
	@FXML private TextField searchInvoiceCustomer;
	@FXML private TextField searchCustomerName;
	@FXML private TextField searchCustomerLastName;
	@FXML private TextField searchCustomerCompany;
	@FXML private Button searchCustomer;
	@FXML private Button searchInvoice;
	@FXML private VBox searchResults;
	
	public void initialize(URL url, ResourceBundle resources) {
		this.presentationModel = new MainModel();
		this.proxy = new Proxy();
		applyBindings();		
	}

	private void applyBindings() {
		customerName.textProperty().bindBidirectional(presentationModel.customerNameProperty());
		customerUID.textProperty().bindBidirectional(presentationModel.customerUIDProperty());
		customerTitle.textProperty().bindBidirectional(presentationModel.customerTitleProperty());
		customerSuffix.textProperty().bindBidirectional(presentationModel.customerSuffixProperty());
		customerSurname.textProperty().bindBidirectional(presentationModel.customerSurnameProperty());
		customerLastname.textProperty().bindBidirectional(presentationModel.customerLastnameProperty());
		customerWorksAt.textProperty().bindBidirectional(presentationModel.customerWorksAtProperty());
		customerBirthday.textProperty().bindBidirectional(presentationModel.customerBirthdayProperty());
		customerAddress.textProperty().bindBidirectional(presentationModel.customerAddressProperty());
		customerPLZ.textProperty().bindBidirectional(presentationModel.customerPLZProperty());
		customerCity.textProperty().bindBidirectional(presentationModel.customerCityProperty());

		companyPane.disableProperty().bind(
				presentationModel.disableEditCompanyBinding());
		personPane.disableProperty().bind(
				presentationModel.disableEditPersonBinding());
	}
	
	@FXML private void createNewCustomer() {
		if(checkCustomerInput()){
			try {
				Customer customer = new Customer();
				customer.set_name(customerName.getText());
				customer.set_uid(customerUID.getText());
				customer.set_title(customerTitle.getText());
				customer.set_suffix(customerSuffix.getText());
				customer.set_surname(customerSurname.getText());
				customer.set_lastname(customerLastname.getText());
				customer.set_dateOfBirth(customerBirthday.getText());
				customer.set_employedAt(customerWorksAt.getText());
				customer.set_address(customerAddress.getText());
				customer.set_plz(Integer.parseInt(customerPLZ.getText()));
				customer.set_city(customerCity.getText());
				
				System.out.println("New Customer created!");
			} catch(NumberFormatException e){
				System.out.println("Field \"PLZ\" in TabPane \"Kunde\" is not an Integer!");
	        	e.printStackTrace();
			}
		} else{
			System.out.println("One or more required fields in TabPane \"Kunde\" is empty!");
		}
	}
	
	@FXML private void clearNewCustomer() {
		customerName.clear();
		customerUID.clear();
		customerTitle.clear();
		customerSuffix.clear();
		customerSurname.clear();
		customerLastname.clear();
		customerWorksAt.clear();
		customerBirthday.clear();
		customerAddress.clear();
		customerPLZ.clear();
		customerCity.clear();
	}
	
	@FXML private void createNewInvoice() {
		if(checkInvoiceInput()){
			try {
				Invoice invoice = new Invoice();
				boolean isOutgoing = false;
				
				if(((String)invoiceDirection.getValue()).equals("Ausgehend")){
					isOutgoing = true;
				}
				
				invoice.set_invoiceNumber(Integer.parseInt(invoiceID.getText()));
				invoice.set_isOutgoing(isOutgoing);
				invoice.set_creationDate(invoiceDate.getText());
				invoice.set_comment(invoiceComment.getText());
				invoice.set_message(invoiceMessage.getText());
				invoice.set_shippingAddress(invoiceShippingAddress.getText());
				invoice.set_invoiceAddress(invoiceAddress.getText());
				
				System.out.println("New Invoice created!");
			} catch(NumberFormatException e){
				System.out.println("Field \"Rechnungsnummer\" in TabPane \"Rechnung\" is not an Integer!");
	        	e.printStackTrace();
			} catch(NullPointerException e){
				System.out.println("One or more fields in TabPane \"Rechnung\" is empty!");
	        	e.printStackTrace();
			}
		} else{
			System.out.println("One or more required fields in TabPane \"Rechnung\" is empty!");
		}
	}
	
	@FXML private void clearNewInvoice() {
		invoiceID.clear();
		invoiceDate.clear();
		invoiceCustomer.clear();
		invoiceShippingAddress.clear();
		invoiceAddress.clear();
		invoiceComment.clear();
		invoiceMessage.clear();
		invoiceElement.clear();
	}
	
	@FXML private void searchForCustomer() {		
			
	}
	
	@FXML private void listAllCustomers() {		
		List<Customer> result = proxy.listAllCustomers();		
		searchResults.getChildren().clear();
		
		for(int i=0; i<result.size(); i++) {
			Label label = new Label();
			StringBuilder strBuilder = new StringBuilder();
			
			strBuilder.append(result.get(i).get_title());
			strBuilder.append(" ");
			strBuilder.append(result.get(i).get_surname());
			strBuilder.append(" ");
			strBuilder.append(result.get(i).get_lastname());
			strBuilder.append(" ");
			strBuilder.append(result.get(i).get_dateOfBirth());
			strBuilder.append(" ");
			strBuilder.append(result.get(i).get_address());
			strBuilder.append(" ");
			strBuilder.append(result.get(i).get_plz());
			strBuilder.append(" ");
			strBuilder.append(result.get(i).get_city());
			strBuilder.append(" ");

			label.setText(strBuilder.toString());
			searchResults.getChildren().add(label);			
		}		
	}
	
	@FXML private void searchForInvoice() {
		
	}
	
	private boolean checkCustomerInput() {
		if(Utils.isNullOrEmpty(customerAddress.getText())
			|| Utils.isNullOrEmpty(customerPLZ.getText())
			|| Utils.isNullOrEmpty(customerCity.getText())){
			return false;
		} else if((!Utils.isNullOrEmpty(customerName.getText())
			&& !Utils.isNullOrEmpty(customerUID.getText()))
			|| (!Utils.isNullOrEmpty(customerTitle.getText())
				&& !Utils.isNullOrEmpty(customerTitle.getText())
				&& !Utils.isNullOrEmpty(customerSuffix.getText())
				&& !Utils.isNullOrEmpty(customerSurname.getText())
				&& !Utils.isNullOrEmpty(customerLastname.getText()))){
			return true;
		}
		return false;
	}
	
	private boolean checkInvoiceInput() {
		if(!Utils.isNullOrEmpty(invoiceID.getText())
			&& !Utils.isNullOrEmpty(invoiceDate.getText())
			&& !Utils.isNullOrEmpty(invoiceCustomer.getText())
			&& !Utils.isNullOrEmpty(invoiceShippingAddress.getText())
			&& !Utils.isNullOrEmpty(invoiceAddress.getText())
			&& !Utils.isNullOrEmpty(invoiceElement.getText())){
			return true;
		}
		return false;
	}
}
