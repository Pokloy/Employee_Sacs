package com.Employee_Sacs.app.model.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_leave")
public class LeaveEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int leave_id;
	
	private int creator_id;
	
	private String leave_reason;
	
	private String start_date;
	
	private String end_date;
	
	private String status;
}
