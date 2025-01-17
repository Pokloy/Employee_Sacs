package com.Employee_Sacs.app.model.logic.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.UserAccountDao;
import com.Employee_Sacs.app.model.dao.UserDao;
import com.Employee_Sacs.app.model.dao.UserInfoDao;
import com.Employee_Sacs.app.model.dao.entity.JoinedUserInfoAndAccount;
import com.Employee_Sacs.app.model.dao.entity.UserAccountEntity;
import com.Employee_Sacs.app.model.dao.entity.UserInformationEntity;
import com.Employee_Sacs.app.model.logic.UserAccountLogic;

@Service
public class UserAccountLogicImpl implements UserAccountLogic {
	@Autowired
	UserAccountDao userAccountDao;
	
	@Autowired
	UserInfoDao userInfoDao;
	
	@Autowired
	UserDao userDao;
	
	@Override
	public int getEmployeeId(String username) {
		return userAccountDao.getEmployeeID(username);
	}
	
	@Override
	public List<UserInformationEntity> getAllEmployee(){
		return userInfoDao.findAll();
	}
	
	@Override
	public UserInformationEntity getUserInfoByFirstName(String firstName) {
		return userInfoDao.getUserInfoByFirstName(firstName);
	}
	
	@Override
	public UserInformationEntity getUserInfoByEmployeeId(Integer employeeID) {
		return userInfoDao.getUserInfoByEmployeeId(employeeID);
	}

	@Override
	public List<JoinedUserInfoAndAccount> findAllUser(){
		return userDao.findAllUser();
	}
	
	@Override
	public UserInformationEntity addNewUser(UserInformationEntity userInfoEntity) {
		return userInfoDao.saveAndFlush(userInfoEntity);
	}
	
	@Override
	public UserAccountEntity addNewUserAcc(UserAccountEntity userAccEntity) {
		return userDao.saveAndFlush(userAccEntity);
	}
	
	@Override
	public List<JoinedUserInfoAndAccount> findUserByUserAccById(Integer employeeIdQuery){
		return userDao.findUserByUserAccountById(employeeIdQuery);
	}
	
	@Override
	public void deleteUserAccInfo(Integer empId) {
		userDao.deleteById(empId);
		userInfoDao.deleteById(empId);
	}
	
	@Override
	public void updateUserInfo(UserInformationEntity userInfoEntity) {
		userInfoDao.save(userInfoEntity);
	}
	
	@Override
	public void updateUserAcc(UserAccountEntity userAccountEntity) {
		userAccountDao.save(userAccountEntity);
	}
}
