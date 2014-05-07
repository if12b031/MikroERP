package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchCustomerModel {

	/* Properties related to Kunde */
	private StringProperty uid = new SimpleStringProperty();
	private StringProperty title = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();
	private StringProperty lastname = new SimpleStringProperty();
	private StringProperty suffix = new SimpleStringProperty();
	private StringProperty birthday = new SimpleStringProperty();
	private StringProperty address = new SimpleStringProperty();
	private StringProperty plz = new SimpleStringProperty();
	private StringProperty city = new SimpleStringProperty();
	
	public SearchCustomerModel(String uid,String companyName, String title, String name, String lastname, 
			String suffix, String birthday, String address, String plz, String city){
		if(uid == null){
			uid = "";
		}
		if(companyName == null){
			companyName = "";
		}
		if(name == null){
			name = "";
		}
		if(lastname == null){
			lastname = "";
		}
		this.uid.set(uid);
		this.title.set(title);
		this.suffix.set(suffix);
		this.name.set(name + companyName);
		this.lastname.set(lastname + uid);
		this.birthday.set(birthday);
		this.address.set(address);
		this.plz.set(plz);
		this.city.set(city);
	}
	
	/* Getters for Properties */
	public StringProperty uidProperty() {
		return uid;
	}
	
	public StringProperty titleProperty() {
		return title;
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public StringProperty lastnameProperty() {
		return lastname;
	}
	
	public StringProperty suffixProperty() {
		return suffix;
	}
	
	public StringProperty birthdayProperty() {
		return birthday;
	}
	
	public StringProperty addressProperty() {
		return address;
	}
	
	public StringProperty plzProperty() {
		return plz;
	}
	
	public StringProperty cityProperty() {
		return city;
	}
	
	
	/*	Getters and Setters	*/
	public String getUID() {
		return uid.get();
	}

	public void setUID(String uid) {
		this.uid.set(uid);
	}
	
	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String Name) {
		this.name.set(Name);
	}

	public String getLastname() {
		return lastname.get();
	}

	public void setLastname(String lastname) {
		this.lastname.set(lastname);
	}
	
	public String getSuffix() {
		return suffix.get();
	}
	
	public void setSuffix(String suffix) {
		this.suffix.set(suffix);
	}

	public String getBirthday() {
		return birthday.get();
	}

	public void setBirthday(String birthday) {
		this.birthday.set(birthday);
	}

	public String getAddress() {
		return address.get();
	}

	public void setAddress(String address) {
		this.address.set(address);
	}

	public String getPlz() {
		return plz.get();
	}

	public void setPlz(String plz) {
		this.plz.set(plz);
	}

	public String getCity() {
		return city.get();
	}

	public void setCity(String city) {
		this.city.set(city);
	}
}
