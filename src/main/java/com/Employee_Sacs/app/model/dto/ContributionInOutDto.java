package com.Employee_Sacs.app.model.dto;

import java.util.List;

import com.Employee_Sacs.app.model.obj.ContributionObj;

import lombok.Data;

@Data
public class ContributionInOutDto {
	private List<ContributionObj> contributionObj;
	private String firstName;
	private String lastName;
	private int contribution_id;
	private int employee_id;
	private String contribution_name;
	private String contribute_value;
	private String minimum;
	private String maximum;
}
