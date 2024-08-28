package com.Employee_Sacs.app.login;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.Employee_Sacs.app.model.dao.UserDao;

import jakarta.servlet.http.HttpSession;

@Controller
@Scope("prototype")
public class LoginController {
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	
	@Autowired
	UserDao userDao;
	
	
	@GetMapping("/login")
	public String login(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		String role = "";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
        	role = authority.getAuthority();
        }
        
		if(userName.equals("anonymousUser")) {
			return "/login/login";
		} else {
			if(role.equals("admin")) {
				return "/home/adminHomepage";
			} else if (role.equals("user")) {
				return "/home/userHomepage";
			} else {
				return "/login/login";
			}
		} 
	}
	
	
	@GetMapping("/logout")
	public String logout(){
		
		SecurityContextHolder.clearContext();
		httpSession.invalidate();
		return "/login/login";
	}
	
}
