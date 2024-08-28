package com.Employee_Sacs.app.model.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_task")
public class TaskEntity {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int task_id;
	
	private int creator_id;
	
	private int assign_id;
	
	private String taskname;
	
	private String datestart;
	 
	private String dateend;
	
	private String progress;
}
