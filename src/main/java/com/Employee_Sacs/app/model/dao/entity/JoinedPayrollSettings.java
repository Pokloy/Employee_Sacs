package com.Employee_Sacs.app.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinedPayrollSettings {
	public JoinedPayrollSettings (Object[]objects) {
		this(
				(Integer) objects[0],
				(Integer) objects[1],
				(String) objects[2],
				(String) objects[3],
				(double) objects[4],
				(double) objects[5],
				(double) objects[6],
				(boolean) objects[7],
				(String) objects[8]
			);
	}
	
	private int s_payroll_id;
	private int employee_id;
	private String firstName;
	private String lastName;
	private double month_days;
	private double non_workingdays;
	private double salary;
	private boolean active;
	private String date;
}
