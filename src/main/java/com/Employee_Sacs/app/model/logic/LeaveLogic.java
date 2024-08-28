package com.Employee_Sacs.app.model.logic;

import java.util.List;

import com.Employee_Sacs.app.model.dao.entity.JoinedLeaveUserInfo;
import com.Employee_Sacs.app.model.dao.entity.LeaveEntity;

public interface LeaveLogic {
	public LeaveEntity addLeave(LeaveEntity leaveEntity);
	
	public List<LeaveEntity> showAllLeave();
	
	public List<JoinedLeaveUserInfo> getAllLeaveWithname();
	
	public void deleteLeave(int leaveId);
	
	public List<JoinedLeaveUserInfo> getSpecificLeave(int leaveId);
	
	public List<JoinedLeaveUserInfo> getSpecificLeaveByCreatorId(int creatorId);
}
