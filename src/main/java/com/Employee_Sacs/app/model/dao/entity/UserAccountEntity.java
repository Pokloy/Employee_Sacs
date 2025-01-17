package com.Employee_Sacs.app.model.dao.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_employee_account")
public class UserAccountEntity {
	@Id	
	private int employeeId;
	
	private String password;
	
	private String username;
	
}
