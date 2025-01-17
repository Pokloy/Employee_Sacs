package com.Employee_Sacs.app.model.obj;

import lombok.Data;

@Data
public class PayrollObj {
	private int payroll_id;
	
	private int employee_id;
	
	private int s_payroll_id;
	
	private double bonus;
	
	private double holiday;
	
	private double allowance;
	
	private double adjustments;
	
	private double absences;
	
	private double undertime;
	
	private double gross_pay;
	
	private double net_pay;
	
	private double numbers_day;
	
	private String date_cover_start;
	
	private String date_cover_end;
	
	private String date_produced;
}
