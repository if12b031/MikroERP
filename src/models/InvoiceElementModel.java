package models;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InvoiceElementModel {

	/* Porperties related to Rechnung */
	private StringProperty name = new SimpleStringProperty();
	private FloatProperty price = new SimpleFloatProperty();
	private IntegerProperty amount = new SimpleIntegerProperty();
	private FloatProperty net = new SimpleFloatProperty();
	private FloatProperty ust= new SimpleFloatProperty();
	private FloatProperty total = new SimpleFloatProperty();
	
	public InvoiceElementModel(String name, float price, int amount){
		this.name.set(name);
		this.price.set(price);
		this.amount.set(amount);
		this.net.set(price*amount);
		this.ust.set(this.net.get() / 100 * 20); //20% ust
		this.total.set(this.net.get() + this.ust.get());
	}
	
	/* Getters for Properties */
	public void setTotal(SimpleFloatProperty total) {
		this.total = total;
	}
	
	public final StringProperty nameProperty() {
		return name;
	}

	public final IntegerProperty amountProperty() {
		return amount;
	}
	
	public final FloatProperty priceProperty() {
		return price;
	}

	public final FloatProperty netProperty() {
		return net;
	}
	
	public final FloatProperty ustProperty() {
		return ust;
	}

	public final FloatProperty totalProperty() {
		return total;
	}
	
	
	/*	Getters and Setters	*/
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public float getPrice() {
		return price.get();
	}
	
	public void setPrice(float price) {
		this.price.set(price);
	}
	
	public int getAmount() {
		return amount.get();
	}
	
	public void setAmount(int amount) {
		this.amount.set(amount);
	}
	
	public float getNet() {
		return net.get();
	}
	
	public void setNet(float net) {
		this.net.set(net);
	}
	
	public float getUst() {
		return ust.get();
	}
	
	public void setUst(float ust) {
		this.ust.set(ust);
	}
	
	public float getTotal() {
		return total.get();
	}
	
	public void setTotal(float total) {
		this.total.set(total);
	}
}
