package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import models.InvoiceElementModel;
import models.InvoicePresentationModel;
import models.InvoiceTableModel;
import proxy.Proxy;
import utils.Utils;
import invoice.Invoice;
import invoice.InvoiceElement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class InvoiceController<T> implements Initializable {
	
	private InvoicePresentationModel presentationModel;
	private Proxy proxy;
	private ArrayList<InvoiceElement> addedInvoiceElements = new ArrayList<InvoiceElement>();
	private ArrayList<InvoiceElement> allInvoiceElements = new ArrayList<InvoiceElement>();
	private Invoice searchResult;
		
	@FXML TabPane tabPane;
	
	/* Variables related to TabPane Rechnung */
	@FXML private TextField invoiceID;
	@FXML private TextField invoiceDate;
	@FXML private TextField invoiceCustomer;
	@FXML private TextField invoiceShippingAddress;
	@FXML private TextField invoiceAddress;
	@FXML private TextField invoiceComment;
	@FXML private TextArea invoiceMessage;
	@FXML private ComboBox<InvoiceElement> invoiceElement;
	@FXML private ComboBox<T> invoiceDirection;
	@FXML private TextField invoiceElementAmount;
	@FXML private Button createInvoice;
	@FXML private Button clearInvoice;
	@FXML private Button cancelInvoice;
	@FXML private Button addElement;
	@FXML private Label messageLabelRechnung;
	@FXML private TableView<InvoiceElementModel> elementTable;
	
	public void initialize(URL url, ResourceBundle resources) {
		this.presentationModel = new InvoicePresentationModel();
		this.proxy = new Proxy();
		tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		        clearMessages();
		    }
		});
		elementTable.setPlaceholder(new Text(""));
		elementTable.setEditable(false);
		applyBindings();
		setArticles();
	}

	private void applyBindings() {
		invoiceID.textProperty().bindBidirectional(presentationModel.invoiceIDProperty());
		invoiceDate.textProperty().bindBidirectional(presentationModel.invoiceDateProperty());
		invoiceCustomer.textProperty().bindBidirectional(presentationModel.invoiceCustomerProperty());
		invoiceShippingAddress.textProperty().bindBidirectional(presentationModel.invoiceShippingAddressProperty());
		invoiceAddress.textProperty().bindBidirectional(presentationModel.invoiceAddressProperty());
		invoiceComment.textProperty().bindBidirectional(presentationModel.invoiceCommentProperty());
		invoiceMessage.textProperty().bindBidirectional(presentationModel.invoiceMessageProperty());
		invoiceElementAmount.textProperty().bindBidirectional(presentationModel.invoiceElementAmountProperty());
		messageLabelRechnung.textProperty().bindBidirectional(presentationModel.messageLabelProperty());
	}
	
	private void setArticles() {
		this.allInvoiceElements = proxy.getArticles();
		if(allInvoiceElements != null) {
			ObservableList<InvoiceElement> comboBoxElements = FXCollections.observableArrayList();
			for(int i=0; i<allInvoiceElements.size(); i++){
				comboBoxElements.add(allInvoiceElements.get(i));
			}
			invoiceElement.setItems(comboBoxElements);
			invoiceElement.setCellFactory(new Callback<ListView<InvoiceElement>, ListCell<InvoiceElement>>(){
		            @Override 
		            public ListCell<InvoiceElement> call(ListView<InvoiceElement> p){
		            	return new ListCell<InvoiceElement>(){             
		                    @Override 
		                    protected void updateItem(InvoiceElement item, boolean empty){
		                        super.updateItem(item, empty);
		                        
		                        if(item == null || empty){
		                            setGraphic(null);
		                        } else{
		                        	setText(item.get_name());
		                        }
		                   }
		              };
		          }
			});
			invoiceElement.setConverter(new StringConverter<InvoiceElement>(){
	              @Override
	              public String toString(InvoiceElement element){
	            	  if (element == null){
	            		  return null;
	            	  } else{
	            		  return element.get_name();
	            	  }
	              }
	              @Override
	              public InvoiceElement fromString(String name) {
	            	  return null;
	              }
	        });
		}
	}
	
	@SuppressWarnings("unchecked")
	public void displaySearchresult() {
		presentationModel.setInvoiceID(Integer.toString(searchResult.get_invoiceNumber()));
		presentationModel.setInvoiceDate(searchResult.get_creationDate());
		presentationModel.setInvoiceCustomer(searchResult.get_customerName());
		presentationModel.setInvoiceShippingAddress(searchResult.get_shippingAddress());
		presentationModel.setInvoiceAddress(searchResult.get_invoiceAddress());
		presentationModel.setInvoiceComment(searchResult.get_comment());
		presentationModel.setInvoiceMessage(searchResult.get_message());
		
		if(searchResult.get_articles() != null){
			this.addedInvoiceElements = searchResult.get_articles();
		}
		displayInvoiceElements();
		
		if(searchResult.is_isOutgoing()){
			invoiceDirection.setValue((T) "Ausgehend"); // suppressWarning because of this cast
		} else{
			invoiceDirection.setValue((T) "Eingehend"); // suppressWarning because of this cast
		}
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
				invoice.set_customerName(invoiceCustomer.getText());
				invoice.set_creationDate(invoiceDate.getText());
				invoice.set_comment(invoiceComment.getText());
				invoice.set_message(invoiceMessage.getText());
				invoice.set_shippingAddress(invoiceShippingAddress.getText());
				invoice.set_invoiceAddress(invoiceAddress.getText());
				invoice.set_articles(addedInvoiceElements);
				
				if(proxy.createInvoice(invoice) == 0){
					clearNewInvoice();					
					messageLabelRechnung.setText("New Invoice created!");
					System.out.println("New Invoice created!");
				} else {
					messageLabelRechnung.setText("Error while creating new Invoice!");
					System.out.println("Error while creating new Invoice!");
				}
				
				messageLabelRechnung.setText("New Invoice created!");
				System.out.println("New Invoice created!");
			} catch(NumberFormatException e){
				messageLabelRechnung.setText("Field \"Rechnungsnummer\" in TabPane \"Rechnung\" is not an Integer!");
				System.out.println("Field \"Rechnungsnummer\" in TabPane \"Rechnung\" is not an Integer!");
			} catch(NullPointerException e){
				messageLabelRechnung.setText("One or more required fields in TabPane \"Rechnung\" is empty!");
				System.out.println("One or more required fields in TabPane \"Rechnung\" is empty!");
			} 
		} else{
			messageLabelRechnung.setText("One or more required fields in TabPane \"Rechnung\" is empty!");
			System.out.println("One or more required fields in TabPane \"Rechnung\" is empty!");
		}
	}
	
	@FXML private void clearNewInvoice() {
		presentationModel.clearInvoice();
		elementTable.setItems(null);
		invoiceElement.getSelectionModel().clearSelection();
		invoiceDirection.getSelectionModel().clearSelection();
		addedInvoiceElements.clear();
	}
	
	@FXML private void cancelNewInvoice() {
		Stage stage = (Stage) cancelInvoice.getScene().getWindow();
	    stage.close();
	}
	
	@FXML private void addElement() {
		clearMessages();
		if(checkInvoiceELementInput()){
			try {
				InvoiceElement invElem = new InvoiceElement();
				int index = invoiceElement.getSelectionModel().getSelectedIndex();
				
				invElem.set_name(allInvoiceElements.get(index).get_name());
				invElem.set_amount(Integer.parseInt(invoiceElementAmount.getText()));
				invElem.set_price((allInvoiceElements.get(index).get_price()));
				addedInvoiceElements.add(invElem);
				clearAddElement();
				displayInvoiceElements();
			} catch(NumberFormatException e){
				e.printStackTrace();
				messageLabelRechnung.setText("Field \"Menge\" in TabPane \"Rechnung\" is not an Integer!");
				System.out.println("Field \"Menge\" in TabPane \"Rechnung\" is not an Integer!");
			}
		}
	}
	
	private boolean checkInvoiceInput() {
		try {
			if(Integer.parseInt(invoiceID.getText()) > 0
				&& !Utils.isNullOrEmpty(invoiceDate.getText())
				&& !Utils.isNullOrEmpty((String)invoiceDirection.getValue())
				&& !Utils.isNullOrEmpty(invoiceCustomer.getText())
				&& !Utils.isNullOrEmpty(invoiceShippingAddress.getText())
				&& !Utils.isNullOrEmpty(invoiceAddress.getText())
				&& !elementTable.getItems().isEmpty()){
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
			messageLabelRechnung.setText("Field \"Menge\" in TabPane \"Rechnung\" is not an Integer!");
			System.out.println("Field \"Menge\" in TabPane \"Rechnung\" is not an Integer!");
		} catch(NullPointerException e){
			messageLabelRechnung.setText("Field \"Bezeichnung\" in TabPane \"Rechnung\" is not selected!");
			System.out.println("Field \"Bezeichnung\" in TabPane \"Rechnung\" is not selected!");
		}
		if(Integer.parseInt(invoiceElementAmount.getText()) <= 0){
			messageLabelRechnung.setText("Field \"Menge\" in TabPane \"Rechnung\" is not valid!");
			System.out.println("Field \"Menge\" in TabPane \"Rechnung\" is not valid!");
		}
		return false;
	}
	
	private void displayInvoiceElements() {
        InvoiceTableModel tableModel = new InvoiceTableModel(addedInvoiceElements);
		ObservableList<InvoiceElementModel> items = (ObservableList<InvoiceElementModel>) tableModel.getItems();
		
		elementTable.setItems(items);
		elementTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
			    if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)){
			    	int index = elementTable.getSelectionModel().getSelectedIndex();
			    	if(index >= 0){
			    		addedInvoiceElements.remove(index);
			    		displayInvoiceElements();
			    	}
			    }
			}
		});
	}
	
	private void clearAddElement() {
		invoiceElement.getSelectionModel().clearSelection();
		invoiceElementAmount.clear();
	}
	
	private void clearMessages() {
		messageLabelRechnung.setText("");
	}

	public Invoice getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(Invoice searchResult) {
		this.searchResult = searchResult;
	}
}
