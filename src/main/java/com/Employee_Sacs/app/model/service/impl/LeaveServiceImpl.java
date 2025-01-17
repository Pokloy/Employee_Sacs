package com.Employee_Sacs.app.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.entity.JoinedLeaveUserInfo;
import com.Employee_Sacs.app.model.dao.entity.LeaveEntity;
import com.Employee_Sacs.app.model.dto.LeaveInOutDto;
import com.Employee_Sacs.app.model.logic.LeaveLogic;
import com.Employee_Sacs.app.model.obj.LeaveObj;
import com.Employee_Sacs.app.model.service.LeaveService;
import com.Employee_Sacs.app.model.service.LoggedInUserService;

@Service
public class LeaveServiceImpl implements LeaveService {
	@Autowired
	LeaveLogic leaveLogic;
	
	@Autowired
	LoggedInUserService loggedInUserService;
	
	@Override
	public LeaveInOutDto getSpecificLeaveByCreatorId() {
		LeaveInOutDto leaveIO = new LeaveInOutDto();
		List<JoinedLeaveUserInfo> leaveEntityList = leaveLogic.getSpecificLeaveByCreatorId(loggedInUserService.getEmployeeId());
		List<LeaveObj> leaveObjList = new ArrayList<>();
		
		if(leaveEntityList != null && !leaveEntityList.isEmpty()) {
			for(JoinedLeaveUserInfo LEEntity : leaveEntityList) {
				LeaveObj leaveObj = new LeaveObj();
				leaveObj.setCreator_id(LEEntity.getCreator_id());
				leaveObj.setLeave_reason(LEEntity.getLeave_reason());
				leaveObj.setStart_date(LEEntity.getStart_date());
				leaveObj.setEnd_date(LEEntity.getEnd_date());
				leaveObj.setStatus(LEEntity.getStatus());
				
				leaveObjList.add(leaveObj);
			}
			leaveIO.setLeaveObj(leaveObjList);
		}
		return leaveIO;
	}
	
	@Override
	public LeaveInOutDto getAllLeaveInfo() {
		LeaveInOutDto leaveIO = new LeaveInOutDto();
		List<LeaveEntity> leaveEntityList = leaveLogic.showAllLeave();
		List<LeaveObj> leaveObjList = new ArrayList<>();
		
		if(leaveEntityList != null && !leaveEntityList.isEmpty()) {
			for(LeaveEntity LEEntity : leaveEntityList) {
				LeaveObj leaveObj = new LeaveObj();
				leaveObj.setCreator_id(LEEntity.getCreator_id());
				leaveObj.setLeave_reason(LEEntity.getLeave_reason());
				leaveObj.setStart_date(LEEntity.getStart_date());
				leaveObj.setEnd_date(LEEntity.getEnd_date());
				leaveObj.setStatus(LEEntity.getStatus());
				
				leaveObjList.add(leaveObj);
			}
			leaveIO.setLeaveObj(leaveObjList);
		}
		return leaveIO;
	}
	
	@Override
	public LeaveInOutDto getAllLeaveInfoWithNames() {
		LeaveInOutDto leaveIO = new LeaveInOutDto();
		List<JoinedLeaveUserInfo> leaveEntityList = leaveLogic.getAllLeaveWithname();
		List<LeaveObj> leaveObjList = new ArrayList<>();
		if(leaveEntityList != null && !leaveEntityList.isEmpty()) {
			for(JoinedLeaveUserInfo joinedEntity : leaveEntityList ) {
				LeaveObj leaveObj = new LeaveObj();
				leaveObj.setFirstname(joinedEntity.getFirstname());
				leaveObj.setLastname(joinedEntity.getLastname());
				leaveObj.setCreator_id(joinedEntity.getCreator_id());
				leaveObj.setLeave_id(joinedEntity.getLeave_id());
				leaveObj.setLeave_reason(joinedEntity.getLeave_reason());
				leaveObj.setStart_date(joinedEntity.getStart_date());
				leaveObj.setEnd_date(joinedEntity.getEnd_date());
				leaveObj.setStatus(joinedEntity.getStatus());
				
				leaveObjList.add(leaveObj);
			}
			leaveIO.setLeaveObj(leaveObjList);
		}
		return leaveIO;
	}
	
	@Override
	public void addLeave(LeaveInOutDto leaveIO) {
		LeaveEntity leaveEntity = new LeaveEntity();
		leaveEntity.setLeave_id(leaveIO.getLeave_id());
		leaveEntity.setCreator_id(leaveIO.getCreator_id());
		leaveEntity.setLeave_reason(leaveIO.getLeave_reason());
		leaveEntity.setStart_date(leaveIO.getStart_date());
		leaveEntity.setEnd_date(leaveIO.getEnd_date());
		leaveEntity.setStatus(leaveIO.getStatus());
		
		leaveLogic.addLeave(leaveEntity);
	}
	
	@Override
	public void deleteLeave(int leaveId) {
		leaveLogic.deleteLeave(leaveId);
	}
	
	@Override
	public LeaveInOutDto getSpecificLeaveInfo(int leaveId) {
		LeaveInOutDto leaveIO = new LeaveInOutDto();
		List<JoinedLeaveUserInfo> leaveEntityList = leaveLogic.getSpecificLeave(leaveId);
		List<LeaveObj> leaveObjList = new ArrayList<>();
		if(leaveEntityList != null && !leaveEntityList.isEmpty()) {
			for(JoinedLeaveUserInfo joinedLeaveEntity : leaveEntityList) {
				LeaveObj leaveObj = new LeaveObj();
				leaveObj.setFirstname(joinedLeaveEntity.getFirstname());
				leaveObj.setLastname(joinedLeaveEntity.getLastname());
				leaveObj.setCreator_id(joinedLeaveEntity.getCreator_id());
				leaveObj.setLeave_id(joinedLeaveEntity.getLeave_id());
				leaveObj.setLeave_reason(joinedLeaveEntity.getLeave_reason());
				leaveObj.setStart_date(joinedLeaveEntity.getStart_date());
				leaveObj.setEnd_date(joinedLeaveEntity.getEnd_date());
				leaveObj.setStatus(joinedLeaveEntity.getStatus());
				
				leaveObjList.add(leaveObj);
			}
			leaveIO.setLeaveObj(leaveObjList);
		}
		return leaveIO;
	}
}
