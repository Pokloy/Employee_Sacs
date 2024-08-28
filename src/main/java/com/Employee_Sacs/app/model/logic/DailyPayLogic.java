package com.Employee_Sacs.app.model.logic;

import java.util.List;

import com.Employee_Sacs.app.model.dao.entity.DailyPayEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedDailyPay;

public interface DailyPayLogic {
	public void insertDailyPay(DailyPayEntity dailyPayEntity);
	
	public List<JoinedDailyPay> getDailyPayWithName(int employeeId);
}
