package com.Employee_Sacs.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Employee_Sacs.app.controller.dto.AccountWebDto;
import com.Employee_Sacs.app.model.dao.UserDao;
import com.Employee_Sacs.app.model.dao.entity.JoinedUserInfoAccount;
import com.Employee_Sacs.app.model.dao.entity.JoinedUserInfoAndAccount;
import com.Employee_Sacs.app.model.dao.entity.UserAccountEntity;
import com.Employee_Sacs.app.model.dao.entity.UserInformationEntity;
import com.Employee_Sacs.app.model.dto.UserInfoAccountDto;
import com.Employee_Sacs.app.model.dto.UserInfoInOutDto;
import com.Employee_Sacs.app.model.logic.UserAccountLogic;
import com.Employee_Sacs.app.model.obj.UserInfoAccObj;
import com.Employee_Sacs.app.model.service.LoggedInUserService;

@Controller
@Scope("prototype")
public class UserController {
	
	@Autowired
	LoggedInUserService loggedInUserService;
	
	@Autowired
	UserAccountLogic userAccLogic;

	@Autowired
	UserDao userDao;
	 
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	HomeController homeController;
	
	@GetMapping("/Admin/users/home")
	public String userListHome(Model model, 
			@ModelAttribute @Validated AccountWebDto accountWebDto,
			@RequestParam(name="employee_Id", required=false) Integer employeeId) {
		homeController.currentUser(model);
		UserInfoAccountDto UIA = loggedInUserService.findAllUsersAccountWithName();
		AccountWebDto accountWithNameWebDto = new AccountWebDto();
		accountWithNameWebDto.setUserInfoAccObj(UIA.getUserInfoAccObj());
		model.addAttribute("accWebDto", accountWithNameWebDto);
		return "/users/userList";
	}
	@GetMapping("/Admin/users/add")
	public String userAddGet(Model model, AccountWebDto accountWebDto) {
		homeController.currentUser(model);
		return "/users/userAdd";
	}
	
	@PostMapping("/Admin/users/add")
	public String userAddPost(Model model, AccountWebDto accountWebDto) {
		UserInfoAccountDto newUserAcc = new UserInfoAccountDto();
		UserInfoInOutDto newUserInfo = new UserInfoInOutDto();
		
		newUserAcc.setUserName(accountWebDto.getUsername());
		newUserAcc.setPassword(accountWebDto.getPassword());
		newUserInfo.setFirstname(accountWebDto.getFirstname());
		newUserInfo.setLastname(accountWebDto.getLastname());
		newUserInfo.setMiddlename(accountWebDto.getMiddlename());
		newUserInfo.setEmail(accountWebDto.getEmail());
		newUserInfo.setAddress(accountWebDto.getAddress());
		newUserInfo.setMobileNumber(accountWebDto.getMobileNumber());
		newUserInfo.setPosition(accountWebDto.getPosition());
		newUserInfo.setRole(accountWebDto.getRole());
		loggedInUserService.addNewUser(newUserAcc, newUserInfo);
		
		return "redirect:/Admin/users/home";
	}
	
	@GetMapping("/Admin/users/edit")
	public String userEditGet(Model model,
			@ModelAttribute @Validated AccountWebDto accountWebDto,
			@RequestParam(name="employee_Id", required=false) Integer employeeId) {
		homeController.currentUser(model);
		UserInfoInOutDto userInfoIo = loggedInUserService.findUserById(employeeId);
		accountWebDto.setUserInfoAccObjList(userInfoIo.getUserInfoAccObjList());
		return "/users/userEdit";
	}
	
	@PostMapping("/Admin/users/edit")
	public String userEditPost(Model model,
			@ModelAttribute @Validated AccountWebDto accountWebDto,
			@RequestParam(name="employee_Id", required=false) Integer employeeId) {
		UserInformationEntity userInfoEntity = new UserInformationEntity();
		userInfoEntity.setEmployeeId(employeeId);
		userInfoEntity.setFirstname(accountWebDto.getFirstname());
		userInfoEntity.setLastname(accountWebDto.getLastname());
		userInfoEntity.setMiddlename(accountWebDto.getMiddlename());
		userInfoEntity.setAddress(accountWebDto.getAddress());
		userInfoEntity.setEmail(accountWebDto.getEmail());
		userInfoEntity.setMobileNumber(accountWebDto.getMobileNumber());
		userInfoEntity.setPosition(accountWebDto.getPosition());
		userInfoEntity.setRole(accountWebDto.getRole());
		userAccLogic.updateUserInfo(userInfoEntity);
		
		UserAccountEntity userAccountEntity = new UserAccountEntity();
		userAccountEntity.setEmployeeId(employeeId); 
		userAccountEntity.setPassword(encoder.encode(accountWebDto.getPassword()));
		userAccountEntity.setUsername(accountWebDto.getUsername());
		userAccLogic.updateUserAcc(userAccountEntity);
		return "redirect:/Admin/users/home";
	}
	
	
	@PostMapping("/Admin/users/delete")
	public String userDelete(Model model,
			@ModelAttribute @Validated AccountWebDto accountWebDto,
			@RequestParam(name="employee_Id", required=false) Integer employeeId) {
		loggedInUserService.deleteUser(employeeId);
		return "redirect:/Admin/users/home";
	}
}
