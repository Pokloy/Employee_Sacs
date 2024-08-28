package com.Employee_Sacs.app.model.service;

import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dto.TaskInOutDto;

public interface TaskService {
	public TaskInOutDto getAlltask();
	
	public void addTask(int assignId, String taskName, String dateStarted, String dateEnded, String progresss);
	
	public TaskInOutDto getTaskById(int taskid);
	
	public void deleteTaskById(int taskId);

	public void updateTask(TaskInOutDto taskIO);
	
	public TaskInOutDto getSpecificTaskByAssignId();
}
