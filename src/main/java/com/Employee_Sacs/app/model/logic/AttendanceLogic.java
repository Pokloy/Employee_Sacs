package com.Employee_Sacs.app.model.logic;

import java.util.List;
import java.util.Optional;

import com.Employee_Sacs.app.model.dao.entity.AttendanceEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedAttendanceUserInfo;

public interface AttendanceLogic {
	public List<AttendanceEntity> getAllAttendanceEntityByEmployeeId(String employeeId);
	
	public void saveClockInAttendance(AttendanceEntity attendanceEntity);
	
	public List<JoinedAttendanceUserInfo> getAllAttendance();
	
	public Optional<AttendanceEntity> findAttendanceById(int attendanceId);
	
	public void updateBreakInByDateNow(String breakIn, String employeeId, String date);

	public void updateBreakOutIdByDateNow(String breakOut, String employeeId, String date);

	public void updateClockOutIdByDateNow(String clockOut, String employeeId, String date);
	
	public AttendanceEntity getSpecificAttendance(int attendanceId);
	
	public void deleteSpecificAttendanceById(int attendanceId);
	
	public void updateStatusById(
			int attendanceID,
			String clockin, 
			String breakin, 
			String breakout, 
			String clockout, 
			String status);
	
	public void updateAttendanceHour(String attendancehours, String breakhours, int employeeId, String date, double latehours, double overtime);
	
	public AttendanceEntity getAttendanceByDateandEmployeeId(String date, int employeeId);
	
	public AttendanceEntity getSpecificAttendanceByClockoutIsZero();
}
