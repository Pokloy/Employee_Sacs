package com.Employee_Sacs.app.model.obj;

import lombok.Data;

@Data
public class TaskObj {
	private int task_id;
	private int creator_id;
	private String creatorName;
	private int assign_id;
	private String assigneeName;
	private String taskName;
	private String dateStart;
	private String dateEnd;
	private String progress;
}
