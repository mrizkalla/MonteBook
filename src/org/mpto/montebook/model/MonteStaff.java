package org.mpto.montebook.model;


import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "MonteStaff")
public class MonteStaff extends Model {
	@Column(name = "objectId")
	public String objectId;
	@Column(name = "email")
	public String email;
	@Column(name = "extension")
	public String extension;
	@Column(name = "firstName")
	public String firstName;
	@Column(name = "lastName")
	public String lastName;
	@Column(name = "salutation")
	public String salutation;
	@Column(name = "title")
	public String title;

	
	public static MonteStaff getStaff() {
		return new Select()
			.from(MonteStaff.class)
			.limit(1)
			.executeSingle();
	}
	
	public static List<MonteStaff> getUniqueTitles() {
		return new Select("title")
			.distinct()
			.from(MonteStaff.class)
			.orderBy("title")
			.execute();
	}
	


}
