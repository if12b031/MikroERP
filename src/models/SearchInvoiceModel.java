package models;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchInvoiceModel {

	/* Properties related to Rechnung */
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private StringProperty creationDate = new SimpleStringProperty();
	private StringProperty outgoing = new SimpleStringProperty();
	private StringProperty net = new SimpleStringProperty();
	private StringProperty ust= new SimpleStringProperty();
	private StringProperty total = new SimpleStringProperty();
	
	public SearchInvoiceModel(int id, String name, String creationDate, boolean outgoing, double ust, double net, double total){
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setDecimalSeparator('.');
		DecimalFormat format = new DecimalFormat("#0.00", symbols); // format input to 2 decimal places (e.g. 20,1344 to 20,13)
		
		this.id.set(id);
		this.name.set(name);
		this.creationDate.set(creationDate);
		if (outgoing == true){
			this.outgoing.set("Ausgehend");
		} else{
			this.outgoing.set("Eingehend");
		} 
		this.ust.set(format.format(ust));
		this.net.set(format.format(net));
		this.total.set(format.format(total));
	}
	
	/* Getters for Properties */
	public final IntegerProperty idProperty() {
		return id;
	}
	
	public final StringProperty nameProperty() {
		return name;
	}
	
	public final StringProperty creationDateProperty() {
		return creationDate;
	}
	
	public final StringProperty outgoingProperty() {
		return outgoing;
	}

	public final StringProperty netProperty() {
		return net;
	}
	
	public final StringProperty ustProperty() {
		return ust;
	}

	public final StringProperty totalProperty() {
		return total;
	}
	
	
	/*	Getters and Setters	*/
	public int getId() {
		return id.get();
	}
	
	public void setId(int id) {
		this.id.set(id);
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getCreationDate() {
		return creationDate.get();
	}
	
	public void setCreationDate(String creationDate) {
		this.creationDate.set(creationDate);
	}
		
	public String getUst() {
		return ust.get();
	}
	
	public void setUst(String ust) {
		this.ust.set(ust);
	}
	
	public String getNet() {
		return net.get();
	}
	
	public void setNet(String net) {
		this.net.set(net);
	}
	
	public String getTotal() {
		return total.get();
	}
	
	public void setTotal(String total) {
		this.total.set(total);
	}
}
