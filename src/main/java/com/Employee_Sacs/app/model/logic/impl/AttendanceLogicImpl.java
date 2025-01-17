package com.Employee_Sacs.app.model.logic.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.AttendanceDao;
import com.Employee_Sacs.app.model.dao.entity.AttendanceEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedAttendanceUserInfo;
import com.Employee_Sacs.app.model.logic.AttendanceLogic;

@Service
public class AttendanceLogicImpl implements  AttendanceLogic {
	@Autowired
	private AttendanceDao attendanceDao;
	
	@Override
	public List<AttendanceEntity> getAllAttendanceEntityByEmployeeId(String employeeId){
		return attendanceDao.getAllAttendanceEntity(employeeId);
	}
	
	@Override
	public void saveClockInAttendance(AttendanceEntity attendanceEntity) {
		attendanceDao.saveAndFlush(attendanceEntity);
	}
	@Override
	public List<JoinedAttendanceUserInfo> getAllAttendance(){
		return attendanceDao.getAllAttendanceWithNames();
	}
	@Override
	public Optional<AttendanceEntity> findAttendanceById(int attendanceId) {
		Optional<AttendanceEntity> result = attendanceDao.findById(attendanceId);
		return result;
	}
	
	@Override
	public void updateBreakInByDateNow(String breakIn, String employeeId, String date) {
		attendanceDao.updateBreakInIdByDateNow(breakIn, employeeId, date);
	}
	
	@Override
	public void updateBreakOutIdByDateNow(String breakOut, String employeeId, String date) {
		attendanceDao.updateBreakOutIdByDateNow(breakOut, employeeId, date);
	}
	
	@Override
	public void updateClockOutIdByDateNow(String clockOut, String employeeId, String date) {
		attendanceDao.updateClockOutIdByDateNow(clockOut, employeeId, date);
	}
	
	@Override
	public AttendanceEntity getSpecificAttendance(int attendanceId) {
		return attendanceDao.getAttendanceById(attendanceId);
	}
	
	@Override
	public void deleteSpecificAttendanceById(int attendanceId) {
		attendanceDao.deleteById(attendanceId);
	}
	
	@Override
	public void updateStatusById(
			int attendanceID,
			String clockin, 
			String breakin, 
			String breakout, 
			String clockout, 
			String status) {
		attendanceDao.updateStatusByAttenID(attendanceID, 
				clockin, 
				breakin, 
				breakout, 
				clockout,  
				status);
	}
	
	@Override
	public void updateAttendanceHour(String attendancehours, String breakhours, int employeeId, String date, double latehours, double overtime) {
		attendanceDao.updateAttendanceHour(attendancehours, breakhours ,employeeId, date, latehours, overtime );
	}
	
	@Override
	public AttendanceEntity getAttendanceByDateandEmployeeId(String date, int employeeId) {
		return attendanceDao.getAttendanceByDateandEmployeeId(date, employeeId);
	}
	
	@Override
	public AttendanceEntity getSpecificAttendanceByClockoutIsZero() {
		return attendanceDao.getSpecificAttendanceByClockoutIsZero("00:00");
	}
}
