package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.CustomerPresentationModel;
import proxy.Proxy;
import utils.Utils;
import contacts.Customer;
import eu.schudt.javafx.controls.calendar.DatePicker;

public class CustomerController implements Initializable{
	
	private CustomerPresentationModel presentationModel;
	private Customer searchResult;
	private Proxy proxy;
	private SearchresultCustomerController searchController;
	private DatePicker birthdayPicker;
	private boolean wrongReference = true;

    @FXML TabPane tabPane;
	
	/* Variables related to TabPane Kunde */
	@FXML private TextField customerName;
	@FXML private TextField customerUID;
	@FXML private TextField customerTitle;
	@FXML private TextField customerSuffix;
	@FXML private TextField customerSurname;
	@FXML private TextField customerLastname;
	@FXML private TextField customerWorksAt;
	@FXML private TextField customerAddress;
	@FXML private TextField customerPLZ;
	@FXML private TextField customerCity;
	@FXML private VBox companyPane;
	@FXML private VBox personPane;
	@FXML private HBox birthdayBox;
	@FXML private Button createCustomer;
	@FXML private Button clearCustomer;
	@FXML private Button cancelCustomer;
	@FXML private Label messageLabelKunde;
	
	public void initialize(URL url, ResourceBundle resources) {
		this.presentationModel = new CustomerPresentationModel();
		this.proxy = new Proxy();
		tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		        clearMessages();
		    }
		});
		
		// Initialize the DatePicker for birthday
		birthdayPicker = new DatePicker(Locale.GERMAN);
		birthdayPicker.setPromptText("YYYY-MM-DD");
		birthdayPicker.setPrefWidth(200);
		birthdayPicker.getStyleClass().add("padding");
		birthdayPicker.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		birthdayPicker.getCalendarView().todayButtonTextProperty().set("Today");
		birthdayPicker.getCalendarView().setShowWeeks(false);
		birthdayPicker.getStylesheets().add("main/application.css");

		// Add DatePicker to HBox
		birthdayBox.getChildren().add(birthdayPicker);
		
		customerWorksAt.focusedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue) {
		            // textfield is focused: nothing happens
		        }
		        else {
		            if(!Utils.isNullOrEmpty(customerWorksAt.getText())) {
                        checkCompany();
                    }
		        }
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
		customerAddress.textProperty().bindBidirectional(presentationModel.customerAddressProperty());
		customerPLZ.textProperty().bindBidirectional(presentationModel.customerPLZProperty());
		customerCity.textProperty().bindBidirectional(presentationModel.customerCityProperty());
		messageLabelKunde.textProperty().bindBidirectional(presentationModel.messageLabelProperty());

		companyPane.disableProperty().bind(
				presentationModel.disableEditCompanyBinding());
		personPane.disableProperty().bind(
				presentationModel.disableEditPersonBinding());
	}
	
	public void displaySearchresult() {
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
		
		if(searchResult.get_id() > 0){
			createCustomer.setText("Update");
			clearCustomer.setDisable(true);
		}
		
		if(!Utils.isNullOrEmpty(customerWorksAt.getText())) {
		    wrongReference = false;
		}
		
		try {
            birthdayPicker.setSelectedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN).parse((searchResult.get_dateOfBirth())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	@FXML private void createNewCustomer() {
		if(checkCustomerInput()){
			try {
			    if(Utils.isNullOrEmpty(customerWorksAt.getText())) {
			        wrongReference = false;
			    }
			    if( wrongReference ) {
                    messageLabelKunde.setText("Wrong reference in Field \"Arbeitet bei\"");
                    System.out.println("Wrong reference in Field \"Arbeitet bei\"");
                    return;
                }
			    
			    String birthday = null;
			    if( birthdayPicker.getSelectedDate() != null) {
			       birthday = new SimpleDateFormat("yyyy-MM-dd").format(birthdayPicker.getSelectedDate());
			    }
			    
			    Customer customer = new Customer();
				customer.set_id(searchResult.get_id());
				customer.set_name(customerName.getText());
				customer.set_uid(customerUID.getText());
				customer.set_title(customerTitle.getText());
				customer.set_suffix(customerSuffix.getText());
				customer.set_surname(customerSurname.getText());
				customer.set_lastname(customerLastname.getText());
				customer.set_dateOfBirth(birthday);
				customer.set_employedAt(customerWorksAt.getText());
				customer.set_address(customerAddress.getText());
				customer.set_plz(Integer.parseInt(customerPLZ.getText()));
				customer.set_city(customerCity.getText());
				
				int create = proxy.createCustomer(customer);
				if(create == 0 && customer.get_id() <= 0){
					clearNewCustomer();
					messageLabelKunde.setText("New Customer created!");
					System.out.println("New Customer created!");
				} else if(create == 0 && customer.get_id() > 0){
					messageLabelKunde.setText("Customer updated!");
					System.out.println("Customer updated!");
					searchController.updateSearchresult();
				} else{
					messageLabelKunde.setText("Error while creating/updating Customer!");
					System.out.println("Error while creating/updating Customer!");
				}
			} catch(NumberFormatException e){
				messageLabelKunde.setText("Field \"PLZ\" is not an Integer!");
				System.out.println("Field \"PLZ\" in TabPane \"Kunde\" is not an Integer!");
			}
		} else{
			messageLabelKunde.setText("One or more required fields are empty!");
			System.out.println("One or more required fields in TabPane \"Kunde\" are empty!");
		}
	}
	
	@FXML private void clearNewCustomer() {
		presentationModel.clearCustomer();
		birthdayPicker.setSelectedDate(null);
	}
	
	@FXML private void cancelNewCustomer() {
		Stage stage = (Stage) cancelCustomer.getScene().getWindow();
	    stage.close();
	}
	
	@FXML private void checkCompany() {
	    this.wrongReference = true;
	    ArrayList<Customer> result;
	    if(Utils.isNullOrEmpty(customerWorksAt.getText())) {
            result = proxy.searchCustomer(null, null, ".*");
        } else {
            result = proxy.searchCustomer(null, null, customerWorksAt.getText());
        }
        if ( result != null ) {
            if ( result.size() == 1 ) {
                customerWorksAt.setText(result.get(0).get_name());
                wrongReference = false;
            } else {
                openSearchWindow(result);
            } 
        } else {
            result = proxy.searchCustomer(null, null, ".*");
            openSearchWindow(result);
        }
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
		    if ( birthdayPicker.invalidProperty().get()) {
		        return false;
		    }
			return true;
		}
		return false;
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
    private void openSearchWindow(ArrayList<Customer> searchResultList) {      
        if(searchResultList.size() < 1){
            messageLabelKunde.setText("No search results found!");
            return;
        }       
        try {           
            wrongReference = true;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Reference.fxml"));
            TabPane root = (TabPane)fxmlLoader.load();
            
            Stage secondStage = new Stage(StageStyle.DECORATED);        
            Scene scene = new Scene(root, 666, 750);
            secondStage.initModality(Modality.WINDOW_MODAL);
            scene.getStylesheets().add(getClass().getResource("/main/application.css").toExternalForm());
            secondStage.setScene(scene);
            secondStage.setTitle("SWE 2 - MikroERP");
            
            ReferenceController controller = fxmlLoader.<ReferenceController>getController();
            controller.setSearchResultList(searchResultList);
            controller.setCustomerController(this);
            controller.displaySearchresult();
            secondStage.show();
            
        } catch (IOException e){
            e.printStackTrace();
        }
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

	public SearchresultCustomerController getSearchController() {
		return searchController;
	}

	public void setSearchController(SearchresultCustomerController searchController) {
		this.searchController = searchController;
	}
	
	public void setReference (String company) {
	    this.customerWorksAt.setText(company);
	}
	
	public void setWrongReference(boolean wrongReference) {
        this.wrongReference = wrongReference;
    }
}
