package com.Employee_Sacs.app.model.service;

import com.Employee_Sacs.app.model.dao.entity.UserAccountEntity;
import com.Employee_Sacs.app.model.dao.entity.UserInformationEntity;
import com.Employee_Sacs.app.model.dto.UserInfoAccountDto;
import com.Employee_Sacs.app.model.dto.UserInfoInOutDto;

public interface LoggedInUserService {
	public int getEmployeeId();
	
	public UserInfoInOutDto getAllUserInfo();
	
	public UserInfoInOutDto getUserInfoByFirstName(String firstname);
	
	public UserInfoInOutDto getUserInfoByEmployeeId(Integer employeeID);
	
	public UserInfoAccountDto findAllUsersAccountWithName();
	
	public void addNewUser(UserInfoAccountDto userInfoAccIO , UserInfoInOutDto userInfoIO);
	
	public UserInfoInOutDto findUserById(Integer employeeIdQuery);
	
	public void deleteUser(Integer empId);
}
