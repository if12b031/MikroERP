package models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InvoiceElementModel {

	/* Properties related to Rechnung */
	private StringProperty name = new SimpleStringProperty();
	private DoubleProperty price = new SimpleDoubleProperty();
	private IntegerProperty amount = new SimpleIntegerProperty();
	private DoubleProperty net = new SimpleDoubleProperty();
	private DoubleProperty ust= new SimpleDoubleProperty();
	private DoubleProperty total = new SimpleDoubleProperty();
	
	public InvoiceElementModel(String name, double price, int amount){
		this.name.set(name);
		this.price.set(price);
		this.amount.set(amount);
		this.net.set(price*amount);
		this.ust.set(this.net.get() / 100 * 20); //20% ust
		this.total.set(this.net.get() + this.ust.get());
	}
	
	/* Getters for Properties */
	public void setTotal(SimpleDoubleProperty total) {
		this.total = total;
	}
	
	public final StringProperty nameProperty() {
		return name;
	}

	public final IntegerProperty amountProperty() {
		return amount;
	}
	
	public final DoubleProperty priceProperty() {
		return price;
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
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public double getPrice() {
		return price.get();
	}
	
	public void setPrice(double price) {
		this.price.set(price);
	}
	
	public int getAmount() {
		return amount.get();
	}
	
	public void setAmount(int amount) {
		this.amount.set(amount);
	}
	
	public double getNet() {
		return net.get();
	}
	
	public void setNet(double net) {
		this.net.set(net);
	}
	
	public double getUst() {
		return ust.get();
	}
	
	public void setUst(double ust) {
		this.ust.set(ust);
	}
	
	public double getTotal() {
		return total.get();
	}
	
	public void setTotal(double total) {
		this.total.set(total);
	}
}
