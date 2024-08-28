package com.Employee_Sacs.app.model.dto;

import java.util.List;

import com.Employee_Sacs.app.model.obj.PayrollSettingsObj;

import lombok.Data;

@Data
public class PayrollSettingsInOutDto {
	public List<PayrollSettingsObj> payrollObjList;	
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
