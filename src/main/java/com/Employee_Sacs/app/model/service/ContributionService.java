package com.Employee_Sacs.app.model.service;

import com.Employee_Sacs.app.model.dto.ContributionInOutDto;
import com.Employee_Sacs.app.model.dto.UserInfoInOutDto;

public interface ContributionService {
	public ContributionInOutDto getAllContributeEmployeeId();
	
	public ContributionInOutDto getSpecificContributionByLoginUser(); 
	
	public ContributionInOutDto getSpecificContributionByContributionId(int contributionID);
	
	public ContributionInOutDto getAllContribution();
	
	public void updateSpecificContribution(int contributionID, 
			String contributionName, 
			String contributionValue, 
			String minimum, 
			String maximum);
	
	public void deleteSpecificContribution(int contributionID);
	
	public ContributionInOutDto getContributionByEmployeeId(int employeeId);
	

}
