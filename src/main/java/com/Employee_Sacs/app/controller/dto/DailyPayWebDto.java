package com.Employee_Sacs.app.controller.dto;

import java.util.List;

import com.Employee_Sacs.app.model.dto.DailyInOutDto;
import com.Employee_Sacs.app.model.obj.DailyPayObj;

import lombok.Data;

@Data
public class DailyPayWebDto {
	private List<DailyPayObj> dailyPayObj;
	
	private String firstName;
	
	private String lastName;
	
	private int dailypay_id;
	
	private int attendance_id;
	
	private String regulardaily;
	
	private String overtimedaily;
	
	private String latedaily;
	
	private boolean status;
	
	private String date;
}
