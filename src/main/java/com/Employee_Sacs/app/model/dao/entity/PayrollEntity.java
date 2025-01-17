package com.Employee_Sacs.app.model.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_payroll")
public class PayrollEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int payroll_id;
	
	private int employee_id;
	
	private int s_payroll_id;
	
	private double bonus;
	
	private double holiday;
	
	private double allowance;
	
	private double overtime;
	
	private double adjustments;
	
	private double absences;
	
	private double undertime;
	
	private double gross_pay;
	
	private double net_pay;
	
	private double numbers_day;
	
	private String date_cover_start;
	
	private String date_cover_end;
	
	private String date_produced;
	
	private double total_regular_pay;
	
	private double total_deduction;
}
