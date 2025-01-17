package com.Employee_Sacs.app.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.entity.ContributionEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedContributionUserInfo;
import com.Employee_Sacs.app.model.dao.entity.UserInformationEntity;
import com.Employee_Sacs.app.model.dto.ContributionInOutDto;
import com.Employee_Sacs.app.model.dto.UserInfoInOutDto;
import com.Employee_Sacs.app.model.logic.ContributionLogic;
import com.Employee_Sacs.app.model.logic.UserAccountLogic;
import com.Employee_Sacs.app.model.obj.ContributionObj;
import com.Employee_Sacs.app.model.obj.UserInfoObj;
import com.Employee_Sacs.app.model.service.AttendanceService;
import com.Employee_Sacs.app.model.service.ContributionService;
import com.Employee_Sacs.app.model.service.LoggedInUserService;

@Service
public class ContributionServiceImpl implements ContributionService {
	@Autowired
	ContributionLogic contributionLogic;
	
	@Autowired
	LoggedInUserService loggedInUserService;
	
	@Autowired
	UserAccountLogic userAccountLogic;
	
	@Override
	public ContributionInOutDto getAllContributeEmployeeId() {
		ContributionInOutDto contributeIO = new ContributionInOutDto();

		List<ContributionEntity> contriList = contributionLogic.getAllContributeByEmployeeId(loggedInUserService.getEmployeeId());
		
		List<ContributionObj> contributeObj = new ArrayList<>(); 
		
		if(contriList != null && !contriList.isEmpty()) {
			for(ContributionEntity contriEntity :  contriList) {
				ContributionObj contributionObj = new ContributionObj();
				contributionObj.setEmployee_id(contriEntity.getEmployee_id());
				contributionObj.setContribution_id(contriEntity.getContribution_id());
				contributionObj.setContribution_name(contriEntity.getContribution_name());
				contributionObj.setContribute_value(contriEntity.getContribute_value());
				contributionObj.setMinimum(contriEntity.getMinimum());
				contributionObj.setMaximum(contriEntity.getMaximum());
				
				contributeObj.add(contributionObj);
			}
			contributeIO.setContributionObj(contributeObj);
		}
 		
		return contributeIO;
		
		
	}
	
	@Override
	public ContributionInOutDto getContributionByEmployeeId(int employeeId) {
		ContributionInOutDto contributeIO = new ContributionInOutDto();
		
		List<ContributionEntity> contriList = contributionLogic.getAllContributeByEmployeeId(employeeId);
		
		List<ContributionObj> contributeObj = new ArrayList<>();
		
		if(contriList != null && !contriList.isEmpty()) {
			for(ContributionEntity contriEntity :  contriList) {
				ContributionObj contributionObj = new ContributionObj();
				contributionObj.setEmployee_id(contriEntity.getEmployee_id());
				contributionObj.setContribution_id(contriEntity.getContribution_id());
				contributionObj.setContribution_name(contriEntity.getContribution_name());
				contributionObj.setContribute_value(contriEntity.getContribute_value());
				contributionObj.setMinimum(contriEntity.getMinimum());
				contributionObj.setMaximum(contriEntity.getMaximum());
				
				contributeObj.add(contributionObj);
			}
			contributeIO.setContributionObj(contributeObj);
		}
		return contributeIO;
	}
	
	@Override
	public ContributionInOutDto getSpecificContributionByLoginUser() {
		ContributionInOutDto contributeIO = new ContributionInOutDto();
		ContributionEntity entity = contributionLogic.getSpecificContribution(loggedInUserService.getEmployeeId());
		contributeIO.setContribution_id(entity.getContribution_id());
		contributeIO.setEmployee_id(entity.getEmployee_id());
		contributeIO.setContribution_name(entity.getContribution_name());
		contributeIO.setContribute_value(entity.getContribute_value());
		contributeIO.setMinimum(entity.getMinimum());
		contributeIO.setMaximum(entity.getMaximum());
		return contributeIO;
	}
	
	@Override
	public ContributionInOutDto getSpecificContributionByContributionId(int contributionID) {
		ContributionInOutDto contributeIO = new ContributionInOutDto();
		ContributionEntity entity = contributionLogic.getSpecificContribution(contributionID);
		contributeIO.setContribution_id(entity.getContribution_id());
		contributeIO.setEmployee_id(entity.getEmployee_id());
		contributeIO.setContribution_name(entity.getContribution_name());
		contributeIO.setContribute_value(entity.getContribute_value());
		contributeIO.setMinimum(entity.getMinimum());
		contributeIO.setMaximum(entity.getMaximum());
		return contributeIO;
	}
	
	@Override
	public ContributionInOutDto getAllContribution() {
		ContributionInOutDto contributeIO = new ContributionInOutDto();
		List<JoinedContributionUserInfo> contributionList = contributionLogic.getContributionEntity();
		List<ContributionObj> contributeObj = new ArrayList<>(); 
		
		if(contributionList != null && !contributionList.isEmpty()) {
			for(JoinedContributionUserInfo contriEntity :  contributionList) {
				ContributionObj contriObj = new ContributionObj();
				contriObj.setFirstName(contriEntity.getFirstName());
				contriObj.setLastName(contriEntity.getLastName());
				contriObj.setContribution_id(contriEntity.getContribution_id());
				contriObj.setEmployee_id(contriEntity.getEmployee_id());
				contriObj.setContribution_name(contriEntity.getContribution_name());
				contriObj.setContribute_value(contriEntity.getContribute_value());
				contriObj.setMinimum(contriEntity.getMinimum());
				contriObj.setMaximum(contriEntity.getMaximum());
				
				contributeObj.add(contriObj);
			}
			contributeIO.setContributionObj(contributeObj);
		}
		return contributeIO;
	}
	
	@Override
	public void updateSpecificContribution(int contributionID, 
			String contributionName, 
			String contributionValue, 
			String minimum, 
			String maximum) {
		contributionLogic.updateSpecificContributionByContributionId(contributionID, 
				contributionName, 
				contributionValue, 
				minimum, 
				maximum);
		}
	
	@Override
	public void deleteSpecificContribution(int contributionID) {
		contributionLogic.deleteContributionByContributionId(contributionID);
	}

}
