package com.Employee_Sacs.app.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Employee_Sacs.app.model.dao.entity.DailyPayEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedDailyPay;

@Repository
public interface DailyPayDao extends JpaRepository<DailyPayEntity, Integer> {
	
	final String GET_DAILY_PAY_BY_EMPLOYEE_ID = " SELECT ti.firstname, ti.lastname, td.dailypay_id, "
			+ " td.attendance_id, td.regulardaily, td.overtimedaily, "
			+ " td.latedaily, td.status, ta.date "
			+ " FROM DailyPayEntity td "
			+ " INNER JOIN AttendanceEntity ta ON td.attendance_id = ta.attendance_id "
			+ " INNER JOIN UserInformationEntity ti ON ta.employee_id = ti.employeeId "
			+ " WHERE ti.employeeId = :employeeId ";
	
	@Query(value = GET_DAILY_PAY_BY_EMPLOYEE_ID)
	public List<Object[]> getDailyPayWithNameRaw(int employeeId) throws DataAccessException;
	
	default List<JoinedDailyPay> getDailyPayWithName(int employeeId){
		List<Object[]> dailyPayObj = getDailyPayWithNameRaw(employeeId);
		
		if(dailyPayObj.isEmpty()) {
			return null;
		}
		
		List<JoinedDailyPay> result = new ArrayList<>();
		for(Object[] row : dailyPayObj) {
			result.add(new JoinedDailyPay(row));
		}
		return result;
	}
	
	
}
