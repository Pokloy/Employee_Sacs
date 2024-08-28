package com.Employee_Sacs.app.model.service;

import com.Employee_Sacs.app.model.dto.AttendanceInOutDto;

public interface AttendanceService {
	public AttendanceInOutDto displayCurrentUserAttendance();
	
	public void addClockIn();
	
	public AttendanceInOutDto displayAllAttendance();
	
	public void updateBreakIn(String breakIn, String employeeId, String date);

	public void updateBreakOut(String breakIn, String employeeId, String date);

	public void updateClockOut(String breakIn, String employeeId, String date);
	
	public AttendanceInOutDto getSpecificAttendanceById(int attendanceId);
	
	public void deleteAttendanceById(int attendanceId);
	
	public void updateAttendanceById(AttendanceInOutDto attendanceDto);
	
	public double updateLateDuration(String attendanceHours);
	
	public double updateOverTimeDuration(String attendanceHours);
	
	public double calculateDailyPay(String dateMonth, double totalLatePerMinute);
	
	public double convertToCustomScale(double input);
	
	public double calculateTotalLateDeduction(String dateMonth, double totalLatePerMinute);
	
	public double calculateHourlyRate(String dateMonth, double totalLatePerMinute);
}
