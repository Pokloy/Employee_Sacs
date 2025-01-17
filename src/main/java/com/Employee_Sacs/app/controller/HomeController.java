package com.Employee_Sacs.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Employee_Sacs.app.model.dao.TaskDao;
import com.Employee_Sacs.app.model.dao.UserAccountDao;
import com.Employee_Sacs.app.model.logic.UserAccountLogic;
import com.Employee_Sacs.app.model.service.LoggedInUserService;

@Controller
public class HomeController {
	
	
	@Autowired
	UserAccountLogic userAccountLogic;
	
	@Autowired
	LoggedInUserService loggedInUserService;

	@Autowired
	TaskDao taskDao;
	
	@GetMapping("/testHeader")
	public String testHeader(){
		return "/layout/header";
	}
	
	@GetMapping("/testSideMenu")
	public String testSideMenu(){
		return "/layout/sidemenu";
	}
	
	@GetMapping("/User/home")
	public String userHome(Model model){
		currentUser(model);
		return "/home/userHomepage";
	}
	
	@GetMapping("/Admin/home")
	public String adminHome(Model model){
		currentUser(model);
		return "/home/adminHomepage";
	}
	
	@GetMapping("/form/confirm")
	public String taskTest() {
		return "/home/test";
	}
	
	public Model currentUser(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.isAuthenticated()) {
	        Object principal = authentication.getPrincipal();
	        String username;
	        if (principal instanceof UserDetails) {
	            username = ((UserDetails) principal).getUsername();
	        } else {
	            username = principal.toString();
	        }
	        model.addAttribute("username", username);
	    }
	    
	    return model;
	}
}
