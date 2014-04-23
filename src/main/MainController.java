package main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import proxy.Proxy;
import utils.Utils;
import invoice.Invoice;
import invoice.InvoiceElement;
import contacts.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController<T> implements Initializable{
	
	private MainModel presentationModel;
	private Proxy proxy;
	private ArrayList<InvoiceElement> tmpInvoiceElements = new ArrayList<InvoiceElement>();
	private int lastIndex;
		
	@FXML TabPane tabPane;
	
	/* Variables related to TabPane Start */
	@FXML private Text totalCustomers;
	@FXML private Text totalInvoices;
	@FXML private Label messageLabelStart;
	
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
	
	/* Variables related to TabPane Rechnung */
	@FXML private TextField invoiceID;
	@FXML private TextField invoiceDate;
	@FXML private TextField invoiceCustomer;
	@FXML private TextField invoiceShippingAddress;
	@FXML private TextField invoiceAddress;
	@FXML private TextField invoiceComment;
	@FXML private TextArea invoiceMessage;
	@FXML private ComboBox<T> invoiceElement;
	@FXML private TextField invoiceElementAmount;
	@FXML private Button createInvoice;
	@FXML private Button clearInvoice;
	@FXML private Button addElement;
	@FXML private ComboBox<T> invoiceDirection;
	@FXML private Label messageLabelRechnung;
	@FXML private VBox elementList;
	
	/* Variables related to TabPane Suche */
	@FXML private TextField searchInvoiceDateFrom;
	@FXML private TextField searchInvoiceDateTo;
	@FXML private TextField searchInvoiceValueFrom;
	@FXML private TextField searchInvoiceValueTo;
	@FXML private TextField searchInvoiceCustomer;
	@FXML private TextField searchCustomerName;
	@FXML private TextField searchCustomerLastName;
	@FXML private TextField searchCustomerCompany;
	@FXML private Button searchCustomer;
	@FXML private Button searchInvoice;
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
		messageLabelKunde.setText("");
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
				invoice.set_articles(tmpInvoiceElements);
				tmpInvoiceElements.clear();
				
				messageLabelRechnung.setText("New Invoice created!");
				System.out.println("New Invoice created!");
			} catch(NumberFormatException e){
				messageLabelRechnung.setText("Field \"Rechnungsnummer\" in TabPane \"Rechnung\" is not an Integer!");
				System.out.println("Field \"Rechnungsnummer\" in TabPane \"Rechnung\" is not an Integer!");
			} catch(NullPointerException e){
				messageLabelRechnung.setText("One or more fields in TabPane \"Rechnung\" is empty!");
				System.out.println("One or more fields in TabPane \"Rechnung\" is empty!");
			}
		} else{
			messageLabelRechnung.setText("One or more required fields in TabPane \"Rechnung\" is empty!");
			System.out.println("One or more required fields in TabPane \"Rechnung\" is empty!");
		}
	}
	
	@FXML private void clearNewInvoice() {
		invoiceID.clear();
		invoiceDirection.getSelectionModel().clearSelection();
		invoiceDate.clear();
		invoiceCustomer.clear();
		invoiceShippingAddress.clear();
		invoiceAddress.clear();
		invoiceComment.clear();
		invoiceMessage.clear();
		invoiceElement.getSelectionModel().clearSelection();
		invoiceElementAmount.clear();
		tmpInvoiceElements.clear();
		messageLabelRechnung.setText("");
		elementList.getChildren().clear();
	}
	
	@FXML private void clearAddElement() {
		invoiceElement.getSelectionModel().clearSelection();
		invoiceElementAmount.clear();
	}
	
	@FXML private void addElement() {
		if(checkInvoiceELementInput()){
			try {
				InvoiceElement invElem = new InvoiceElement();
				
				invElem.set_name(invoiceElement.getValue().toString());
				invElem.set_amount(Integer.parseInt(invoiceElementAmount.getText()));
				invElem.set_price(100);
				tmpInvoiceElements.add(invElem);
				clearAddElement();
				displayInvoiceElements();
			} catch(NumberFormatException e){
				messageLabelRechnung.setText("Field \"Menge\" in TabPane \"Rechnung\" is not an Integer!");
				System.out.println("Field \"Menge\" in TabPane \"Rechnung\" is not an Integer!");
			}
		}
	}
	
	@FXML private void searchForCustomer() {		
		List<Customer> searchResultList = proxy.searchCustomer(searchCustomerName.getText(),
											searchCustomerLastName.getText(), searchCustomerCompany.getText());
		openSearchWindow(searchResultList, null);
	}
	
	@FXML private void searchForInvoice() {
		List<Invoice> searchResultList = proxy.searchInvoice(searchInvoiceDateFrom.getText(),
											searchInvoiceDateTo.getText(), searchInvoiceValueFrom.getText(), searchInvoiceValueTo.getText(),
											searchInvoiceCustomer.getText());
		openSearchWindow(null, searchResultList);
	}
	
	@FXML private void listAllCustomers() {						
		List<Customer> searchResultList = proxy.listAllCustomers();
		openSearchWindow(searchResultList, null);
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
		try {
			if(!Utils.isNullOrEmpty(invoiceID.getText())
				&& !Utils.isNullOrEmpty(invoiceDate.getText())
				&& !Utils.isNullOrEmpty(invoiceCustomer.getText())
				&& !Utils.isNullOrEmpty(invoiceShippingAddress.getText())
				&& !Utils.isNullOrEmpty(invoiceAddress.getText())
				&& !Utils.isNullOrEmpty(invoiceElement.getValue().toString())){
				return true;
			}
		} catch(NumberFormatException e){
			messageLabelRechnung.setText("Field \"ID\" in TabPane \"Rechnung\" is not an Integer!");
			System.out.println("Field \"ID\" in TabPane \"Rechnung\" is not an Integer!");
		} catch(NullPointerException e){
			messageLabelRechnung.setText("Field \"Richtung\" in TabPane \"Rechnung\" is not selected!");
			System.out.println("Field \"Richtung\" in TabPane \"Rechnung\" is not selected!");
		}	
		return false;
	}
	
	private boolean checkInvoiceELementInput() {
		try {
			if(!invoiceElement.getValue().toString().equals("Bezeichnung")
				&& Integer.parseInt(invoiceElementAmount.getText()) > 0) {
				return true;
			}
		} catch(NumberFormatException e){
			messageLabelRechnung.setText("Field \"Artikelbezeichnung\" in TabPane \"Rechnung\" is not an Integer!");
			System.out.println("Field \"Menge\" in TabPane \"Rechnung\" is not an Integer!");
		} catch(NullPointerException e){
			messageLabelRechnung.setText("Field \"Bezeichnung\" in TabPane \"Rechnung\" is not selected!");
			System.out.println("Field \"Bezeichnung\" in TabPane \"Rechnung\" is not selected!");
		}
		return false;
	}
	
	private void openSearchWindow(List<Customer> searchResultListCustom, List<Invoice> searchResultListInv) {		
		if(searchResultListCustom == null && searchResultListInv == null){
			messageLabelSuche.setText("No search results found!");
			return;
		}		
		try {			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchResult.fxml"));
			StackPane root = (StackPane)fxmlLoader.load();
			
			Stage secondStage = new Stage(StageStyle.DECORATED);		
			Scene scene = new Scene(root, 550, 300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			secondStage.setScene(scene);
			secondStage.setTitle("Suchergebnisse");
			
			SearchController controller = fxmlLoader.<SearchController>getController();
			controller.setSearchResultList(searchResultListCustom, searchResultListInv);
			secondStage.show();			
			controller.printSearchResults();			
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void displayInvoiceElements() {
		final Label label = new Label();
		StringBuilder strBuilder = new StringBuilder();
		lastIndex = tmpInvoiceElements.size() - 1;
			
		strBuilder.append("ID: ");
		strBuilder.append(lastIndex);
		strBuilder.append("  Name: ");
		strBuilder.append(tmpInvoiceElements.get(lastIndex).get_name());
		strBuilder.append("  Menge: ");
		strBuilder.append(tmpInvoiceElements.get(lastIndex).get_amount());
		strBuilder.append("  Preis: ");
		strBuilder.append(tmpInvoiceElements.get(lastIndex).get_price());
		strBuilder.append("  Total: ");
		strBuilder.append(tmpInvoiceElements.get(lastIndex).get_amount() * tmpInvoiceElements.get(lastIndex).get_price());
		strBuilder.append(" ");
			
		label.setText(strBuilder.toString());
		label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
            	// missing: remove InvoiceElement from tmpInvoiceElements array
            	elementList.getChildren().remove(label);
            }
        });
		elementList.getChildren().add(label);
	}
	
	private void clearMessages() {
		messageLabelStart.setText("");
		messageLabelKunde.setText("");
		messageLabelRechnung.setText("");
		messageLabelSuche.setText("");
	}
}
