package com.Employee_Sacs.app.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinedUserInfoAccount {
	public JoinedUserInfoAccount(Object[]objects) {
		this((String) objects[0],
			(String) objects[1],
			(String) objects[2]
		   );
	}
	public String username;
	
	public String password;
	
	public String role;
}
