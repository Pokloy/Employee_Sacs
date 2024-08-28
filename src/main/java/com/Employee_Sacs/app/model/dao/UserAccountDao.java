package com.Employee_Sacs.app.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Employee_Sacs.app.model.dao.entity.UserAccountEntity;

@Repository
public interface UserAccountDao extends JpaRepository<UserAccountEntity, Integer>{
	final String GET_EMPLOYEE_ID_USING_USERNAME = " SELECT e.employeeId "
			+ " FROM UserAccountEntity e "
			+ " WHERE username = :username ";
	
	@Query(value=GET_EMPLOYEE_ID_USING_USERNAME)
	public int getEmployeeID(String username) throws DataAccessException;
}
