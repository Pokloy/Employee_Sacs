package com.Employee_Sacs.app.model.obj;

import lombok.Data;

@Data
public class AttendanceObj {
	private String firstName;
	private String lastName;
	private int attendance_id;
	private int employee_id;
	private String clockin;
	private String breakin;
	private String breaskout;
	private String clockout;
	private String attendancehours;
	private String status;
	private String date;
	private String breakHours;
	private double latehours;
	private double overtime;
}
