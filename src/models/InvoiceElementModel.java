package models;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InvoiceElementModel {

	/* Properties related to Rechnung */
	private StringProperty name = new SimpleStringProperty();
	private StringProperty price = new SimpleStringProperty();
	private IntegerProperty amount = new SimpleIntegerProperty();
	private StringProperty net = new SimpleStringProperty();
	private StringProperty ust= new SimpleStringProperty();
	private StringProperty total = new SimpleStringProperty();
	
	public InvoiceElementModel(String name, double price, int amount){
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setDecimalSeparator('.');
		DecimalFormat format = new DecimalFormat("#0.00", symbols); // format input to 2 decimal places (e.g. 20,1344 to 20,13)
		
		this.name.set(name);
		this.amount.set(amount);
		this.price.set(format.format(price));
		this.net.set(format.format(price*amount));
		double ust = (price*amount) / 100 * 20; // 20% ust
		this.ust.set(format.format(ust));
		this.total.set(format.format(Double.parseDouble(this.net.get()) + Double.parseDouble(this.ust.get())));
	}
	
	/* Getters for Properties */
	public void setTotal(SimpleStringProperty total) {
		this.total = total;
	}
	
	public final StringProperty nameProperty() {
		return name;
	}

	public final IntegerProperty amountProperty() {
		return amount;
	}
	
	public final StringProperty priceProperty() {
		return price;
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
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getPrice() {
		return price.get();
	}
	
	public void setPrice(String price) {
		this.price.set(price);
	}
	
	public int getAmount() {
		return amount.get();
	}
	
	public void setAmount(int amount) {
		this.amount.set(amount);
	}
	
	public String getNet() {
		return net.get();
	}
	
	public void setNet(String net) {
		this.net.set(net);
	}
	
	public String getUst() {
		return ust.get();
	}
	
	public void setUst(String ust) {
		this.ust.set(ust);
	}
	
	public String getTotal() {
		return total.get();
	}
	
	public void setTotal(String total) {
		this.total.set(total);
	}
}
