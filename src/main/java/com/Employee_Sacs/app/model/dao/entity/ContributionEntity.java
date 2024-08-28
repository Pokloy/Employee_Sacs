package com.Employee_Sacs.app.model.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_contribution")
public class ContributionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contribution_id;
	
	private int employee_id;
	
	private String contribution_name;
	
	private String contribute_value;
	
	private String minimum;
	
	private String maximum;
}
