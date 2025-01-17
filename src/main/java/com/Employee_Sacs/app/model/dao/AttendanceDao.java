package com.Employee_Sacs.app.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.Employee_Sacs.app.model.dao.entity.AttendanceEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedAttendanceUserInfo;

public interface AttendanceDao extends JpaRepository<AttendanceEntity, Integer> {

    final String VIEW_ALL_ATTENDANCE_BY_EMPLOYEE_ID = "SELECT e FROM AttendanceEntity e WHERE employee_id = :employeeID";
    
    final String GET_ATTENDANCE_BY_USER_ID = "SELECT e FROM AttendanceEntity e WHERE e.attendance_id = :attendanceID";
    
//    final String VIEW_ALL_ATTENDANCE_WITH_NAME = "SELECT te.firstname, te.lastname, "
//            + "ta.attendance_id, ta.clockin, ta.breakin, "
//            + "ta.breakout, ta.clockout, ta.attendancehours, "
//            + "ta.status, ta.date "
//            + "FROM tb_employee_info te "
//            + "INNER JOIN tb_attendance ta ON te.employee_id = ta.employee_id";
    
    
    final String VIEW_ALL_ATTENDANCE_WITH_NAME = "SELECT te.firstname, te.lastname, "
            + "ta.attendance_id, ta.employee_id, ta.clockin, ta.breakin, "
            + "ta.breakout, ta.clockout, ta.attendancehours, "
            + "ta.status, ta.date, ta.breakhours, ta.latehours, ta.overtime "
            + "FROM UserInformationEntity te "
            + "INNER JOIN AttendanceEntity ta ON te.employeeId = ta.employee_id";

    final String UPDATE_BREAKIN_BY_EMPLOYEE_ID_AND_DATE = "UPDATE AttendanceEntity "
            + "SET breakin = :breakIn "
            + "WHERE employee_id = :employeeId AND date = :date";

    final String UPDATE_BREAKOUT_BY_EMPLOYEE_ID_AND_DATE = "UPDATE AttendanceEntity  "
            + "SET breakout = :breakOut "
            + "WHERE employee_id = :employeeId AND date = :date";

    final String UPDATE_CLOCKOUT_BY_EMPLOYEE_ID_AND_DATE = "UPDATE AttendanceEntity  "
            + "SET clockout = :clockOut "
            + "WHERE employee_id = :employeeId AND date = :date";

    final String UPDATE_ATTENDANCE_BY_ATTENDANCE_ID = "UPDATE AttendanceEntity  "
            + "SET clockin = :clockin, "
            + " breakin = :breakin, "
            + " breakout = :breakout, "
            + " clockout = :clockout, "
            + " status = :status "
            + "WHERE attendance_id = :attendanceID";
    
    final String UPDATE_ATTENDANCE_HOURS = "UPDATE AttendanceEntity"
    		+ " SET attendancehours = :attendancehours, "
    		+ " breakhours = :breakhours,"
    		+ " latehours = :latehours,"
    		+ " overtime = :overtime "
    		+ " WHERE employee_id = :employeeId AND date = :date ";
    
   
    final String SELECT_ATTENDANCE_BY_DATE_AND_EMPLOYEE_ID = " SELECT e "
    		+ " FROM AttendanceEntity e "
    		+ " WHERE date = :date AND "
    		+ " employee_id = :employeeId ";
    
    final String SELECT_SPECIFIC_ATTENDANCE_BY_CLOCKOUT_IS_ZERO = " SELECT e "
    		+ " FROM AttendanceEntity e "
    		+ " WHERE clockout = :clockOut ";
    
    
    @Query(value = SELECT_SPECIFIC_ATTENDANCE_BY_CLOCKOUT_IS_ZERO)
    public AttendanceEntity getSpecificAttendanceByClockoutIsZero(String clockOut) throws DataAccessException;
    
    @Query(value = SELECT_ATTENDANCE_BY_DATE_AND_EMPLOYEE_ID)
    public AttendanceEntity getAttendanceByDateandEmployeeId(String date, int employeeId) throws DataAccessException;
    
    @Modifying
    @Transactional
    @Query(value = UPDATE_ATTENDANCE_HOURS)
    public void updateAttendanceHour(String attendancehours, String breakhours, int employeeId, String date, double latehours, double overtime) throws DataAccessException;
    

    @Modifying
    @Transactional
    @Query(value = UPDATE_ATTENDANCE_BY_ATTENDANCE_ID)
    public void updateStatusByAttenID(
            int attendanceID,
            String clockin,
            String breakin,
            String breakout,
            String clockout,
            String status) throws DataAccessException;

    @Query(value = VIEW_ALL_ATTENDANCE_BY_EMPLOYEE_ID)
    public List<AttendanceEntity> getAllAttendanceEntity(String employeeID) throws DataAccessException;

    @Modifying
    @Transactional
    @Query(value = UPDATE_BREAKIN_BY_EMPLOYEE_ID_AND_DATE)
    public void updateBreakInIdByDateNow(String breakIn, String employeeId, String date) throws DataAccessException;

    @Modifying
    @Transactional
    @Query(value = UPDATE_BREAKOUT_BY_EMPLOYEE_ID_AND_DATE)
    public void updateBreakOutIdByDateNow(String breakOut, String employeeId, String date) throws DataAccessException;

    @Modifying
    @Transactional
    @Query(value = UPDATE_CLOCKOUT_BY_EMPLOYEE_ID_AND_DATE)
    public void updateClockOutIdByDateNow(String clockOut, String employeeId, String date) throws DataAccessException;

    @Query(value = GET_ATTENDANCE_BY_USER_ID)
    public AttendanceEntity getAttendanceById(int attendanceID) throws DataAccessException;

    @Query(value = VIEW_ALL_ATTENDANCE_WITH_NAME)
    List<Object[]> getAllAttendanceWithNamesRaw() throws DataAccessException;

    default List<JoinedAttendanceUserInfo> getAllAttendanceWithNames() {
        List<Object[]> attendanceObj = getAllAttendanceWithNamesRaw();
        
        if(attendanceObj.isEmpty()) {
            return null;
        }
        
        List<JoinedAttendanceUserInfo> result = new ArrayList();
        for (Object[] row : attendanceObj) {
            result.add(new JoinedAttendanceUserInfo(row));
        }
        
        return result;
    }
}


//default JoinedAttendanceUserInfo getAllAttendanceWithNames() {
//	List<Object[]> attendanceObj = getAllAttendanceWithNamesRaw();
//	
//	if(attendanceObj.isEmpty()) {
//		return null;
//	}
//	
//	Object[] firstRow = attendanceObj.get(0);
//	
//	String firstName = (String) firstRow[0];
//	String lastName = (String) firstRow[1];
//	int attendanceId = (int) firstRow[2];
//	int employeeId = (int) firstRow[3];
//	String clockIn = (String) firstRow[4];
//	String breakIn = (String) firstRow[5];
//	String breakOut = (String) firstRow[6];
//	String clockOut = (String) firstRow[7];
//	
//	return new JoinedAttendanceUserInfo(firstName, 
//			lastName, 
//			attendanceId, 
//			employeeId, 
//			clockIn,
//			breakIn,
//			breakOut,
//			clockOut);
//} 
