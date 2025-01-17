package com.Employee_Sacs.app.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Employee_Sacs.app.model.dao.entity.PayrollEntity;

public interface PayrollDao extends JpaRepository<PayrollEntity, Integer> {
	final String FIND_PAYROLL_BY_ID = " SELECT e "
			+ " FROM PayrollEntity e "
			+ " WHERE payroll_id = :payrollID ";
	
	@Query(value = FIND_PAYROLL_BY_ID)
	public PayrollEntity getpayrollById(int payrollID);
}
