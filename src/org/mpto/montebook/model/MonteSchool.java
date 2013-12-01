package org.mpto.montebook.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "MonteSchool")
public class MonteSchool extends Model {
	@Column(name = "name")
	public String name;
	@Column(name = "street")
	public String street;
	@Column(name = "city")
	public String city;
	@Column(name = "state")
	public String state;
	@Column(name = "zip")
	public String zip;
	@Column(name = "phone")
	public String phone;
	@Column(name = "principalEmail")
	public String principalEmail;
	@Column(name = "principalSalutation")
	public String principalSalutation;
	@Column(name = "principalFirstName")
	public String principalFirstName;
	@Column(name = "principalLastName")
	public String principalLastName;
	
	public static MonteSchool getSchool() {
		return new Select()
			.from(MonteSchool.class)
			.limit(1)
			.executeSingle();
	}
}