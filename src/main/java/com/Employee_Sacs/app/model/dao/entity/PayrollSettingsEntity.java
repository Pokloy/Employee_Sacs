package com.Employee_Sacs.app.model.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_payroll_settings")
public class PayrollSettingsEntity {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int s_payroll_id;
	
	private int employee_id;
	
	private double month_days;
	
	private double non_workingdays;
	
	private double salary;
	
	private boolean active;
	
	private String date;
}
