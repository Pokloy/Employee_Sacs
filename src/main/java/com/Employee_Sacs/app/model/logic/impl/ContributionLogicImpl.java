package com.Employee_Sacs.app.model.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.ContributionDao;
import com.Employee_Sacs.app.model.dao.entity.ContributionEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedContributionUserInfo;
import com.Employee_Sacs.app.model.logic.ContributionLogic;

@Service
public class ContributionLogicImpl implements ContributionLogic {
	@Autowired
	ContributionDao contributionDao;
	
	public List<ContributionEntity> getAllContributeByEmployeeId(int employeeId){
		List<ContributionEntity> entityList = contributionDao.getAllContributionByEmployeeId(employeeId);
		return entityList;
	}
	
	public ContributionEntity getSpecificContribution(int contributionID) {
		ContributionEntity entity = contributionDao.getSpecificContribution(contributionID);
		return entity;
	}
	
	public List<JoinedContributionUserInfo> getContributionEntity(){
		List<JoinedContributionUserInfo> result = contributionDao.getAllContribution();
		return result;
	}
	
	public void updateSpecificContributionByContributionId(int contributionID, 
			String contributionName, 
			String contributionValue, 
			String minimum, 
			String maximum) {		
		contributionDao.updateContributionById(contributionID, 
				contributionName , 
				contributionValue, 
				minimum, 
				maximum);
	}
	
	public void  deleteContributionByContributionId(int contributionID) {
		contributionDao.deleteById(contributionID);
	}
	
	public void addContribution(ContributionEntity contriEntity) {
		contributionDao.saveAndFlush(contriEntity);
	}
	
}
