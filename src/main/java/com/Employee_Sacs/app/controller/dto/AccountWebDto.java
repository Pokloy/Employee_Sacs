package com.Employee_Sacs.app.controller.dto;

import java.util.List;

import com.Employee_Sacs.app.model.obj.UserInfoAccObj;
import com.Employee_Sacs.app.model.obj.UserInfoAccountObj;
import com.Employee_Sacs.app.model.obj.UserInfoObj;

import lombok.Data;

@Data
public class AccountWebDto {
	List<UserInfoAccObj> userInfoAccObjList;
	List<UserInfoAccountObj> userInfoAccObj;
	List<UserInfoObj> userInfoObjList;
	private int employeeId;
	private String firstname;
	private String lastname;
	private String middlename;
	private String address;
	private String email;
	private String mobileNumber;
	private String position;
	private String username;
	private String password;
	private String role;
}
