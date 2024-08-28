package com.Employee_Sacs.app.model.dto;

import java.util.List;

import com.Employee_Sacs.app.model.obj.UserInfoAccObj;
import com.Employee_Sacs.app.model.obj.UserInfoObj;

import lombok.Data;

@Data
public class UserInfoInOutDto {
	List<UserInfoAccObj> userInfoAccObjList;
	List<UserInfoObj> userInfoObjList;
	private int employeeId;
	private String firstname;
	private String lastname;
	private String middlename;
	private String address;
	private String email;
	private String role;
	private String mobileNumber;
	private String position;
}
