package com.Employee_Sacs.app.model.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.TaskDao;
import com.Employee_Sacs.app.model.dao.entity.JoinedTaskUserInfo;
import com.Employee_Sacs.app.model.dao.entity.TaskEntity;
import com.Employee_Sacs.app.model.logic.TaskLogic;

@Service
public class TaskLogicImpl implements TaskLogic {

	@Autowired
	private TaskDao taskDao;

	@Override
	public List<TaskEntity> TaskList() {
		return taskDao.viewAllTask();
	}

	@Override
	public void TaskAdd(TaskEntity taskEntity) {
		taskDao.save(taskEntity);
	}

	@Override
	public TaskEntity TaskById(int taskId) {
		return taskDao.getReferenceById(taskId);
	}

	@Override
	public List<JoinedTaskUserInfo> getAllTaskWithNames(){
		return taskDao.getAllTaskWithNames();
	}
	
	@Override
	public void deleteTaskById(int taskId) {
		taskDao.deleteById(taskId);
	}
	@Override 
	public void TaskUpdateById(TaskEntity saveTaskEntity) { 
		taskDao.updateTaskById(saveTaskEntity.getTask_id(), 
				saveTaskEntity.getTaskname(), 
				saveTaskEntity.getDatestart(), 
				saveTaskEntity.getDateend(), 
				saveTaskEntity.getProgress());
	}
	@Override
	public List<JoinedTaskUserInfo> getSpecificTaskByAssignId(int assignId){
		return taskDao.getSpecificTask(assignId);
	}
}
