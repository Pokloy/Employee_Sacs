package com.Employee_Sacs.app.model.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.entity.AttendanceEntity;
import com.Employee_Sacs.app.model.dao.entity.DailyPayEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedAttendanceUserInfo;
import com.Employee_Sacs.app.model.dto.AttendanceInOutDto;
import com.Employee_Sacs.app.model.dto.DailyInOutDto;
import com.Employee_Sacs.app.model.dto.PayrollSettingsInOutDto;
import com.Employee_Sacs.app.model.logic.AttendanceLogic;
import com.Employee_Sacs.app.model.logic.PayrollLogic;
import com.Employee_Sacs.app.model.obj.AttendanceObj;
import com.Employee_Sacs.app.model.service.AttendanceService;
import com.Employee_Sacs.app.model.service.LoggedInUserService;
import com.Employee_Sacs.app.model.service.PayrollService;

@Service
public class AttendanceServiceImpl implements AttendanceService {
	@Autowired
	private AttendanceLogic attendanceLogic;
	
	@Autowired
	UserDetailsService userDetails;
	
	@Autowired
	LoggedInUserService loggedInUserService;
	
	@Autowired
	PayrollService payrollService;
	
	@Autowired
	PayrollLogic payrollLogic;
	
	@Override
	public AttendanceInOutDto getSpecificAttendanceById(int attendanceId) {
		AttendanceInOutDto attendanceDTO = new AttendanceInOutDto();
		AttendanceEntity specificAttendance = attendanceLogic.getSpecificAttendance(attendanceId);
		attendanceDTO.setAttendance_id(specificAttendance.getAttendance_id());
		attendanceDTO.setEmployee_id(specificAttendance.getEmployee_id());
		attendanceDTO.setClockin(specificAttendance.getClockin());
		attendanceDTO.setBreakin(specificAttendance.getBreakin());
		attendanceDTO.setBreakout(specificAttendance.getBreakout());
		attendanceDTO.setClockout(specificAttendance.getClockout());
		attendanceDTO.setAttendancehours(specificAttendance.getAttendancehours());
		attendanceDTO.setStatus(specificAttendance.getStatus());
		attendanceDTO.setDate(specificAttendance.getDate());	
		attendanceDTO.setBreakHours(specificAttendance.getBreakhours());
		attendanceDTO.setLatehours(specificAttendance.getLatehours());
		attendanceDTO.setOvertime(specificAttendance.getOvertime());
		return attendanceDTO;
	}
	
	@Override
	public AttendanceInOutDto displayAllAttendance() {
		AttendanceInOutDto attendanceDTO = new AttendanceInOutDto();
		List<JoinedAttendanceUserInfo> attendanceList = attendanceLogic.getAllAttendance();
		List<AttendanceObj> attendanceObjList = new ArrayList<>();
		if(attendanceList != null && !attendanceList.isEmpty()) {
			for(JoinedAttendanceUserInfo attendanceEntity : attendanceList) {
				AttendanceObj attendanceObj = new AttendanceObj();
				attendanceObj.setFirstName(attendanceEntity.getFirstName());
				attendanceObj.setLastName(attendanceEntity.getLastName());
				attendanceObj.setAttendance_id(attendanceEntity.getAttendanceId());
				attendanceObj.setEmployee_id(attendanceEntity.getEmployeeId());
				attendanceObj.setClockin(attendanceEntity.getClockIn());
				attendanceObj.setBreakin(attendanceEntity.getBreakIn());
				attendanceObj.setBreaskout(attendanceEntity.getBreakOut());
				attendanceObj.setClockout(attendanceEntity.getClockOut());
				attendanceObj.setAttendancehours(attendanceEntity.getAttendancehours());
				attendanceObj.setStatus(attendanceEntity.getStatus());
				attendanceObj.setDate(attendanceEntity.getDate());
				attendanceObj.setBreakHours(attendanceEntity.getBreakHours());
				attendanceObj.setLatehours(attendanceEntity.getLatehours());
				attendanceObj.setOvertime(attendanceEntity.getOvertime());
				attendanceObjList.add(attendanceObj);
			}
			attendanceDTO.setAttendanceObj(attendanceObjList);
		}
		
		return attendanceDTO;
	}
	
	@Override
	public AttendanceInOutDto displayCurrentUserAttendance() {
			AttendanceInOutDto attendanceDTO = new AttendanceInOutDto();
			String loggedInuser = String.valueOf(loggedInUserService.getEmployeeId());
			List<AttendanceEntity> attendanceList = attendanceLogic.getAllAttendanceEntityByEmployeeId(loggedInuser);
			
			List<AttendanceObj> attendanceObjList = new ArrayList<>();
			
			if(attendanceList != null && !attendanceList.isEmpty()) {
				for(AttendanceEntity attendanceEntity : attendanceList) {
					AttendanceObj attendanceObj = new AttendanceObj();
					attendanceObj.setAttendance_id(attendanceEntity.getAttendance_id());
					attendanceObj.setEmployee_id(attendanceEntity.getEmployee_id());
					attendanceObj.setClockin(attendanceEntity.getClockin());
					attendanceObj.setBreakin(attendanceEntity.getBreakin());
					attendanceObj.setBreaskout(attendanceEntity.getBreakout());
					attendanceObj.setClockout(attendanceEntity.getClockout());
					attendanceObj.setAttendancehours(attendanceEntity.getAttendancehours());
					attendanceObj.setStatus(attendanceEntity.getStatus());
					attendanceObj.setDate(attendanceEntity.getDate());
					attendanceObj.setBreakHours(attendanceEntity.getBreakhours());
					attendanceObj.setLatehours(attendanceEntity.getLatehours());
					attendanceObj.setOvertime(attendanceEntity.getOvertime());
					attendanceObjList.add(attendanceObj);
				}
				attendanceDTO.setAttendanceObj(attendanceObjList);
			}
			return attendanceDTO;
		}
	
	@Override
	public void addClockIn() {
		
	    // Get current date and time
	    LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	    
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		AttendanceEntity attendanceEntity = new AttendanceEntity();
		attendanceEntity.setEmployee_id(loggedInUserService.getEmployeeId());
		attendanceEntity.setClockin(now.format(timeFormatter));
    	attendanceEntity.setBreakin("00:00");
    	attendanceEntity.setBreakout("00:00");
    	attendanceEntity.setClockout("00:00");
    	attendanceEntity.setAttendancehours("00:00");
    	attendanceEntity.setBreakhours("00:00");
    	attendanceEntity.setStatus("in progress");
    	attendanceEntity.setDate(now.format(dateFormatter));
    	attendanceEntity.setLatehours(0);
		attendanceLogic.saveClockInAttendance(attendanceEntity);
	}
	
	@Override
	public void updateBreakIn(String breakIn, String employeeId, String date) {
		attendanceLogic.updateBreakInByDateNow(breakIn,employeeId,date);
	}
	
	@Override
	public void updateBreakOut(String breakIn, String employeeId, String date) {
		attendanceLogic.updateBreakOutIdByDateNow(breakIn, employeeId, date);
	}
	
	@Override
	public void updateClockOut(String breakIn, String employeeId, String date) {

		attendanceLogic.updateClockOutIdByDateNow(breakIn, employeeId, date);
		AttendanceEntity getSpeicificAtendanceLogic = attendanceLogic.getAttendanceByDateandEmployeeId(date, loggedInUserService.getEmployeeId());
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime breakInTime = LocalTime.parse(getSpeicificAtendanceLogic.getBreakin(), timeFormatter);
		LocalTime breakOutTime = LocalTime.parse(getSpeicificAtendanceLogic.getBreakout(), timeFormatter);
		LocalTime clockIn = LocalTime.parse(getSpeicificAtendanceLogic.getClockin(), timeFormatter);
		LocalTime clockOut = LocalTime.parse(getSpeicificAtendanceLogic.getClockout(), timeFormatter);
    	
    	Duration durationClock = Duration.between(clockIn, clockOut);
    	Duration durationBreak = Duration.between(breakInTime, breakOutTime);
    	Duration actualWorkDuration = durationClock.minus(durationBreak);
    	
        // calculation for actual work hours
        long totalMinutesClock = actualWorkDuration.toMinutes();
        long hoursClock = totalMinutesClock / 60;
        long minutesClock = totalMinutesClock % 60;
        
        // hourly rate calculation
        double regularHourlyRate = calculateHourlyRate(date, hoursClock);
        
        String totalBreakTime = String.format("%02d:%02d", 	durationBreak.toHours(), durationBreak.toMinutesPart());
        // Total work time
        // String totalWorkTime = String.format("%02d:%02d", durationClock.toHours(), durationClock.toMinutesPart());
        String actualWorkTime = String.format("%02d:%02d", hoursClock,minutesClock);

        // hourly rate of late
        double lateHours = updateLateDuration(actualWorkTime);
        // hourly rate of overtime
        double overtimeHours = updateOverTimeDuration(actualWorkTime);
        
        // late convertion from hours to minutes
        double lateConvertHoursToMinutes = convertToCustomScale(lateHours);

        attendanceLogic.updateAttendanceHour(actualWorkTime, totalBreakTime, loggedInUserService.getEmployeeId(), date, lateHours, overtimeHours);

        double totalDailyPay =  calculateDailyPay(date, lateConvertHoursToMinutes);
        String dailyPay = String.valueOf(totalDailyPay);
        double totalLateDeduction = calculateTotalLateDeduction(date, lateConvertHoursToMinutes);
        String lateDeduction = String.valueOf(totalLateDeduction);
        double totalRegularOvertime = payrollService.formulaForRegularOverTime(regularHourlyRate, overtimeHours) ;
        String RegularOverTime = String.valueOf(totalRegularOvertime);
        
        DailyInOutDto dailyIO = new DailyInOutDto();
        AttendanceEntity attendanceEntityByDateAndEmployeeId = attendanceLogic.getAttendanceByDateandEmployeeId(date, loggedInUserService.getEmployeeId());
        dailyIO.setAttendance_id(attendanceEntityByDateAndEmployeeId.getAttendance_id());
        dailyIO.setLatedaily(lateDeduction);
        dailyIO.setOvertimedaily(RegularOverTime);
        dailyIO.setRegulardaily(dailyPay);
        payrollService.insertDailypay(dailyIO);
        
        
//      System.out.println("regular hourly rate: " + regularHourlyRate);
//      System.out.println("overtime hour: " + overtimeHours);
//      System.out.println("total regular rate: " + RegularOverTime);
//      System.out.println("Total Late Minute of the day: " + lateConvertHoursToMinutes);
//      System.out.println("Daily Pay: " + dailyPay);
//      System.out.println("Total Late Deduction: " + totalLateDeduction);
//      System.out.println("OverTime WorkHours: " + totalRegularOvertime);
        

	}
	
	@Override
	public double updateLateDuration(String attendanceHours) {
		double convertedAttendanceHours = payrollService.convertMilitaryTimeToDecimal(attendanceHours);
		
		double workHours = 8.0;
		
		if(convertedAttendanceHours < 8) {
			double lateDuration =  workHours - convertedAttendanceHours;
			return roundToTwoDecimalPlaces(lateDuration);
		} else {
			return 0.0;
		}
	}
	
	@Override
	public double updateOverTimeDuration(String attendanceHours) {
		double convertedAttendanceHours = payrollService.convertMilitaryTimeToDecimal(attendanceHours);
		
		double workHours = 8.0;
		
		if(convertedAttendanceHours > 8) {
			double overtTimeDuration =  convertedAttendanceHours -  workHours;
			return roundToTwoDecimalPlaces(overtTimeDuration);
		} else {
			return 0.0;
		}
		
	}
	
	public static double roundToTwoDecimalPlaces(double value) {
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	@Override
	public void deleteAttendanceById(int attendanceId) {
		attendanceLogic.deleteSpecificAttendanceById(attendanceId);
	}
	
	@Override
	public void updateAttendanceById(AttendanceInOutDto attendanceDto) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime breakInTime = LocalTime.parse(attendanceDto.getBreakin(), timeFormatter);
		LocalTime breakOutTime = LocalTime.parse(attendanceDto.getBreakout(), timeFormatter);
		LocalTime clockIn = LocalTime.parse(attendanceDto.getClockin(), timeFormatter);
		LocalTime clockOut = LocalTime.parse(attendanceDto.getClockout(), timeFormatter);
		
		Duration durationClock = Duration.between(clockIn, clockOut);
		Duration durationBreak = Duration.between(breakInTime, breakOutTime);
		Duration actualWorkDuration = durationClock.minus(durationBreak);
		
		
        long totalMinutesClock = actualWorkDuration.toMinutes();
        long hoursClock = totalMinutesClock / 60;
        long minutesClock = totalMinutesClock % 60;
              
        String totalBreakTime = String.format("%02d:%02d", 	durationBreak.toHours(), durationBreak.toMinutesPart());
        // Total work time
        // String totalWorkTime = String.format("%02d:%02d", durationClock.toHours(), durationClock.toMinutesPart());
        String actualWorkTime = String.format("%02d:%02d", hoursClock,minutesClock);

        // hourly rate of late
        double lateHours = updateLateDuration(actualWorkTime);
        // hourly rate of overtime
        double overtimeHours = updateOverTimeDuration(actualWorkTime);
        
        attendanceLogic.updateAttendanceHour(actualWorkTime, totalBreakTime, attendanceDto.getEmployee_id(), attendanceDto.getDate(), lateHours, overtimeHours);
        
		attendanceLogic.updateStatusById(attendanceDto.getAttendance_id(), 
				attendanceDto.getClockin(), 
				attendanceDto.getBreakin(), 
				attendanceDto.getBreakout(), 
				attendanceDto.getClockout(),
				attendanceDto.getStatus());
	}
	
	@Override
	public double calculateDailyPay(String dateMonth, double totalLatePerMinute) {
		PayrollSettingsInOutDto gettingPayrollSettings = payrollService.getPayrollSettingsByDateAndEmpId(dateMonth);
		double dailyWage = payrollService.formulaDailyWage(gettingPayrollSettings.getSalary(), gettingPayrollSettings.getMonth_days(), gettingPayrollSettings.getNon_workingdays());
		double hourlyRate = payrollService.formulaHourlyRate(8, dailyWage);
		double minuteRate = payrollService.formulaMinuteRate(hourlyRate);
		double lateDeductionPerMinute = payrollService.formulaForlateDeductionPerMinute(minuteRate, totalLatePerMinute);
		double lateDeductionPerDay = payrollService.formulaForDeductionPerDay(dailyWage, lateDeductionPerMinute);
		return lateDeductionPerDay;
	}
	
	@Override
	public double calculateTotalLateDeduction(String dateMonth, double totalLatePerMinute) {
		PayrollSettingsInOutDto gettingPayrollSettings = payrollService.getPayrollSettingsByDateAndEmpId(dateMonth);
		double dailyWage = payrollService.formulaDailyWage(gettingPayrollSettings.getSalary(), gettingPayrollSettings.getMonth_days(), gettingPayrollSettings.getNon_workingdays());
		double hourlyRate = payrollService.formulaHourlyRate(8, dailyWage);
		double minuteRate = payrollService.formulaMinuteRate(hourlyRate);
		double lateDeductionPerMinute = payrollService.formulaForlateDeductionPerMinute(minuteRate, totalLatePerMinute);
		return lateDeductionPerMinute;
	}
	
	@Override
	public double calculateHourlyRate(String dateMonth, double totalHour) {
		PayrollSettingsInOutDto gettingPayrollSettings = payrollService.getPayrollSettingsByDateAndEmpId(dateMonth);
		double dailyWage = payrollService.formulaDailyWage(gettingPayrollSettings.getSalary(), gettingPayrollSettings.getMonth_days(), gettingPayrollSettings.getNon_workingdays());
		double hourlyRate = payrollService.formulaHourlyRate(8, dailyWage);
		return hourlyRate;
	}
	
	@Override
	public double convertToCustomScale(double input) {
        // Define the conversion factor (where 1 unit is equivalent to 60)
        double conversionFactor = 60.0;

        // Convert the input using the conversion factor
        double result = input * conversionFactor;
        
        // Return the converted result
        return result;
	}
}
