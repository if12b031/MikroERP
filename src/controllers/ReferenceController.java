package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import proxy.Proxy;
import contacts.Customer;
import models.ReferencePresentationModel;
import models.SearchCustomerModel;
import models.SearchresultCustomerTableModel;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReferenceController<T> implements Initializable {
	
	private ArrayList<Customer> searchresultList;
	private CustomerController customerController;
	private InvoiceController<T> invoiceController;
	private Proxy proxy;
	private ReferencePresentationModel presentationModel;
	
	@FXML private TableView<SearchCustomerModel> searchresultTable;
	@FXML private TabPane tabPane;
	@FXML private TextField searchCustomerName;
    @FXML private TextField searchCustomerLastName;
	@FXML private TextField searchCustomerCompany;
		
	public void initialize(URL url, ResourceBundle resources) {
		searchresultTable.setPlaceholder(new Text(""));
		searchresultTable.setEditable(false);
		this.proxy = new Proxy();
		this.presentationModel = new ReferencePresentationModel();
				
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
	    // search when enter-key is pressed
        searchCustomerName.setOnKeyReleased(new EventHandler<KeyEvent>() {
            final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
            public void handle(KeyEvent t) {
                if (combo.match(t)) {
                    searchCustomer();
                }
            }
        });
        
        searchCustomerLastName.setOnKeyReleased(new EventHandler<KeyEvent>() {
            final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
            public void handle(KeyEvent t) {
                if (combo.match(t)) {
                    searchCustomer();
                }
            }
        });
        
        searchCustomerCompany.setOnKeyReleased(new EventHandler<KeyEvent>() {
            final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
            public void handle(KeyEvent t) {
                if (combo.match(t)) {
                    searchCustomer();
                }
            }
        });
	}
	
	@FXML private void setReference() {
	    int index = searchresultTable.getSelectionModel().getSelectedIndex();                  
        if(index >= 0){
            if(invoiceController != null) {
                if(searchresultList.get(index).get_name() == null) {
                    invoiceController.setReferenceName(searchresultList.get(index).get_surname()
                            + " " + searchresultList.get(index).get_lastname());
                } else {
                    invoiceController.setReferenceName(searchresultList.get(index).get_name());
                }
                invoiceController.setReferenceId(searchresultList.get(index).get_id());
                invoiceController.setWrongReference(false);
            } else {
                customerController.setReference(searchresultList.get(index).get_name());
                customerController.setWrongReference(false);
            }
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.close();
        }
	}
	
	@FXML private void searchCustomer() {
        searchresultList = proxy.searchCustomer(searchCustomerName.getText(), searchCustomerLastName.getText(), 
                searchCustomerCompany.getText());
        displaySearchresult();
    }
	
	public void displaySearchresult() {
	    if ( invoiceController == null ) {
            searchCustomerName.setDisable(true);
            searchCustomerLastName.setDisable(true);
        } else {
            applyBindings();
        }	    
	    SearchresultCustomerTableModel tableModel = new SearchresultCustomerTableModel(searchresultList);
		ObservableList<SearchCustomerModel> items = (ObservableList<SearchCustomerModel>) tableModel.getItems();
		
		searchresultTable.setItems(items);
		searchresultTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
			    if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)){
			        setReference();
			    }
			}
		});
	}
		
	public ArrayList<Customer> getSearchresultList() {
		return searchresultList;
	}

	public void setSearchResultList(ArrayList<Customer> searchresultList) {
		this.searchresultList = searchresultList;
	}
	
	public CustomerController getCustomerController() {
        return customerController;
    }

    public void setCustomerController(CustomerController controller) {
        this.customerController = controller;
    }
    
    public InvoiceController<T> getInvoiceController() {
        return invoiceController;
    }
    
    public void setInvoiceController(InvoiceController<T> controller) {
        this.invoiceController = controller;
    }
}
