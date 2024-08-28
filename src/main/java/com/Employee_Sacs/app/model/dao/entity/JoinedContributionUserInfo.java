package com.Employee_Sacs.app.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinedContributionUserInfo {
	public JoinedContributionUserInfo(Object[] objects) {
		this(
				(String)objects[0],
				(String)objects[1],
				(Integer)objects[2],
				(Integer)objects[3],
				(String)objects[4],
				(String)objects[5],
				(String)objects[6],
				(String)objects[7]
				);
		}
		private String firstName;
		
		private String lastName;
		
		private int contribution_id;
		
		private int employee_id;
		
		private String contribution_name;
		
		private String contribute_value;
		
		private String minimum;
		
		private String maximum;
}
