package com.Employee_Sacs.app.model.dto;

import java.util.List;

import com.Employee_Sacs.app.model.obj.UserInfoAccountObj;

import lombok.Data;

@Data
public class UserInfoAccountDto {
	List<UserInfoAccountObj> userInfoAccObj;
	private int employeeId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String address;
	private String email;
	private String role;
	private String mobile_number;
	private String position;
	private String userName;
	private String password;
	private String conPassword;
}
