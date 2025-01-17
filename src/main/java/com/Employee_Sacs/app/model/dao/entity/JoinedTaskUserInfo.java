package com.Employee_Sacs.app.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinedTaskUserInfo {
	public JoinedTaskUserInfo(Object[]objects) {
		this(	
				(Integer) objects[0],
				(Integer) objects[1],
				(String) objects[2],
				(Integer) objects[3],
				(String) objects[4],
				(String) objects[5],
				(String) objects[6],
				(String) objects[7],
				(String) objects[8]
			   );
	}
	
	private int task_id;
	
	private int creator_id;
	
	private String creatorName;
	
	private int assign_id;
	
	private String assigneeName;
	
	private String taskname;
	
	private String datestart;
	 
	private String dateend;
	
	private String progress;
}
