package com.Employee_Sacs.app.model.obj;

import lombok.Data;

@Data
public class LeaveObj {
	private String firstname;
	
	private String lastname;
	
	private int leave_id;
	
	private int creator_id;
	
	private String leave_reason;
	
	private String start_date;
	
	private String end_date;
	
	private String status;
}
