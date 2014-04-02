package main;

import utils.Utils;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MainModel {

	private StringProperty customerName = new SimpleStringProperty();
	private StringProperty customerUID = new SimpleStringProperty();
	private StringProperty customerTitle = new SimpleStringProperty();
	private StringProperty customerSuffix = new SimpleStringProperty();
	private StringProperty customerSurname = new SimpleStringProperty();
	private StringProperty customerLastname = new SimpleStringProperty();
	private StringProperty customerWorksAt = new SimpleStringProperty();
	private StringProperty customerBirthday = new SimpleStringProperty();
	private StringProperty customerAddress = new SimpleStringProperty();
	private StringProperty customerPLZ = new SimpleStringProperty();
	private StringProperty customerCity = new SimpleStringProperty();
	
	private BooleanBinding disableEditPerson = new BooleanBinding() {
		@Override
		protected boolean computeValue() {
			return (!Utils.isNullOrEmpty(getCustomerName()) //Company-name textfield is not empty
				|| !Utils.isNullOrEmpty(getCustomerUID())) //Company-UID textfield is not empty
				/* All textfields related to person are empty */
				&& Utils.isNullOrEmpty(getCustomerSurname())
				&& Utils.isNullOrEmpty(getCustomerLastname())
				&& Utils.isNullOrEmpty(getCustomerTitle())
				&& Utils.isNullOrEmpty(getCustomerSuffix())
				&& Utils.isNullOrEmpty(getCustomerBirthday())
				&& Utils.isNullOrEmpty(getCustomerWorksAt());
		}
	};

	private BooleanBinding disableEditCompany = new BooleanBinding() {
		@Override
		protected boolean computeValue() {
			return /* All textfields related to person are not empty */
				!Utils.isNullOrEmpty(getCustomerSurname())
				|| !Utils.isNullOrEmpty(getCustomerLastname())
				|| !Utils.isNullOrEmpty(getCustomerTitle())
				|| !Utils.isNullOrEmpty(getCustomerSuffix())
				|| !Utils.isNullOrEmpty(getCustomerBirthday())
				|| !Utils.isNullOrEmpty(getCustomerWorksAt())
				&& (Utils.isNullOrEmpty(getCustomerName()) //Company-name textfield is empty
				&& Utils.isNullOrEmpty(getCustomerUID())); //Company-UID textfield is empty
		}
	};

	public MainModel() {
		ChangeListener<String> canEditListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				disableEditPerson.invalidate();
				disableEditCompany.invalidate();
			}
		};
		customerName.addListener(canEditListener);
		customerUID.addListener(canEditListener);
		customerTitle.addListener(canEditListener);
		customerSuffix.addListener(canEditListener);
		customerSurname.addListener(canEditListener);
		customerLastname.addListener(canEditListener);
		customerWorksAt.addListener(canEditListener);
		customerBirthday.addListener(canEditListener);
	}
	
	/* Getters for StringProperties */
	public  StringProperty customerNameProperty() {
		return customerName;
	}

	public StringProperty customerUIDProperty() {
		return customerUID;
	}
	
	public StringProperty customerTitleProperty() {
		return customerTitle;
	}
	
	public StringProperty customerSuffixProperty() {
		return customerSuffix;
	}
	
	public StringProperty customerSurnameProperty() {
		return customerSurname;
	}
	
	public StringProperty customerLastnameProperty() {
		return customerLastname;
	}
	
	public StringProperty customerWorksAtProperty() {
		return customerWorksAt;
	}
	
	public StringProperty customerBirthdayProperty() {
		return customerBirthday;
	}
	
	public StringProperty customerAddressProperty() {
		return customerAddress;
	}
	
	public StringProperty customerPLZProperty() {
		return customerPLZ;
	}
	
	public StringProperty customerCityProperty() {
		return customerCity;
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

	public String getCustomerUID() {
		return customerUID.get();
	}

	public void setCustomerUID(String customerUID) {
		this.customerUID.set(customerUID);
	}
	public String getCustomerTitle() {
		return customerTitle.get();
	}

	public void setCustomerTitle(String customerTitle) {
		this.customerSurname.set(customerTitle);
	}
	public String getCustomerSuffix() {
		return customerSuffix.get();
	}

	public void setCustomerSuffix(String customerSuffix) {
		this.customerSurname.set(customerSuffix);
	}

	public String getCustomerSurname() {
		return customerSurname.get();
	}

	public void setCustomerSurname(String customerSurname) {
		this.customerSurname.set(customerSurname);
	}

	public String getCustomerLastname() {
		return customerLastname.get();
	}

	public void setCustomerLastname(String customerLastname) {
		this.customerLastname.set(customerLastname);
	}

	public String getCustomerWorksAt() {
		return customerWorksAt.get();
	}

	public void setCustomerWorksAt(String customerWorksAt) {
		this.customerWorksAt.set(customerWorksAt);
	}

	public String getCustomerBirthday() {
		return customerBirthday.get();
	}

	public void setCustomerBirthday(String customerBirthday) {
		this.customerBirthday.set(customerBirthday);
	}

	public String getCustomerAddress() {
		return customerAddress.get();
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress.set(customerAddress);
	}

	public String getCustomerPLZ() {
		return customerPLZ.get();
	}

	public void setCustomerPLZ(String customerPLZ) {
		this.customerPLZ.set(customerPLZ);
	}

	public String getCustomerCity() {
		return customerCity.get();
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity.set(customerCity);
	}	
}
