package main;

import java.net.URL;
import java.util.ResourceBundle;

import contacts.Person;
import contacts.Company;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MainController {

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
	
	private MainModel presentationModel;
	private Person person;
	private Company company;

	public void setPerson(Object model) {
		this.person = (Person) model;
		this.presentationModel.setPerson(this.person);
	}
	
	public void setCompany(Object model) {
		this.company = (Company) model;
		this.presentationModel.setCompany(this.company);
	}

	public void initialize(URL url, ResourceBundle rb) {
		this.presentationModel = new MainModel();
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
		customerAddress.textProperty().bindBidirectional(presentationModel.customerAddressProperty());
		customerPLZ.textProperty().bindBidirectional(presentationModel.customerPLZProperty());
		customerCity.textProperty().bindBidirectional(presentationModel.customerCityProperty());
		
		personPane.disableProperty().bind(
				presentationModel.disableEditPersonBinding());
		companyPane.disableProperty().bind(
				presentationModel.disableEditCompanyBinding());
	}
}
