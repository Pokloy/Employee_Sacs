package com.Employee_Sacs.app.controller.dto;

import java.util.List;

import com.Employee_Sacs.app.model.obj.AttendanceDailyPayObj;
import com.Employee_Sacs.app.model.obj.ContributionObj;
import com.Employee_Sacs.app.model.obj.PayrollObj;
import com.Employee_Sacs.app.model.obj.PayrollSettingsObj;
import com.Employee_Sacs.app.model.obj.UserInfoObj;

import lombok.Data;

@Data
public class PayrollWebDto {
	List<UserInfoObj> userInfoObjList;
	List<PayrollSettingsObj> payrollObjList;	
	List<PayrollObj> payrollpayrollObj;
	List<AttendanceDailyPayObj> attendanceDailyPayObj;
	private List<ContributionObj> contributionObj;
	private int payroll_id;
	private int s_payroll_id;
	private int employee_id;
	private String firstName;
	private String lastName;
	private double month_days;
	private double non_workingdays;
	private double salary;
	private boolean active;
	private String date;
	private String date_cover_start;
	private String date_cover_end;
	private double totalRegularPay = 0.0;
	private double totalLateDeduction = 0.0;
	private double totalOvertimePay = 0.0;
	private double holiday = 0.0;
	private double bunos = 0.0;
	private double allowance = 0.0;
	private double adjustments = 0.0;
	private double absences = 0.0;
	private double undertime = 0.0;
	private double gross_pay = 0.0;
	private double net_pay = 0.0;
	private double total_contribution = 0.0;
	private double numbers_day = 0.0;
	private double total_deduction;
}
