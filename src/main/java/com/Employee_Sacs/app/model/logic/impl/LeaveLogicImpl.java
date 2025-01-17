package com.Employee_Sacs.app.model.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.LeaveDao;
import com.Employee_Sacs.app.model.dao.entity.JoinedLeaveUserInfo;
import com.Employee_Sacs.app.model.dao.entity.LeaveEntity;
import com.Employee_Sacs.app.model.logic.LeaveLogic;

@Service
public class LeaveLogicImpl implements LeaveLogic {
	@Autowired
	private LeaveDao leaveDao;
	
	public LeaveEntity addLeave(LeaveEntity leaveEntity) {
		return leaveDao.saveAndFlush(leaveEntity);
	}
	
	public List<LeaveEntity> showAllLeave() {
		return leaveDao.findAll();
	}
	
	public List<JoinedLeaveUserInfo> getAllLeaveWithname(){
		return leaveDao.getAllLeaveWithName();
	}
	
	public void deleteLeave(int leaveId) {
		leaveDao.deleteById(leaveId);
	}
	
	public List<JoinedLeaveUserInfo> getSpecificLeave(int leaveId){
		return leaveDao.getSpecificLeave(leaveId);
	}
	
	public List<JoinedLeaveUserInfo> getSpecificLeaveByCreatorId(int creatorId){
		return leaveDao.getSpecificLeaveByCreatorId(creatorId);
	}
}
