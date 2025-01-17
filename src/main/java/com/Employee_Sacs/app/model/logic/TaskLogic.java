package com.Employee_Sacs.app.model.logic;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.entity.JoinedTaskUserInfo;
import com.Employee_Sacs.app.model.dao.entity.TaskEntity;

public interface TaskLogic {

	List<TaskEntity> TaskList();
	
	public void TaskAdd(TaskEntity taskEntity);
	
	public TaskEntity TaskById(int taskId);
	
	public List<JoinedTaskUserInfo> getAllTaskWithNames();
	
	public void deleteTaskById(int taskId);
	
	public void TaskUpdateById(TaskEntity saveTaskEntity);
	
	public List<JoinedTaskUserInfo> getSpecificTaskByAssignId(int assignId);
}
