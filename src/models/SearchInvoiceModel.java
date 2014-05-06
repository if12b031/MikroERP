package models;

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
	private DoubleProperty net = new SimpleDoubleProperty();
	private DoubleProperty ust= new SimpleDoubleProperty();
	private DoubleProperty total = new SimpleDoubleProperty();
	
	public SearchInvoiceModel(int id, String name, String lastname, String creationDate, boolean outgoing, double ust, double net, double total){
		this.id.set(id);
		if(lastname != null) {
			this.name.set(name + lastname);
		} else{
			this.name.set(name);
		}
		this.creationDate.set(creationDate);
		if (outgoing == true){
			this.outgoing.set("Ausgehend");
		} else{
			this.outgoing.set("Eingehend");
		}
		this.ust.set(ust);
		this.net.set(net);
		this.total.set(total);
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

	public final DoubleProperty netProperty() {
		return net;
	}
	
	public final DoubleProperty ustProperty() {
		return ust;
	}

	public final DoubleProperty totalProperty() {
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
		
	public double getUst() {
		return ust.get();
	}
	
	public void setUst(double ust) {
		this.ust.set(ust);
	}
	
	public double getNet() {
		return net.get();
	}
	
	public void setNet(double net) {
		this.net.set(net);
	}
	
	public double getTotal() {
		return total.get();
	}
	
	public void setTotal(double total) {
		this.total.set(total);
	}
}
