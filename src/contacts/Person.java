package contacts;

import java.sql.Date;

public class Person extends Customer {

	private String _name;
	private String _lastName;
	private String _title;
	private String _suffix;
	private Date _dateOfBirth;
	private String _employedAt;
	
	public String get_name() {
		return _name;
	}
	
	public void set_name(String _name) {
		this._name = _name;
	}
	
	public String get_lastName() {
		return _lastName;
	}
	
	public void set_lastName(String _lastName) {
		this._lastName = _lastName;
	}
	
	public String get_title() {
		return _title;
	}
	
	public void set_title(String _title) {
		this._title = _title;
	}
	
	public String get_suffix() {
		return _suffix;
	}
	
	public void set_suffix(String _suffix) {
		this._suffix = _suffix;
	}
	
	public Date get_dateOfBirth() {
		return _dateOfBirth;
	}
	
	public void set_dateOfBirth(Date _dateOfBirth) {
		this._dateOfBirth = _dateOfBirth;
	}
	
	public String get_employedAt() {
		return _employedAt;
	}
	
	public void set_employedAt(String _employedAt) {
		this._employedAt = _employedAt;
	}
}
