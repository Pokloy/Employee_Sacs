package com.Employee_Sacs.app.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.Employee_Sacs.app.model.obj.AttendanceDailyPayObj;

import lombok.Data;

@Data
public class AttendanceDailyPayInOutDto {
	
	List<AttendanceDailyPayObj> attendanceDailyPayList = new ArrayList<>();
	
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
