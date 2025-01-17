package com.Employee_Sacs.app.model.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_dailypay")
public class DailyPayEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dailypay_id;
	
	private int attendance_id;
	
	private String regulardaily;
	
	private String overtimedaily;
	
	private String latedaily;
	
	private boolean status;
}
