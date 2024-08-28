package com.Employee_Sacs.app.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinedAttendanceDailyPay {
	public JoinedAttendanceDailyPay(Object[] objects) {
		this(
				(Integer)objects[0],
				(Integer)objects[1],
				(String)objects[2],
				(String)objects[3],
				(double)objects[4],
				(String)objects[5],
				(double)objects[6],
				(String)objects[7],
				(String)objects[8]
			);
	}	
	
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
