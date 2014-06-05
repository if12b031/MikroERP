package models;

import utils.Utils;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ReferencePresentationModel {

	/* Porperties related to Kunde */
	private StringProperty customerName = new SimpleStringProperty();
	private StringProperty customerLastname = new SimpleStringProperty();
	private StringProperty companyName= new SimpleStringProperty();
	
	private BooleanBinding disableEditPerson = new BooleanBinding() {
		@Override
		protected boolean computeValue() {
			return (!Utils.isNullOrEmpty(getCompanyName()) //Company-UID textfield is not empty
				/* All textfields related to person are empty */
				&& (Utils.isNullOrEmpty(getCustomerName())
						|| Utils.isNullOrEmpty(getCustomerLastname())));
		}
	};

	private BooleanBinding disableEditCompany = new BooleanBinding() {
		@Override
		protected boolean computeValue() {
			return /* All textfields related to person are not empty */
				!Utils.isNullOrEmpty(getCustomerName())
				|| !Utils.isNullOrEmpty(getCustomerLastname())
				&& Utils.isNullOrEmpty(getCompanyName()); //Company-UID textfield is empty
		}
	};

	public ReferencePresentationModel() {
		ChangeListener<String> canEditListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				disableEditPerson.invalidate();
				disableEditCompany.invalidate();
			}
		};
		customerName.addListener(canEditListener);
		customerLastname.addListener(canEditListener);
		companyName.addListener(canEditListener);
	}
	
	/* Getters for StringProperties */
	public  StringProperty customerNameProperty() {
		return customerName;
	}
	
	public StringProperty customerLastnameProperty() {
		return customerLastname;
	}

	public StringProperty companyNameProperty() {
		return companyName;
	}
		
	public BooleanBinding disableEditPersonBinding() {
		return disableEditPerson;
	}

	public BooleanBinding disableEditCompanyBinding() {
		return disableEditCompany;
	}
	
	/*	Getters and Setters	*/
	public String getCustomerName() {
		return customerName.get();
	}

	public void setCustomerName(String customerName) {
		this.customerName.set(customerName);
	}
	
	public String getCustomerLastname() {
		return customerLastname.get();
	}
	
	public void setCustomerLastname(String customerLastname) {
		this.customerLastname.set(customerLastname);
	}
	
	public String getCompanyName() {
		return companyName.get();
	}

	public void setCompanyName(String companyName) {
		this.companyName.set(companyName);
	}	
}
