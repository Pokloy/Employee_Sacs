package com.Employee_Sacs.app.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Employee_Sacs.app.model.dao.entity.JoinedUserInfoAccount;
import com.Employee_Sacs.app.model.dao.entity.JoinedUserInfoAndAccount;
import com.Employee_Sacs.app.model.dao.entity.UserAccountEntity;

@Repository
public interface UserDao extends JpaRepository<UserAccountEntity, Integer> {

    final String FIND_USER_INFO_AND_ACCOUNT_BY_USERNAME = 
            "SELECT e.username, e.password, i.role " +
            "FROM UserAccountEntity e " +
            "LEFT JOIN UserInformationEntity i ON i.employeeId = e.employeeId " +
            "WHERE e.username = :username";
    
    
    final String GET_ALL_USER_AND_INFO = " SELECT ti.employeeId,"
    		+ " ti.firstname, "
    		+ " ti.lastname, "
    		+ " ti.middlename, "
    		+ " ti.address, "
    		+ " ti.email, "
    		+ " ti.role, "
    		+ " ti.mobileNumber, "
    		+ " ti.position, "
    		+ " ta.password, "
    		+ " ta.username "
    		+ " FROM UserAccountEntity ta "
    		+ " INNER JOIN UserInformationEntity ti ON ta.employeeId = ti.employeeId ";
    
    final String FIND_USER_AND_INFO_BY_ID = " SELECT ti.employeeId, "
    		+ " ti.firstname, "
    		+ " ti.lastname, "
    		+ " ti.middlename, "
    		+ " ti.address, "
    		+ " ti.email, "
    		+ " ti.role, "
    		+ " ti.mobileNumber, "
    		+ " ti.position, "
    		+ " ta.password, "
    		+ " ta.username "
    		+ " FROM UserAccountEntity ta "
    		+ " INNER JOIN UserInformationEntity ti ON ta.employeeId = ti.employeeId "
    		+ " WHERE ta.employeeId = :employeeIdQuery ";
    

    @Query(value = FIND_USER_INFO_AND_ACCOUNT_BY_USERNAME)
    List<Object[]> findUserInfoAndAccountByUsernameRaw(@Param("username") String username) throws DataAccessException;

    default JoinedUserInfoAccount findUserInfoAndAccountByUsername(String username) {
        List<Object[]> objList = findUserInfoAndAccountByUsernameRaw(username);
        
        if (objList.isEmpty()) {
            return null;  // Or throw an exception if appropriate
        }
        
        Object[] firstRow = objList.get(0);
        
        String returnedUsername = (String) firstRow[0];
        String password = (String) firstRow[1];
        String role = (String) firstRow[2];
        
        return new JoinedUserInfoAccount(returnedUsername, password, role);
    }
    
    @Query(value = GET_ALL_USER_AND_INFO)
    List<Object[]> findAllUserRaw() throws DataAccessException;
    
    default List<JoinedUserInfoAndAccount> findAllUser() {
    	List<Object[]> objList = findAllUserRaw();
    	
    	if(objList.isEmpty()) {
    		return null;  // Or throw an exception if appropriate
    	}
    	
    	List<JoinedUserInfoAndAccount> result = new ArrayList<>();
    	for(Object[] row : objList) {
    		result.add(new JoinedUserInfoAndAccount(row));
    	}
    	
    	return result;
    	
    }
    
    @Query(value = FIND_USER_AND_INFO_BY_ID)
    List<Object[]> findUserByUserAccountByIdRaw(Integer employeeIdQuery) throws DataAccessException;
    
    default List<JoinedUserInfoAndAccount> findUserByUserAccountById(Integer employeeIdQuery){
    	List<Object[]> objListFindUserEmployerId = findUserByUserAccountByIdRaw(employeeIdQuery);
    	
    	if(objListFindUserEmployerId.isEmpty()) {
    		return null;
    	}
    	
    	List<JoinedUserInfoAndAccount> result = new ArrayList<>();
    	for(Object[] row : objListFindUserEmployerId) {
    		result.add(new JoinedUserInfoAndAccount(row));
    	}
    	
    	return result;
    }
}
