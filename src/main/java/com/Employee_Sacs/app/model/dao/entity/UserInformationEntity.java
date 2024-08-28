package com.Employee_Sacs.app.model.dao.entity;

import java.sql.Timestamp;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_employee_info")
public class UserInformationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;
	
	private String firstname;
	
	private String lastname;
	
	private String middlename;
	
	private String address;
	
	private String email;
	
	private String role;
	
	private String mobileNumber;
	
	private String position;
	
}
