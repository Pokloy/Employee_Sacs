package com.Employee_Sacs.app.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinedLeaveUserInfo {
	public JoinedLeaveUserInfo(Object[]objects) {
		this(
				(String) objects[0],
				(String) objects[1],
				(Integer) objects[2],
				(Integer) objects[3],
				(String) objects[4],
				(String) objects[5],
				(String) objects[6],
				(String) objects[7]
			);
	}
	
	private String firstname;
	
	private String lastname;
	
	private int creator_id;
	
	private int leave_id;
	
	private String leave_reason;
	
	private String start_date;
	
	private String end_date;
	
	private String status;
}
