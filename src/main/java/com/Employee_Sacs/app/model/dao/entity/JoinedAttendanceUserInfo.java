package com.Employee_Sacs.app.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinedAttendanceUserInfo {
	public JoinedAttendanceUserInfo(Object[] objects) {
		this(
				(String)objects[0],
				(String)objects[1],
				(Integer)objects[2],
				(Integer)objects[3],
				(String)objects[4],
				(String)objects[5],
				(String)objects[6],
				(String)objects[7],
				(String)objects[8],
				(String)objects[9],
				(String)objects[10],
				(String)objects[11],
				(double)objects[12],
				(double)objects[13]
				);
	}
	private String firstName;
	
	private String lastName;
	
	private int attendanceId;
	
	private int employeeId;
	
	private String clockIn;
	
	private String breakIn;
	
	private String breakOut;
	
	private String clockOut;
	
	private String attendancehours;
	
	private String status;
	
	private String date;
	
	private String breakHours;
	
	private double latehours;
	
	private double overtime;
}
