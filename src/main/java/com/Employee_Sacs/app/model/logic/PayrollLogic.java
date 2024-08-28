package com.Employee_Sacs.app.model.logic;

import java.util.List;

import com.Employee_Sacs.app.model.dao.entity.JoinedAttendanceDailyPay;
import com.Employee_Sacs.app.model.dao.entity.JoinedPayrollSettings;
import com.Employee_Sacs.app.model.dao.entity.PayrollEntity;
import com.Employee_Sacs.app.model.dao.entity.PayrollSettingsEntity;

public interface PayrollLogic {
	public PayrollSettingsEntity getPayrollByDateAndEmpId(String dateMonth, int employee_id);
	
	public void insertPayrollSettings(PayrollSettingsEntity payroll);
	
	public List<JoinedPayrollSettings> getPayrollSettings(int employeeId);
	
	public void deletePayrollSettings(int payrollSettingsId);
	
	public List<PayrollEntity> findPayrollByEmployeeId(int employeeId);
	
	public List<JoinedAttendanceDailyPay> findDailyPayListByDateRange(int employeeId, String fromDate, String toDate);
	
	public PayrollEntity getpayrollById(int payrollID);
	
	public void insertPayroll(PayrollEntity payrollEntity);
}
