package com.Employee_Sacs.app.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinedUserInfoAndAccount {
	public JoinedUserInfoAndAccount(Object[]objects) {
		this((Integer) objects[0],
				(String) objects[1],
				(String) objects[2],
				(String) objects[3],
				(String) objects[4],
				(String) objects[5],
				(String) objects[6],
				(String) objects[7],
				(String) objects[8],
				(String) objects[9],
				(String) objects[10]
				);
		
	}
	private Integer employeeId;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private String address;
	
	private String email;
	
	private String role;
	
	private String mobile_number;
	
	private String position;
	
	private String password;
	
	private String username;
	

}
