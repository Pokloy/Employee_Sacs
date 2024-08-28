package com.Employee_Sacs.app.model.service;

import com.Employee_Sacs.app.model.dto.LeaveInOutDto;

public interface LeaveService {
	public LeaveInOutDto getAllLeaveInfo();
	
	public LeaveInOutDto getAllLeaveInfoWithNames();
	
	public void addLeave(LeaveInOutDto leaveIO);
	
	public void deleteLeave(int leaveId);
	
	public LeaveInOutDto getSpecificLeaveInfo(int leaveId);
	
	public LeaveInOutDto getSpecificLeaveByCreatorId();
}
