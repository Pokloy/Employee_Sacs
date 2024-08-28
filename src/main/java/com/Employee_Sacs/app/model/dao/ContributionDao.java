package com.Employee_Sacs.app.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Employee_Sacs.app.model.dao.entity.ContributionEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedContributionUserInfo;

import jakarta.transaction.Transactional;

@Repository
public interface ContributionDao extends JpaRepository<ContributionEntity, Integer>  {
	final String VIEW_ALL_CONTRIBUTION_BY_EMPLOYEE_ID = "SELECT e FROM ContributionEntity e WHERE employee_id = :employeeID";
	
	final String FIND_CONTRIBUTION_BY_CONTRIBUTION_ID =" SELECT e FROM ContributionEntity e WHERE contribution_id = :contributionID ";
	
	final String UPDATE_CONTRIBUTION_CONTRIBUTION_ID = " UPDATE ContributionEntity "
			+ " SET contribution_name = :contributionName, "
			+ " contribute_value = :contributionValue, "
			+ " minimum = :minimum, "
			+ " maximum = :maximum "
			+ " WHERE contribution_id = :contributionId ";
	
	final String VIEW_ALL_CONTRIBUTION_WITH_NAME = " SELECT te.firstname, te.lastname,"
			+ " ce.contribution_id, ce.employee_id, ce.contribution_name,"
			+ " ce.contribute_value, ce.minimum, ce.maximum "
			+ " FROM UserInformationEntity te "
			+ " INNER JOIN ContributionEntity ce ON te.employeeId = ce.employee_id ";

	@Query(value = VIEW_ALL_CONTRIBUTION_BY_EMPLOYEE_ID)
	public List<ContributionEntity> getAllContributionByEmployeeId(int employeeID);
	
	@Query(value = FIND_CONTRIBUTION_BY_CONTRIBUTION_ID)
	public ContributionEntity getSpecificContribution(int contributionID);
	
    
    @Transactional
    @Modifying
    @Query(value = UPDATE_CONTRIBUTION_CONTRIBUTION_ID)
    public void updateContributionById(int contributionId, 
    		String contributionName, 
    		String contributionValue, 
    		String minimum,
    		String maximum);
    
    @Query(value = VIEW_ALL_CONTRIBUTION_WITH_NAME)
    List<Object[]>  getAllContributionRaw();
    
    default List<JoinedContributionUserInfo> getAllContribution(){
    	List<Object[]> contributionObj = getAllContributionRaw();
    	
    	if(contributionObj.isEmpty()) {
    		return null;
    	}
    	
    	List<JoinedContributionUserInfo> result = new ArrayList();
    	for (Object[] row: contributionObj) {
    		result.add(new JoinedContributionUserInfo(row));
    	}
    	
    	return result;
    }
}
