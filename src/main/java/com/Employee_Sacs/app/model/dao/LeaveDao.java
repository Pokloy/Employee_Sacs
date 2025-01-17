package com.Employee_Sacs.app.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Employee_Sacs.app.model.dao.entity.JoinedLeaveUserInfo;
import com.Employee_Sacs.app.model.dao.entity.LeaveEntity;

@Repository
public interface LeaveDao extends JpaRepository<LeaveEntity, Integer> {
	final String VIEW_ALL_LEAVE_INFO_WITH_OWNER = " SELECT tei.firstname, tei.lastname, tl.creator_id, tl.leave_id, "
			+ " tl.leave_reason, tl.start_date, tl.end_date, tl.status "
			+ " FROM LeaveEntity tl "
			+ " INNER JOIN UserInformationEntity tei ON tl.creator_id = tei.employeeId ";
	
	final String VIEW_SPECIFIC_LEAVE_INFO = " SELECT tei.firstname, tei.lastname, tl.creator_id, tl.leave_id, "
			+ " tl.leave_reason, tl.start_date, tl.end_date, tl.status "
			+ " FROM LeaveEntity tl "
			+ " INNER JOIN UserInformationEntity tei ON tl.creator_id = tei.employeeId "
			+ " WHERE tl.leave_id = :leaveId ";
	
	final String VIEW_SPECIFIC_LEAVE_BY_CURRENT_USER = " SELECT tei.firstname, tei.lastname, tl.creator_id, tl.leave_id, "
			+ " tl.leave_reason, tl.start_date, tl.end_date, tl.status "
			+ " FROM LeaveEntity tl "
			+ " INNER JOIN UserInformationEntity tei ON tl.creator_id = tei.employeeId "
			+ " WHERE tl.creator_id = :creatorId ";
	
	@Query(VIEW_SPECIFIC_LEAVE_BY_CURRENT_USER)
	List<Object[]> getSpecificLeaveByCreatorIdRaw(int creatorId) throws DataAccessException;
	
	default List<JoinedLeaveUserInfo> getSpecificLeaveByCreatorId(int creatorId){
		List<Object[]> leaveObj = getSpecificLeaveByCreatorIdRaw(creatorId);
		
		if(leaveObj.isEmpty()) {
			return null;
		}
		
		List<JoinedLeaveUserInfo> result = new ArrayList<>();
		for(Object[] row : leaveObj) {
			result.add(new JoinedLeaveUserInfo(row));
		}
		return result;
	}
	
	
	@Query(value=VIEW_SPECIFIC_LEAVE_INFO)
	List<Object[]> getSpecificLeaveRaw(int leaveId) throws DataAccessException;
	
	default List<JoinedLeaveUserInfo>getSpecificLeave(int leaveId){
		List<Object[]> leaveObj = getSpecificLeaveRaw(leaveId);
		
		if(leaveObj.isEmpty()) {
			return null;
		}
		
		List<JoinedLeaveUserInfo> result = new ArrayList<>();
		for(Object[] row : leaveObj) {
			result.add(new JoinedLeaveUserInfo(row));
		}
		return result;
	}
	
	
	
	@Query(value=VIEW_ALL_LEAVE_INFO_WITH_OWNER)
	List<Object[]> getAllLeaveWithNameRaw() throws DataAccessException;
	
	default List<JoinedLeaveUserInfo> getAllLeaveWithName(){
		List<Object[]> leaveObj = getAllLeaveWithNameRaw();
		
		if(leaveObj.isEmpty()) {
			return null;
		}
		
		List<JoinedLeaveUserInfo> result = new ArrayList<>();
		for(Object[] row : leaveObj) {
			result.add(new JoinedLeaveUserInfo(row));
		}
		return result;
	}
	
}
