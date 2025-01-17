package com.Employee_Sacs.app.model.obj;

import lombok.Data;

@Data
public class ContributionObj {
	private String firstName;
	private String lastName;
	private int contribution_id;
	private int employee_id;
	private String contribution_name;
	private String contribute_value;
	private String minimum;
	private String maximum;
}
