package com.Employee_Sacs.app.model.logic;

import java.util.List;

import com.Employee_Sacs.app.model.dao.entity.ContributionEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedContributionUserInfo;



public interface ContributionLogic {
	public List<ContributionEntity> getAllContributeByEmployeeId(int employeeId);

	public ContributionEntity getSpecificContribution(int contributionID);
	
	public List<JoinedContributionUserInfo> getContributionEntity();
	
	public void updateSpecificContributionByContributionId(
			int contributionID, 
			String contributionName, 
			String contributionValue, 
			String minimum, 
			String maximum);
	
	public void  deleteContributionByContributionId(int contributionID);
	
	public void addContribution(ContributionEntity contriEntity);
}
