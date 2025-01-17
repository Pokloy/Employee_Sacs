package com.Employee_Sacs.app.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinedDailyPay {
	public JoinedDailyPay(Object[] objects) {
		 this(
				 (String)objects[0],
				 (String)objects[1],
				 (int)objects[2],
				 (int)objects[3],
				 (String)objects[4],
				 (String)objects[5],
				 (String)objects[6],
				 (boolean)objects[7],
				 (String)objects[8]
		      );
	}
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
