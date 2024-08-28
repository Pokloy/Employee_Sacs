package com.Employee_Sacs.app.controller.dto;

import java.util.List;

import com.Employee_Sacs.app.model.obj.ContributionObj;
import com.Employee_Sacs.app.model.obj.UserInfoObj;

import lombok.Data;

@Data
public class ContributionWebDto {
	private List<ContributionObj> contributionObj;
	private List<UserInfoObj> userInfoObj;
	private String firstName;
	private String lastName;
	private int contribution_id;
	private int employee_id;
	private String contribution_name;
	private String contribute_value;
	private String minimum;
	private String maximum;
}
