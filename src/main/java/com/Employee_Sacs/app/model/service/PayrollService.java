package com.Employee_Sacs.app.model.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import com.Employee_Sacs.app.model.dto.AttendanceDailyPayInOutDto;
import com.Employee_Sacs.app.model.dto.DailyInOutDto;
import com.Employee_Sacs.app.model.dto.PayrollInOutDto;
import com.Employee_Sacs.app.model.dto.PayrollSettingsInOutDto;

public interface PayrollService {
	public void sampleConvert();
	
	public double convertMilitaryTimeToDecimal(String militaryTime);
	
	public double formulaDailyWage(double salary, double monthDays, double nonWorkingDays);
	
	public double formulaHourlyRate(double totalHoursofWork, double dailyWage);
	
	public double formulaMinuteRate(double hourlyRate);
	
	public double formulaForlateDeductionPerMinute(double latePerMinute, double totalLatePerMinute);
	
	public double formulaForDeductionPerDay(double dailyWage, double totalLateForDay);

//	public double formulaforTotalPayPerDay(double dailyWage, double deductionPerDay);

	public List<LocalDate> getSpecificDaysInCurrentMonth(DayOfWeek sunday);
	
	public PayrollSettingsInOutDto getPayrollSettingsByDateAndEmpId(String dateMonth);
	
	public double countDaysInCurrentMonth();
	
	public void insertingPayrollSettings(PayrollSettingsInOutDto payrollSettingsInOutDto);
	
	public void insertDailypay(DailyInOutDto dailyIO);
	
	public double formulaForRegularOverTime(double hourlyRate,double overtimeHour);
	
	public PayrollSettingsInOutDto getAllPayrollSettings(int employeeId);
	
	public DailyInOutDto getDailyPayWithName(int employeeId);
	
	public PayrollSettingsInOutDto getPayrollSettingsByDateAndEmpIdByParameters(String dateMonth, int employeeId);
	
	public void deletePayrollSettings(int payrollSettingsId);
	
	 public PayrollInOutDto getAllPayrollByEmployeeId(int employeeId);
	 
	 public AttendanceDailyPayInOutDto findListAttendanceDailyPay(int employeeId, String fromDate, String toDate);
	 
	 public PayrollInOutDto getpayrollById(int payrollID);
	 
	 public void insertPayroll(PayrollInOutDto payrollIO);
}
