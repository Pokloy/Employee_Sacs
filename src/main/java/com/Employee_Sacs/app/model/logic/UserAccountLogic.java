package com.Employee_Sacs.app.model.logic;

import java.util.List;

import com.Employee_Sacs.app.model.dao.entity.JoinedUserInfoAndAccount;
import com.Employee_Sacs.app.model.dao.entity.UserAccountEntity;
import com.Employee_Sacs.app.model.dao.entity.UserInformationEntity;

public interface UserAccountLogic {
	public int getEmployeeId(String username);
	
	public List<UserInformationEntity> getAllEmployee();
	
	public UserInformationEntity getUserInfoByFirstName(String firstName);
	
	public UserInformationEntity getUserInfoByEmployeeId(Integer employeeID);
	
	public List<JoinedUserInfoAndAccount> findAllUser();
	
	public UserInformationEntity addNewUser(UserInformationEntity userInfoEntity);
	
	public UserAccountEntity addNewUserAcc(UserAccountEntity userAccEntity);
	
	public List<JoinedUserInfoAndAccount> findUserByUserAccById(Integer employeeIdQuery);
	
	public void deleteUserAccInfo(Integer empId);
	
	public void updateUserInfo(UserInformationEntity userInfoEntity);
	
	public void updateUserAcc(UserAccountEntity userAccountEntity);
}
