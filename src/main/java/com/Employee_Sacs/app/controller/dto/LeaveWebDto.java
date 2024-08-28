package com.Employee_Sacs.app.controller.dto;

import java.util.List;

import com.Employee_Sacs.app.model.obj.LeaveObj;
import com.Employee_Sacs.app.model.obj.UserInfoObj;

import lombok.Data;

@Data
public class LeaveWebDto {
	private List<LeaveObj> leaveObj;
	
	private List<UserInfoObj> userInfoObj;
	
	private String firstname;
	
	private String lastname;
	
	private int leave_id;
	
	private int creator_id;
	
	private String leave_reason;
	
	private String start_date;
	
	private String end_date;
	
	private String status;
}
