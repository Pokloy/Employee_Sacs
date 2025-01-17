package com.Employee_Sacs.app.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Employee_Sacs.app.model.dao.entity.UserInformationEntity;

public interface UserInfoDao extends JpaRepository<UserInformationEntity, Integer> {
	final String FIND_USER_INFO_BY_FIRSTNAME = " SELECT e FROM UserInformationEntity e "
			+ " WHERE firstname = :firstName ";
	final String FIND_USER_INFO_BY_EMP_ID = " SELECT e "
			+ " FROM UserInformationEntity e "
			+ " WHERE employeeId = :employeeID";

	@Query(value = FIND_USER_INFO_BY_FIRSTNAME)
	public UserInformationEntity getUserInfoByFirstName(String firstName);
	
	@Query(value = FIND_USER_INFO_BY_EMP_ID)
	public UserInformationEntity getUserInfoByEmployeeId(Integer employeeID);
}
