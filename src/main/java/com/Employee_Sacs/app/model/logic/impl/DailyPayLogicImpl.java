package com.Employee_Sacs.app.model.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.DailyPayDao;
import com.Employee_Sacs.app.model.dao.entity.DailyPayEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedDailyPay;
import com.Employee_Sacs.app.model.logic.DailyPayLogic;

@Service
public class DailyPayLogicImpl implements DailyPayLogic {
	
	@Autowired
	DailyPayDao dailyPayDao;
	
	
	public void insertDailyPay(DailyPayEntity dailyPayEntity) {
		dailyPayDao.saveAndFlush(dailyPayEntity);
	}
	
	
	public List<JoinedDailyPay> getDailyPayWithName(int employeeId){
		return dailyPayDao.getDailyPayWithName(employeeId);
	}
}
