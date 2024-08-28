package com.Employee_Sacs.app.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.entity.JoinedTaskUserInfo;
import com.Employee_Sacs.app.model.dao.entity.TaskEntity;
import com.Employee_Sacs.app.model.dto.TaskInOutDto;
import com.Employee_Sacs.app.model.logic.TaskLogic;
import com.Employee_Sacs.app.model.obj.TaskObj;
import com.Employee_Sacs.app.model.service.LoggedInUserService;
import com.Employee_Sacs.app.model.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskLogic taskLogic;
	
	@Autowired
	UserDetailsService userDetails;
	
	@Autowired
	LoggedInUserService loggedInUserService;
	
	@Override
	public TaskInOutDto getSpecificTaskByAssignId() {
		TaskInOutDto taskIO = new TaskInOutDto();
		List<JoinedTaskUserInfo> getTaskByAssignId = taskLogic.getSpecificTaskByAssignId(loggedInUserService.getEmployeeId());
		List<TaskObj> taskObjList = new ArrayList<>();
		
		if(getTaskByAssignId != null && !getTaskByAssignId.isEmpty()) {
			for(JoinedTaskUserInfo taskEntity : getTaskByAssignId) {
				TaskObj taskObj = new TaskObj();
				taskObj.setTask_id(taskEntity.getTask_id());
				taskObj.setCreator_id(taskEntity.getCreator_id());
				taskObj.setCreatorName(taskEntity.getCreatorName());
				taskObj.setAssign_id(taskEntity.getAssign_id());
				taskObj.setAssigneeName(taskEntity.getAssigneeName());
				taskObj.setTaskName(taskEntity.getTaskname());
				taskObj.setDateStart(taskEntity.getDatestart());
				taskObj.setDateEnd(taskEntity.getDateend());
				taskObj.setProgress(taskEntity.getProgress());
				taskObjList.add(taskObj);
			}
			taskIO.setTaskObj(taskObjList);
		}
		return taskIO;
	}
	
	@Override
	public TaskInOutDto getAlltask() {
		TaskInOutDto taskInOutDto = new TaskInOutDto();
		
		List<JoinedTaskUserInfo> taskListwithName = taskLogic.getAllTaskWithNames();

		List<TaskObj> taskObjList = new ArrayList<>();
		
		if(taskListwithName != null && !taskListwithName.isEmpty()) {
			for(JoinedTaskUserInfo taskEntity : taskListwithName) {
				TaskObj taskObj = new TaskObj();
				taskObj.setTask_id(taskEntity.getTask_id());
				taskObj.setCreator_id(taskEntity.getCreator_id());
				taskObj.setCreatorName(taskEntity.getCreatorName());
				taskObj.setAssign_id(taskEntity.getAssign_id());
				taskObj.setAssigneeName(taskEntity.getAssigneeName());
				taskObj.setTaskName(taskEntity.getTaskname());
				taskObj.setDateStart(taskEntity.getDatestart());
				taskObj.setDateEnd(taskEntity.getDateend());
				taskObj.setProgress(taskEntity.getProgress());
				taskObjList.add(taskObj);
			}
			taskInOutDto.setTaskObj(taskObjList);
		}
		
		return taskInOutDto;
	}
	
	
	@Override
	public void addTask(int assignId, String taskName, String dateStarted, String dateEnded, String progresss) {
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setCreator_id(loggedInUserService.getEmployeeId());
		taskEntity.setAssign_id(assignId);
		taskEntity.setTaskname(taskName);
		taskEntity.setDatestart(dateStarted);
		taskEntity.setDateend(dateEnded);
		taskEntity.setProgress(progresss);
		taskLogic.TaskAdd(taskEntity);
	}
	
	
	@Override
	public TaskInOutDto getTaskById(int taskid) {
		TaskInOutDto taskIO = new TaskInOutDto();
		TaskEntity taskById = taskLogic.TaskById(taskid);
		taskIO.setTask_id(taskById.getTask_id());
		taskIO.setTaskName(taskById.getTaskname());
		taskIO.setDateStart(taskById.getDatestart());
		taskIO.setDateEnd(taskById.getDateend());
		taskIO.setProgress(taskById.getProgress());
		return taskIO;
	}
	
	@Override
	public void deleteTaskById(int taskId) {
		taskLogic.deleteTaskById(taskId);
	}
	
	@Override
	public void updateTask(TaskInOutDto taskIO) {
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTask_id(taskIO.getTask_id());
		taskEntity.setTaskname(taskIO.getTaskName());
		taskEntity.setDatestart(taskIO.getDateStart());
		taskEntity.setDateend(taskIO.getDateEnd());
		taskEntity.setProgress(taskIO.getProgress());
		taskLogic.TaskUpdateById(taskEntity);
	}
}
