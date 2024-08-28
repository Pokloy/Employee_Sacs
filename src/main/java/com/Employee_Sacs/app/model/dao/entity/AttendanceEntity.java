package com.Employee_Sacs.app.model.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_attendance")
public class AttendanceEntity {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int attendance_id;
	
	private int employee_id;
	
	private String clockin;
	
	private String breakin;
	
	private String breakout;
	
	private String clockout;
	
	private String attendancehours;
	
	private String status;
	
	private String date;
	
	private String breakhours;
	
	private double latehours;
	
	private double overtime;
}
