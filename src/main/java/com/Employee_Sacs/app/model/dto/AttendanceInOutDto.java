package com.Employee_Sacs.app.model.dto;

import java.util.List;

import com.Employee_Sacs.app.model.obj.AttendanceObj;

import lombok.Data;

@Data
public class AttendanceInOutDto {
	private List<AttendanceObj> attendanceObj;
	private String firstName;
	private String lastName;
	private int attendance_id;
	private int employee_id;
	private String clockin;
	private String breakin;
	private String breakout;
	private String clockout;
	private String attendancehours;
	private String status;
	private String date;
	private String breakHours;
	private double latehours;
	private double overtime;
}
