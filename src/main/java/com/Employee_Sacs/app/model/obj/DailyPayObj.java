package com.Employee_Sacs.app.model.obj;

import lombok.Data;

@Data
public class DailyPayObj {
	private String firstName;
	
	private String lastName;
	
	private int dailypay_id;
	
	private int attendance_id;
	
	private String regulardaily;
	
	private String overtimedaily;
	
	private String latedaily;
	
	private boolean status;
	
	private String date;
}
