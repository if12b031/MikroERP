package models;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class InvoicePresentationModel {
	
	/* Properties related to Rechnung */
	private StringProperty invoiceID = new SimpleStringProperty();
	private StringProperty invoiceDate = new SimpleStringProperty();
	private StringProperty invoiceCustomer = new SimpleStringProperty();
	private StringProperty invoiceShippingAddress = new SimpleStringProperty();
	private StringProperty invoiceAddress = new SimpleStringProperty();
	private StringProperty invoiceComment = new SimpleStringProperty();
	private StringProperty invoiceMessage = new SimpleStringProperty();
	private StringProperty invoiceElementAmount = new SimpleStringProperty();
	private StringProperty invoiceNet = new SimpleStringProperty();
	private StringProperty invoiceUst = new SimpleStringProperty();
	private StringProperty invoiceTotal = new SimpleStringProperty();
	private StringProperty messageLabel = new SimpleStringProperty();
	
	public void clearInvoice() {
		invoiceID.setValue("");
		invoiceDate.setValue("");
		invoiceCustomer.setValue("");
		invoiceShippingAddress.setValue("");
		invoiceAddress.setValue("");
		invoiceComment.setValue("");
		invoiceMessage.setValue("");
		invoiceElementAmount.setValue("");
		invoiceNet.setValue("");
		invoiceUst.setValue("");
		invoiceTotal.setValue("");
		messageLabel.setValue("");
	}
	
	/* Getters for Properties */
	public  StringProperty invoiceIDProperty() {
		return invoiceID;
	}

	public StringProperty invoiceDateProperty() {
		return invoiceDate;
	}
	
	public StringProperty invoiceCustomerProperty() {
		return invoiceCustomer;
	}
	
	public StringProperty invoiceShippingAddressProperty() {
		return invoiceShippingAddress;
	}
	
	public StringProperty invoiceAddressProperty() {
		return invoiceAddress;
	}
	
	public StringProperty invoiceCommentProperty() {
		return invoiceComment;
	}
	
	public StringProperty invoiceMessageProperty() {
		return invoiceMessage;
	}
	
	public StringProperty invoiceElementAmountProperty() {
		return invoiceElementAmount;
	}
	
	public StringProperty invoiceNetProperty() {
		return invoiceNet;
	}
	
	public StringProperty invoiceUstProperty() {
		return invoiceUst;
	}
	
	public StringProperty invoiceTotalProperty() {
		return invoiceTotal;
	}
	
	public StringProperty messageLabelProperty() {
		return messageLabel;
	}

	
	/*	Getters and Setters	*/
	public String getInvoiceID() {
		return invoiceID.get();
	}

	public void setInvoiceID(String invoiceId) {
		this.invoiceID.set(invoiceId);
	}

	public String getInvoiceDate() {
		return invoiceDate.get();
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate.set(invoiceDate);
	}
	
	public String getInvoiceCustomer() {
		return invoiceCustomer.get();
	}

	public void setInvoiceCustomer(String invoiceCustomer) {
		this.invoiceCustomer.set(invoiceCustomer);
	}
	
	public String getInvoiceShippingAddress() {
		return invoiceShippingAddress.get();
	}

	public void setInvoiceShippingAddress(String invoiceShippingAddress) {
		this.invoiceShippingAddress.set(invoiceShippingAddress);
	}
	
	public String getInvoiceAddress() {
		return invoiceAddress.get();
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress.set(invoiceAddress);
	}

	public String getInvoiceComment() {
		return invoiceComment.get();
	}

	public void setInvoiceComment(String invoiceComment) {
		this.invoiceComment.set(invoiceComment);
	}

	public String getInvoiceMessage() {
		return invoiceMessage.get();
	}

	public void setInvoiceMessage(String invoiceMessage) {
		this.invoiceMessage.set(invoiceMessage);
	}

	public String getInvoiceElementAmount() {
		return invoiceElementAmount.get();
	}

	public void setInvoiceElementAmount(String invoiceElementAmount) {
		this.invoiceElementAmount.set(invoiceElementAmount);
	}

	public void setInvoiceNet(String invoiceNet) {
		this.invoiceNet.set(invoiceNet);
	}
	
	public String getInvoiceNet() {
		return invoiceNet.get();
	}

	public void setInvoiceUst(String invoiceUst) {
		this.invoiceUst.set(invoiceUst);
	}
	
	public String getInvoiceUst() {
		return invoiceUst.get();
	}
	
	public void setInvoiceTotal(String invoiceTotal) {
		this.invoiceTotal.set(invoiceTotal);
	}
	
	public String getInvoiceTotal() {
		return invoiceTotal.get();
	}
	
	public String getMessageLabel() {
		return messageLabel.get();
	}

	public void setMessageLabel(String messageLabel) {
		this.messageLabel.set(messageLabel);
	}
}
