package com.Employee_Sacs.app.model.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.PayrollDao;
import com.Employee_Sacs.app.model.dao.PayrollSettingsDao;
import com.Employee_Sacs.app.model.dao.entity.JoinedAttendanceDailyPay;
import com.Employee_Sacs.app.model.dao.entity.JoinedPayrollSettings;
import com.Employee_Sacs.app.model.dao.entity.PayrollEntity;
import com.Employee_Sacs.app.model.dao.entity.PayrollSettingsEntity;
import com.Employee_Sacs.app.model.logic.PayrollLogic;

@Service
public class PayrollLogicImpl implements PayrollLogic {
	@Autowired
	private PayrollSettingsDao payrollSettingsDao;
	
	@Autowired
	private PayrollDao payrollDao;
	
	@Override
	public PayrollSettingsEntity getPayrollByDateAndEmpId(String dateMonth, int employee_id) {
		return payrollSettingsDao.findPayrollSettingsByDateAndEmpId(dateMonth, employee_id);
	}
	
	@Override
	public void insertPayrollSettings(PayrollSettingsEntity payroll) {
		 payrollSettingsDao.saveAndFlush(payroll);
	}
	
	@Override
	public List<JoinedPayrollSettings> getPayrollSettings(int employeeId){
		return payrollSettingsDao.getAllPayrollSettingsWithNames(employeeId);
	}
	
	@Override
	public void deletePayrollSettings(int payrollSettingsId) {
		payrollSettingsDao.deleteById(payrollSettingsId);
	}
	
	@Override
	public List<PayrollEntity> findPayrollByEmployeeId(int employeeId) {
		return payrollSettingsDao.findPayrollByEmployeeId(employeeId);
	}
	
	@Override
	public List<JoinedAttendanceDailyPay> findDailyPayListByDateRange(int employeeId, String fromDate, String toDate){
		return payrollSettingsDao.getDailyPayAndAttendanceByDateRange(employeeId, fromDate, toDate);
	}
	
	@Override
	public PayrollEntity getpayrollById(int payrollID) {
		return payrollDao.getpayrollById(payrollID);
	}
	
	@Override
	public void insertPayroll(PayrollEntity payrollEntity) {
		payrollDao.save(payrollEntity);
	}
}
