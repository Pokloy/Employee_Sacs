package com.Employee_Sacs.app.model.dto;

import java.util.List;

import com.Employee_Sacs.app.model.obj.TaskObj;

import lombok.Data;

@Data
public class TaskInOutDto {
	private List<TaskObj> taskObj;
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
