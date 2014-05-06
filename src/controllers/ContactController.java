package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.ContactPresentationModel;
import proxy.Proxy;
import utils.Utils;
import contacts.Customer;

public class ContactController<T> implements Initializable{
	
	private ContactPresentationModel presentationModel;
	private Proxy proxy;
	private Customer searchResult;
		
	@FXML TabPane tabPane;
	
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
	@FXML private Label messageLabelKunde;
	
	public void initialize(URL url, ResourceBundle resources) {
		this.presentationModel = new ContactPresentationModel();
		this.proxy = new Proxy();
		tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		        clearMessages();
		    }
		});
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
		messageLabelKunde.textProperty().bindBidirectional(presentationModel.messageLabelProperty());

		companyPane.disableProperty().bind(
				presentationModel.disableEditCompanyBinding());
		personPane.disableProperty().bind(
				presentationModel.disableEditPersonBinding());
	}
	
	public void displaySearchResult() {
		presentationModel.setCustomerName(searchResult.get_name());
		presentationModel.setCustomerUID(searchResult.get_uid());
		presentationModel.setCustomerTitle(searchResult.get_title());
		presentationModel.setCustomerSuffix(searchResult.get_suffix());
		presentationModel.setCustomerSurname(searchResult.get_surname());
		presentationModel.setCustomerLastname(searchResult.get_lastname());
		presentationModel.setCustomerWorksAt(searchResult.get_employedAt());
		presentationModel.setCustomerBirthday(searchResult.get_dateOfBirth());
		presentationModel.setCustomerAddress(searchResult.get_address());
		if(searchResult.get_plz() == 0){
			presentationModel.setCustomerPLZ("");
		} else{
			presentationModel.setCustomerPLZ(Integer.toString(searchResult.get_plz()));
		}
		presentationModel.setCustomerCity(searchResult.get_city());
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
				
				proxy.createCustomer(customer);
				
				clearNewCustomer();
				
				messageLabelKunde.setText("New Customer created!");
				System.out.println("New Customer created!");
			} catch(NumberFormatException e){
				messageLabelKunde.setText("Field \"PLZ\" in TabPane \"Kunde\" is not an Integer!");
				System.out.println("Field \"PLZ\" in TabPane \"Kunde\" is not an Integer!");
			}
		} else{
			messageLabelKunde.setText("One or more required fields in TabPane \"Kunde\" is empty!");
			System.out.println("One or more required fields in TabPane \"Kunde\" is empty!");
		}
	}
	
	@FXML private void clearNewCustomer() {
		presentationModel.clearCustomer();
	}
			
	private boolean checkCustomerInput() {
		if(Utils.isNullOrEmpty(customerAddress.getText())
			|| Utils.isNullOrEmpty(customerPLZ.getText())
			|| Utils.isNullOrEmpty(customerCity.getText())){
			return false;
		} else if((!Utils.isNullOrEmpty(customerName.getText())
			&& !Utils.isNullOrEmpty(customerUID.getText()))
			|| (!Utils.isNullOrEmpty(customerSurname.getText())
				&& !Utils.isNullOrEmpty(customerLastname.getText()))){
			return true;
		}
		return false;
	}
	
	private void clearMessages() {
		messageLabelKunde.setText("");
	}

	public Customer getSearchResultList() {
		return searchResult;
	}

	public void setSearchResult(Customer searchResult) {
		this.searchResult = searchResult;
	}
}
