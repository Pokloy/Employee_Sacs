package com.Employee_Sacs.app.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.entity.JoinedUserInfoAndAccount;
import com.Employee_Sacs.app.model.dao.entity.UserAccountEntity;
import com.Employee_Sacs.app.model.dao.entity.UserInformationEntity;
import com.Employee_Sacs.app.model.dto.UserInfoAccountDto;
import com.Employee_Sacs.app.model.dto.UserInfoInOutDto;
import com.Employee_Sacs.app.model.logic.UserAccountLogic;
import com.Employee_Sacs.app.model.obj.UserInfoAccObj;
import com.Employee_Sacs.app.model.obj.UserInfoAccountObj;
import com.Employee_Sacs.app.model.obj.UserInfoObj;
import com.Employee_Sacs.app.model.service.LoggedInUserService;

@Service
public class LoggedInUserServiceImp implements LoggedInUserService{
	
	@Autowired
	private UserAccountLogic userAccountLogic;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public int getEmployeeId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return userAccountLogic.getEmployeeId(authentication.getName());
	}
	
	
	@Override
	public UserInfoAccountDto findAllUsersAccountWithName() {
		UserInfoAccountDto userInfoAccountIO = new UserInfoAccountDto();
		List<JoinedUserInfoAndAccount> joinedUserInfoAcc = userAccountLogic.findAllUser();
		List<UserInfoAccountObj> userInfoAccountList = new ArrayList<>();
		if(joinedUserInfoAcc != null && !joinedUserInfoAcc.isEmpty()) {
			for(JoinedUserInfoAndAccount joinedUserInfoAccEntity : joinedUserInfoAcc) {
				UserInfoAccountObj newUserInfoAccountObj = new UserInfoAccountObj();
				newUserInfoAccountObj.setEmployeeId(joinedUserInfoAccEntity.getEmployeeId());
				newUserInfoAccountObj.setFirstname(joinedUserInfoAccEntity.getFirstName());
				newUserInfoAccountObj.setLastname(joinedUserInfoAccEntity.getLastName());
				newUserInfoAccountObj.setPassword(joinedUserInfoAccEntity.getPassword());
				newUserInfoAccountObj.setUserName(joinedUserInfoAccEntity.getUsername());
				userInfoAccountList.add(newUserInfoAccountObj);
			}
			userInfoAccountIO.setUserInfoAccObj(userInfoAccountList);
		}
		return userInfoAccountIO;
	}
	
	@Override
	public UserInfoInOutDto findUserById(Integer employeeIdQuery) {
		UserInfoInOutDto userIO = new UserInfoInOutDto();
		List<UserInfoAccObj> userAccIoObj = new ArrayList<>();
		List<JoinedUserInfoAndAccount> joinedUserEntity = userAccountLogic.findUserByUserAccById(employeeIdQuery);
		if(joinedUserEntity != null && !joinedUserEntity.isEmpty()) {
			for(JoinedUserInfoAndAccount joinedUserIA : joinedUserEntity) {
				UserInfoAccObj newUserInfoAccObj = new UserInfoAccObj();
				newUserInfoAccObj.setEmployeeId(joinedUserIA.getEmployeeId());
				newUserInfoAccObj.setFirstname(joinedUserIA.getFirstName());
				newUserInfoAccObj.setLastname(joinedUserIA.getLastName());
				newUserInfoAccObj.setMiddlename(joinedUserIA.getMiddleName());
				newUserInfoAccObj.setAddress(joinedUserIA.getAddress());
				newUserInfoAccObj.setEmail(joinedUserIA.getEmail());
				newUserInfoAccObj.setRole(joinedUserIA.getRole());
				newUserInfoAccObj.setMobileNumber(joinedUserIA.getMobile_number());
				newUserInfoAccObj.setPosition(joinedUserIA.getPosition());
				newUserInfoAccObj.setUsername(joinedUserIA.getUsername());
				userAccIoObj.add(newUserInfoAccObj);
			}
			userIO.setUserInfoAccObjList(userAccIoObj);
		}
		return userIO;
	}
	
	@Override
	public UserInfoInOutDto getAllUserInfo() {
		UserInfoInOutDto UserInfoIO = new UserInfoInOutDto();
		List<UserInformationEntity> userInfoList = userAccountLogic.getAllEmployee();
		List<UserInfoObj> userInfoObjList = new ArrayList<>();
		
		if(userInfoList != null && !userInfoList.isEmpty()) {
			for(UserInformationEntity UIEntity : userInfoList) {
				UserInfoObj userInfoObj = new UserInfoObj();
				userInfoObj.setEmployeeId(UIEntity.getEmployeeId());
				userInfoObj.setFirstname(UIEntity.getFirstname());
				userInfoObj.setLastname(UIEntity.getLastname());
				userInfoObj.setMiddlename(UIEntity.getMiddlename());
				userInfoObj.setEmail(UIEntity.getEmail());
				userInfoObj.setAddress(UIEntity.getAddress());
				userInfoObj.setMobileNumber(UIEntity.getMobileNumber());
				userInfoObj.setRole(UIEntity.getRole());
				
				userInfoObjList.add(userInfoObj);
			}
			UserInfoIO.setUserInfoObjList(userInfoObjList);
		}
		return UserInfoIO;
	}
	
	@Override
	public UserInfoInOutDto getUserInfoByFirstName(String firstname) {
		UserInfoInOutDto UserInfoIO = new UserInfoInOutDto();
		UserInformationEntity userInfoEntity = userAccountLogic.getUserInfoByFirstName(firstname);
		UserInfoIO.setEmployeeId(userInfoEntity.getEmployeeId());
		UserInfoIO.setFirstname(userInfoEntity.getFirstname());
		UserInfoIO.setLastname(userInfoEntity.getLastname());
		UserInfoIO.setMiddlename(userInfoEntity.getMiddlename());
		UserInfoIO.setMobileNumber(userInfoEntity.getMobileNumber());
		UserInfoIO.setRole(userInfoEntity.getRole());
		UserInfoIO.setAddress(userInfoEntity.getAddress());
		return UserInfoIO;
	}
	
	@Override
	public UserInfoInOutDto getUserInfoByEmployeeId(Integer employeeID) {
		UserInfoInOutDto userInfoIO = new UserInfoInOutDto();
		UserInformationEntity userInfoEntity = userAccountLogic.getUserInfoByEmployeeId(employeeID);
		userInfoIO.setFirstname(userInfoEntity.getFirstname());
		userInfoIO.setLastname(userInfoEntity.getLastname());
		userInfoIO.setMiddlename(userInfoEntity.getMiddlename());
		userInfoIO.setMobileNumber(userInfoEntity.getMobileNumber());
		userInfoIO.setRole(userInfoEntity.getRole());
		userInfoIO.setAddress(userInfoEntity.getAddress());
		userInfoIO.setEmail(userInfoEntity.getEmail());
		userInfoIO.setEmployeeId(userInfoEntity.getEmployeeId());
		return userInfoIO;
	}

	@Override
	public void addNewUser(UserInfoAccountDto userAccIO , UserInfoInOutDto userInfoIO) {
		UserAccountEntity userAccEntity = new UserAccountEntity();
		UserInformationEntity userInfoEntity =  new UserInformationEntity();
		
		userInfoEntity.setFirstname(userInfoIO.getFirstname());
		userInfoEntity.setLastname(userInfoIO.getLastname());
		userInfoEntity.setMiddlename(userInfoIO.getMiddlename());
		userInfoEntity.setMobileNumber(userInfoIO.getMobileNumber());
		userInfoEntity.setPosition(userInfoIO.getPosition());
		userInfoEntity.setRole(userInfoIO.getRole());
		userInfoEntity.setAddress(userInfoIO.getAddress());
		userInfoEntity.setEmail(userInfoIO.getEmail());
		userAccountLogic.addNewUser(userInfoEntity);
		
		UserInfoInOutDto getNewAddUser = getUserInfoByFirstName(userInfoIO.getFirstname());
		int newUserEmpId = getNewAddUser.getEmployeeId();
		userAccEntity.setEmployeeId(newUserEmpId);
		userAccEntity.setUsername(userAccIO.getUserName());
		userAccEntity.setPassword(encoder.encode(userAccIO.getPassword()));
		userAccountLogic.addNewUserAcc(userAccEntity);
	}
	
	@Override
	public void deleteUser(Integer empId) {
		userAccountLogic.deleteUserAccInfo(empId);
	}
}
