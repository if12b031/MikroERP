package main;

import contacts.Company;
import contacts.Person;
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
			return !Utils.isNullOrEmpty(getCustomerName())
					&& Utils.isNullOrEmpty(getCustomerSurname())
					&& Utils.isNullOrEmpty(getCustomerLastname());
		}
	};

	private BooleanBinding disableEditCompany = new BooleanBinding() {
		@Override
		protected boolean computeValue() {
			return Utils.isNullOrEmpty(getCustomerName())
					&& (!Utils.isNullOrEmpty(getCustomerSurname()) || !Utils
							.isNullOrEmpty(getCustomerLastname()));
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
		customerSurname.addListener(canEditListener);
		customerLastname.addListener(canEditListener);
		customerWorksAt.addListener(canEditListener);
		customerBirthday.addListener(canEditListener);
	}
	
	/* Set Customer */
	public void setCompany(Company company) {
		this.customerName.set(company.get_name());
		this.customerUID.set(company.get_uid());
	}
	
	public void setPerson(Person person) {
		this.customerTitle.set(person.get_title());
		this.customerSuffix.set(person.get_suffix());
		this.customerSurname.set(person.get_surname());
		this.customerLastname.set(person.get_lastname());
		this.customerBirthday.set(person.get_dateOfBirth());
		this.customerWorksAt.set(person.get_employedAt());
	}
	
	/* Update Customer */
	public void updateCompany(Company company) {
		company.set_name(this.customerName.get());
		company.set_uid(this.customerUID.get());
	}

	public void updatePerson(Person person) {
		person.set_title(this.customerTitle.get());
		person.set_suffix(this.customerSuffix.get());
		person.set_surname(this.customerSurname.get());
		person.set_lastname(this.customerLastname.get());
		person.set_dateOfBirth(this.customerBirthday.get());
		person.set_employedAt(this.customerWorksAt.get());
	}	

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
