package com.Employee_Sacs.app.model.obj;

import com.Employee_Sacs.app.model.dto.UserInfoAccountDto;

import lombok.Data;

@Data
public class UserInfoAccountObj {
	private int employeeId;
	private String firstname;
	private String lastname;
	private String password;
	private String userName;
}
