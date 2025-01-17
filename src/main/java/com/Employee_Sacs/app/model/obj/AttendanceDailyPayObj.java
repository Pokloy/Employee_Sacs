package com.Employee_Sacs.app.model.obj;

import lombok.Data;

@Data
public class AttendanceDailyPayObj {
	private int attendance_id;
	
	private int employee_id;
	
	private String attendancehours; 
	
	private String regulardaily;

	private double latehours;
	
	private String latedaily;
	
	private double overtime; 
	
	private String overtimedaily;
	
	private String date;
}
