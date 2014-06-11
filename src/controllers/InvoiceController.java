package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import contacts.Customer;
import eu.schudt.javafx.controls.calendar.DatePicker;
import models.InvoiceElementModel;
import models.InvoicePresentationModel;
import models.InvoiceTableModel;
import proxy.Proxy;
import utils.Utils;
import invoice.CalculatedValues;
import invoice.Invoice;
import invoice.InvoiceElement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class InvoiceController<T> implements Initializable {
	
	private InvoicePresentationModel presentationModel;
	private Proxy proxy;
	private ArrayList<InvoiceElement> addedInvoiceElements = new ArrayList<InvoiceElement>();
	private ArrayList<InvoiceElement> allInvoiceElements = new ArrayList<InvoiceElement>();
	private Invoice searchResult;
	private DatePicker datePicker;
	private int invoiceCustomerId = 0;
	private boolean wrongReference = true;
		
	@FXML TabPane tabPane;
	
	/* Variables related to TabPane Rechnung */
	@FXML private TextField invoiceID;
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
	@FXML private Button printInvoice;
	@FXML private Button addElement;
	@FXML private Button setCustomer;
	@FXML private Label invoiceNet;
	@FXML private Label invoiceUst;
	@FXML private Label invoiceTotal;
	@FXML private Label messageLabelRechnung;
	@FXML private HBox invoiceDateBox;
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
		printInvoice.setDisable(true);
		
		// Initialize the DatePicker for invoice date
        datePicker = new DatePicker(Locale.GERMAN);
        datePicker.setPrefWidth(200);
        datePicker.setPromptText("YYYY-MM-DD");
        datePicker.getStyleClass().add("padding");
        datePicker.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        datePicker.getCalendarView().todayButtonTextProperty().set("Today");
        datePicker.getCalendarView().setShowWeeks(false);
        datePicker.getStylesheets().add("main/application.css");

        // Add DatePicker to HBox
        invoiceDateBox.getChildren().add(datePicker);
        
        invoiceCustomer.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (newPropertyValue) {
                    // textfield is focused: nothing happens
                }
                else {
                    if(!Utils.isNullOrEmpty(invoiceCustomer.getText())) {
                        checkCustomer();
                    }
                }
            }
        });
        
        applyBindings();
        setArticles();
	}

	private void applyBindings() {
		invoiceID.textProperty().bindBidirectional(presentationModel.invoiceIDProperty());
		invoiceCustomer.textProperty().bindBidirectional(presentationModel.invoiceCustomerProperty());
		invoiceShippingAddress.textProperty().bindBidirectional(presentationModel.invoiceShippingAddressProperty());
		invoiceAddress.textProperty().bindBidirectional(presentationModel.invoiceAddressProperty());
		invoiceComment.textProperty().bindBidirectional(presentationModel.invoiceCommentProperty());
		invoiceMessage.textProperty().bindBidirectional(presentationModel.invoiceMessageProperty());
		invoiceElementAmount.textProperty().bindBidirectional(presentationModel.invoiceElementAmountProperty());
		invoiceNet.textProperty().bindBidirectional(presentationModel.invoiceNetProperty());
		invoiceUst.textProperty().bindBidirectional(presentationModel.invoiceUstProperty());
		invoiceTotal.textProperty().bindBidirectional(presentationModel.invoiceTotalProperty());
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
		
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator('.');
		DecimalFormat format = new DecimalFormat("###,##0.00", symbols); // format input to 2 decimal places (e.g. 20,1344 to 20,13)
		presentationModel.setInvoiceNet("Netto: " + format.format(searchResult.get_net()));
		presentationModel.setInvoiceUst("Ust: " + format.format(searchResult.get_ust()));
		presentationModel.setInvoiceTotal("Total: " + format.format(searchResult.get_gross()));
		
		if(searchResult.get_articles() != null){
			this.addedInvoiceElements = searchResult.get_articles();
		}
		displayInvoiceElements();
		
		if(searchResult.is_isOutgoing()){
			invoiceDirection.setValue((T) "Ausgehend"); // suppressWarnings because of this cast
		} else{
			invoiceDirection.setValue((T) "Eingehend"); // suppressWarnings because of this cast
		}
		
		if(searchResult.get_id() > 0){
			/* Buttons */
			createInvoice.setText("Update");
			createInvoice.setDisable(true);
			clearInvoice.setDisable(true);
			addElement.setDisable(true);
			printInvoice.setDisable(false);
			setCustomer.setDisable(true);
			/* Textfields */
			invoiceID.setDisable(true);
			invoiceCustomer.setDisable(true);
			invoiceShippingAddress.setDisable(true);
			invoiceAddress.setDisable(true);
			invoiceComment.setDisable(true);
			invoiceMessage.setDisable(true);
			invoiceElement.setDisable(true);
			invoiceDirection.setDisable(true);
			invoiceElementAmount.setDisable(true);
			/* DatePicker */
			datePicker.setDisable(true);
		}
		
		try {
		    datePicker.setSelectedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN).parse((searchResult.get_creationDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	@FXML private void addElement() {
		clearMessages();
		switch(checkInvoiceELementInput()){
			case 0:	try {
						InvoiceElement invElem = new InvoiceElement();
						int index = invoiceElement.getSelectionModel().getSelectedIndex();
						
						invElem.set_name(allInvoiceElements.get(index).get_name());
						invElem.set_amount(Integer.parseInt(invoiceElementAmount.getText()));
						invElem.set_price((allInvoiceElements.get(index).get_price()));
						addedInvoiceElements.add(invElem);
						clearAddElement();
						displayInvoiceElements();
						calculateInvoiceValues();
					} catch(NumberFormatException e){
		
					}
					break;
			case 1:	messageLabelRechnung.setText("Field \"Menge\" is not an Integer!");
					System.out.println("Field \"Menge\" in TabPane \"Rechnung\" is not an Integer!");
					break;
			case 2:	messageLabelRechnung.setText("Field \"Bezeichnung\" is not selected!");
					System.out.println("Field \"Bezeichnung\" in TabPane \"Rechnung\" is not selected!");
					break;
			case 3:	messageLabelRechnung.setText("Field \"Menge\" is not valid!");
					System.out.println("Field \"Menge\" in TabPane \"Rechnung\" is not valid!");
					break;
			default:	messageLabelRechnung.setText("One or more required fields related to \"Artikel\" are empty!");
						System.out.println("One or more required fields related to \"Artikel\" are empty!");
		}
	}
	
	@FXML private void createNewInvoice() {
		switch(checkInvoiceInput()){
			case 0:	try {
        			    if( wrongReference ) {
                            messageLabelRechnung.setText("Wrong reference in Field \"Kundenname\"");
                            System.out.println("Wrong reference in Field \"Kundenname\"");
                            return;
        			    }
			            Invoice invoice = new Invoice();
						boolean isOutgoing = false;
						String invoiceDate = new SimpleDateFormat("yyyy-MM-dd").format(datePicker.getSelectedDate());
						
						if(((String)invoiceDirection.getValue()).equals("Ausgehend")){
							isOutgoing = true;
						}
						invoice.set_id(searchResult.get_id());
						invoice.set_invoiceNumber(Integer.parseInt(invoiceID.getText()));
						invoice.set_customerId(this.invoiceCustomerId);
						invoice.set_isOutgoing(isOutgoing);
						invoice.set_customerName(invoiceCustomer.getText());
						invoice.set_creationDate(invoiceDate);
						invoice.set_comment(invoiceComment.getText());
						invoice.set_message(invoiceMessage.getText());
						invoice.set_shippingAddress(invoiceShippingAddress.getText());
						invoice.set_invoiceAddress(invoiceAddress.getText());
						invoice.set_articles(addedInvoiceElements);
						
						String invUst = invoiceUst.getText().replaceAll("Ust: ", "");
						String invNet = invoiceNet.getText().replaceAll("Netto: ", "");
						String invTotal = invoiceTotal.getText().replaceAll("Total: ", "");
						
						double ust = Double.parseDouble(invUst.replaceAll(",", "."));
						double net = Double.parseDouble(invNet.replaceAll(",", "."));
						double total = Double.parseDouble(invTotal.replaceAll(",", "."));
						
						invoice.set_net(net);
						invoice.set_ust(ust);
						invoice.set_gross(total);
						
						if(proxy.createInvoice(invoice) == 0){
							clearNewInvoice();					
							messageLabelRechnung.setText("New Invoice created!");
							System.out.println("New Invoice created!");
						} else {
							messageLabelRechnung.setText("Error while creating new Invoice!");
							System.out.println("Error while creating new Invoice!");
						}
					} catch(NumberFormatException e){

					} catch(NullPointerException e){
						
					}
					break;
			case 1:	messageLabelRechnung.setText("Field \"Rechnungsnummer\" is not an Integer!");
					System.out.println("Field \"ID\" in TabPane \"Rechnung\" is not an Integer!");
					break;
			case 2: messageLabelRechnung.setText("Field \"Richtung\" is not selected!");
					System.out.println("Field \"Richtung\" in TabPane \"Rechnung\" is not selected!");
					break;
			case 3: messageLabelRechnung.setText("Field \"Rechnungsdatum\" is not a valid date!");
					System.out.println("Field \"Rechnungsdatum\" in TabPane \"Rechnung\" is not a valid date!");
					break;
			case 4: messageLabelRechnung.setText("One or more required fields are empty!");
					System.out.println("One or more required fields in TabPane \"Rechnung\" are empty!");
					break;
			default:	messageLabelRechnung.setText("One or more required fields are empty!");
						System.out.println("One or more required fields in TabPane \"Rechnung\" are empty!");
		}
	}
			
	@FXML private void clearNewInvoice() {
		presentationModel.clearInvoice();
		elementTable.setItems(null);
		invoiceElement.getSelectionModel().clearSelection();
		invoiceDirection.getSelectionModel().clearSelection();
		addedInvoiceElements.clear();
		datePicker.setSelectedDate(null);
	}
	
	@FXML private void cancelNewInvoice() {
		Stage stage = (Stage) cancelInvoice.getScene().getWindow();
	    stage.close();
	}
	
	@FXML private void printInvoice() {
		Document document = new Document();
		List orderedList = new List(List.ORDERED);
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setDecimalSeparator('.');
		DecimalFormat format = new DecimalFormat("#0.00", symbols); // format input to 2 decimal places (e.g. 20,1344 to 20,13)
		try {
			for(int i=0; i<addedInvoiceElements.size(); i++){
				InvoiceElement elem = addedInvoiceElements.get(i);
				orderedList.add(new ListItem(i + ". " + elem.get_name() + ": " + format.format(elem.get_price()) +
						" x " + elem.get_amount() + " = " + format.format(elem.get_price()*elem.get_amount())));
			}
            
			PdfWriter.getInstance(document,
                    new FileOutputStream("InvoicePDFs/Invoice_" + searchResult.get_invoiceNumber() + ".pdf"));
        	
			document.open();
            document.add(new Paragraph("Invoice Number: " + searchResult.get_invoiceNumber()));
            document.add(new Paragraph("Creation Date: " + searchResult.get_creationDate()));
            document.add(new Paragraph("Customer: " + searchResult.get_customerName()));
            document.add(new Paragraph("Invoice Address: " + searchResult.get_invoiceAddress()));
            document.add(new Paragraph("Shipping Address: " + searchResult.get_shippingAddress()));
            document.add(new Paragraph("Comment: " + searchResult.get_comment()));
            document.add(new Paragraph("Message: " + searchResult.get_message()));
            document.add(orderedList);
            document.add(new Paragraph("Net: " + format.format(searchResult.get_net())));
            document.add(new Paragraph("Ust: " + format.format(searchResult.get_ust())));
            document.add(new Paragraph("Total: " + format.format(searchResult.get_gross())));
            document.close();
            
            /* open PDF in PDF viewing program installed on PC */
            if (Desktop.isDesktopSupported()) {
            	File myFile = new File("InvoicePDFs/Invoice_" + searchResult.get_invoiceNumber() + ".pdf");
                Desktop.getDesktop().open(myFile);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML private void checkCustomer() {
	    this.wrongReference = true;
	    ArrayList<Customer> result;
        if(Utils.isNullOrEmpty(invoiceCustomer.getText())) {
            result = proxy.searchCustomer(null, null, null);
        } else {
            result = proxy.searchCustomer(".*", invoiceCustomer.getText(),
                    null);
        }
        if ( result != null ) {
            if ( result.size() == 1 ) {
                if(result.get(0).get_name() == null) {
                    invoiceCustomer.setText(result.get(0).get_surname()
                            + " " + result.get(0).get_lastname());
                } else {
                    invoiceCustomer.setText(result.get(0).get_name());
                }                
                this.invoiceCustomerId = result.get(0).get_id();
                wrongReference = false;
            } else {
                openSearchWindow(result);
            } 
        } else {
            result = proxy.searchCustomer(invoiceCustomer.getText(), ".*", null);
            if ( result != null ) {
                if ( result.size() == 1 ) {
                    if(result.get(0).get_name() == null) {
                        invoiceCustomer.setText(result.get(0).get_surname()
                                + " " + result.get(0).get_lastname());
                    } else {
                        invoiceCustomer.setText(result.get(0).get_name());
                    }
                    this.invoiceCustomerId = result.get(0).get_id();
                    wrongReference = false;
                } else {
                    openSearchWindow(result); 
                } 
            } else {
                result = proxy.searchCustomer(null, null, invoiceCustomer.getText());
                if ( result != null ) {
                    if ( result.size() == 1 ) {
                        if(result.get(0).get_name() == null) {
                            invoiceCustomer.setText(result.get(0).get_surname()
                                    + " " + result.get(0).get_lastname());
                        } else {
                            invoiceCustomer.setText(result.get(0).get_name());
                        }
                        this.invoiceCustomerId = result.get(0).get_id();
                        wrongReference = false;
                    } else {
                        openSearchWindow(result);
                    }
                } else {
                    result = proxy.searchCustomer(null, null, null);
                    openSearchWindow(result);
                }
            }
        }
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    private void openSearchWindow(ArrayList<Customer> searchResultList) {      
        if(searchResultList.size() < 1){
            messageLabelRechnung.setText("No search results found!");
            return;
        }       
        try {           
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Reference.fxml"));
            TabPane root = (TabPane)fxmlLoader.load();
            
            Stage secondStage = new Stage(StageStyle.DECORATED);
            secondStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root, 666, 750);
            scene.getStylesheets().add(getClass().getResource("/main/application.css").toExternalForm());
            secondStage.setScene(scene);
            secondStage.setTitle("SWE 2 - MikroERP");
            
            ReferenceController controller = fxmlLoader.<ReferenceController>getController();
            controller.setSearchResultList(searchResultList);
            controller.setInvoiceController(this);
            controller.displaySearchresult();
            secondStage.show();
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
	
	private int checkInvoiceInput() {
		try {
			if(Integer.parseInt(invoiceID.getText()) > 0
				&& !Utils.isNullOrEmpty(datePicker.getSelectedDate().toString())
				&& !Utils.isNullOrEmpty((String)invoiceDirection.getValue())
				&& !Utils.isNullOrEmpty(invoiceCustomer.getText())
				&& !Utils.isNullOrEmpty(invoiceShippingAddress.getText())
				&& !Utils.isNullOrEmpty(invoiceAddress.getText())
				&& invoiceCustomerId > 0
				&& !elementTable.getItems().isEmpty()){
			    if ( datePicker.invalidProperty().get()) {
	                return 3;
	            }
				return 0;
			} 
		} catch(NumberFormatException e){
			return 1;
		} catch(NullPointerException e){
			return 2;
		}
		return 4;
	}
	
	private int checkInvoiceELementInput() {
		try {
			if(!invoiceElement.getValue().toString().equals("Bezeichnung")
				&& Integer.parseInt(invoiceElementAmount.getText()) > 0) {
				return 0;
			}
		} catch(NumberFormatException e){
			return 1;
		} catch(NullPointerException e){
			return 2;
		}
		if(Integer.parseInt(invoiceElementAmount.getText()) <= 0){
			return 3;
		}
		return 4;
	}
	
	private void displayInvoiceElements() {
        InvoiceTableModel tableModel = new InvoiceTableModel(addedInvoiceElements);
		ObservableList<InvoiceElementModel> items = (ObservableList<InvoiceElementModel>) tableModel.getItems();
		
		elementTable.setItems(items);
		if(searchResult.get_id() <= 0){
			elementTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
				    if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)){
				    	int index = elementTable.getSelectionModel().getSelectedIndex();
				    	if(index >= 0){
				    		addedInvoiceElements.remove(index);
				    		displayInvoiceElements();
				    		calculateInvoiceValues();
				    	}
				    }
				}
			});
		}
	}
	
	private void calculateInvoiceValues() {
		ArrayList<Double> prices = new ArrayList<Double>();
		
		for(int i=0; i<addedInvoiceElements.size(); i++){
			double price = addedInvoiceElements.get(i).get_price();
			int amount = addedInvoiceElements.get(i).get_amount();
			prices.add(price*amount);
		}
		
		CalculatedValues values = proxy.calculateValue(prices);
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator('.');
		DecimalFormat format = new DecimalFormat("###,##0.00", symbols); // format input to 2 decimal places (e.g. 20,1344 to 20,13)
		
		invoiceNet.setText("Netto: " + format.format(values.get_net()));
		invoiceUst.setText("Ust: " + format.format(values.get_ust()));
		invoiceTotal.setText("Total: " + format.format(values.get_gross()));
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
	
	public void setReferenceName(String customer) {
	    this.invoiceCustomer.setText(customer);
	}
	
	public void setReferenceId(int id) {
        this.invoiceCustomerId = id;
    }
	
	public void setWrongReference(boolean wrongReference) {
        this.wrongReference = wrongReference;
    }
}
