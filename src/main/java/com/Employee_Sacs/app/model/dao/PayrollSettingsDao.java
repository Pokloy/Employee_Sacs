package com.Employee_Sacs.app.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Employee_Sacs.app.model.dao.entity.JoinedAttendanceDailyPay;
import com.Employee_Sacs.app.model.dao.entity.JoinedPayrollSettings;
import com.Employee_Sacs.app.model.dao.entity.PayrollEntity;
import com.Employee_Sacs.app.model.dao.entity.PayrollSettingsEntity;

@Repository
public interface PayrollSettingsDao extends JpaRepository<PayrollSettingsEntity, Integer> {
	final String FIND_PAYROLL_SETTINGS_BY_DATE_AND_EMP_ID = " SELECT e "
			+ " FROM PayrollSettingsEntity e "
			+ " WHERE date = :dateMonth AND "
			+ " employee_id = :employee_id ";

	final String GET_ALL_PAYROLL_SETTINGS_BY_EMPLOYEE_ID = " SELECT ts.s_payroll_id, ts.employee_id, "
			+ " ti.firstname, ti.lastname, ts.month_days, "
			+ " ts.non_workingdays, ts.salary, ts.active, ts.date "
			+ " FROM PayrollSettingsEntity ts "
			+ " INNER JOIN UserInformationEntity ti ON ts.employee_id = ti.employeeId "
			+ " WHERE ti.employeeId = :employeeId";
	
	final String GET_ALL_PAYROLL_BY_EMPLOYEEE_ID = " SELECT e FROM PayrollEntity e WHERE employee_id = :employeeId ";
	
//	final String ADD_PAYROLL_SETTINGS = " INSERT INTO PayrollSettingsEntity "
//			+ " (employee_id, month_days, non_workingdays, salary, active, date) "
//			+ " VALUES ( :employeeId, :monthDays, :nonWorkingDays, :salary, :active, :date ) ";
	
	final String GET_ALL_DATE_RANGE = " SELECT ta.attendance_id, "
			+ " ta.employee_id, "
			+ " ta.attendancehours, "
			+ " td.regulardaily, "
			+ "	ta.latehours, "
			+ "	td.latedaily, "
			+ "	ta.overtime, "
			+ "	td.overtimedaily,"
			+ "	ta.date "
			+ "FROM AttendanceEntity ta "
			+ "INNER JOIN DailyPayEntity td ON ta.attendance_id = td.attendance_id "
			+ "WHERE ta.employee_id = :employeeId AND date BETWEEN :fromDate AND :toDate ";
	
	
	@Query(value=FIND_PAYROLL_SETTINGS_BY_DATE_AND_EMP_ID)
	public PayrollSettingsEntity findPayrollSettingsByDateAndEmpId(String dateMonth, int employee_id) throws DataAccessException;

	@Query(value=GET_ALL_PAYROLL_SETTINGS_BY_EMPLOYEE_ID)
	List<Object[]> getAllPayrollSettingsWithNamesRaw(int employeeId) throws DataAccessException;
	
	default List<JoinedPayrollSettings> getAllPayrollSettingsWithNames(int employeeId) {
		List<Object[]> payrollSettingsObj = getAllPayrollSettingsWithNamesRaw(employeeId);
		
		if(payrollSettingsObj.isEmpty()) {
			return null;
		}
		
		List<JoinedPayrollSettings> result = new ArrayList<>(); 
		for(Object[] row : payrollSettingsObj) {
			result.add(new JoinedPayrollSettings(row));
		}
		return result;
	}
	
	@Query(value = GET_ALL_PAYROLL_BY_EMPLOYEEE_ID)
	public List<PayrollEntity> findPayrollByEmployeeId(int employeeId) throws DataAccessException;
	
	@Query(value = GET_ALL_DATE_RANGE)
	List<Object[]> getDailyPayAndAttendanceByDateRangeRaw(int employeeId, String fromDate, String toDate) throws DataAccessException;
	
	default List<JoinedAttendanceDailyPay> getDailyPayAndAttendanceByDateRange(int employeeId, String fromDate, String toDate){
		List<Object[]> dailyPayAttendanceObj = getDailyPayAndAttendanceByDateRangeRaw(employeeId, fromDate, toDate);
		
		if(dailyPayAttendanceObj.isEmpty()) {
			return null;
		}
		
		List<JoinedAttendanceDailyPay> result = new ArrayList<>();
		for(Object[] row : dailyPayAttendanceObj) {	
			result.add(new JoinedAttendanceDailyPay(row));
		}
		return result;
		
	}
	
	
//	@Query(value=ADD_PAYROLL_SETTINGS)
//	public void insertPayrollSettings(int employeeId, double monthDays, double nonWorkingDays, double salary, boolean active, String date) throws DataAccessException;
}