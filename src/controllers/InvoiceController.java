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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class InvoiceController<T> implements Initializable {
	
	private InvoicePresentationModel presentationModel;
	private Proxy proxy;
	private ArrayList<InvoiceElement> tmpInvoiceElements = new ArrayList<InvoiceElement>();
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
	@FXML private ComboBox<T> invoiceElement;
	@FXML private TextField invoiceElementAmount;
	@FXML private Button createInvoice;
	@FXML private Button clearInvoice;
	@FXML private Button addElement;
	@FXML private ComboBox<T> invoiceDirection;
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
	}

	private void applyBindings() {
		
	}
	
	public void displaySearchResult() {
		
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
				
				proxy.createInvoice(invoice);
				
				tmpInvoiceElements.clear();
				clearNewInvoice();
				
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
		elementTable.setItems(null);
	}
	
	@FXML private void clearAddElement() {
		invoiceElement.getSelectionModel().clearSelection();
		invoiceElementAmount.clear();
	}
	
	@FXML private void addElement() {
		clearMessages();
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
			messageLabelRechnung.setText("Field \"Artikelbezeichnung\" in TabPane \"Rechnung\" is not an Integer!");
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
        InvoiceTableModel tableModel = new InvoiceTableModel(tmpInvoiceElements);
		ObservableList<InvoiceElementModel> items = (ObservableList<InvoiceElementModel>) tableModel.getItems();
		
		elementTable.setItems(items);
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
