package com.Employee_Sacs.app.login;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String login(Model model, @RequestParam(value = "error", required = false) String error) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String userName = authentication.getName();
	    String role = "";

	    // Handle authenticated users
	    if (!userName.equals("anonymousUser")) {
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        for (GrantedAuthority authority : authorities) {
	            role = authority.getAuthority();
	        }
	        if (role.equals("admin")) {
	            return "/home/adminHomepage";
	        } else if (role.equals("user")) {
	            return "/home/userHomepage";
	        } else {
	            return "/login/login";
	        }
	    }

	    // Handle unauthenticated users
	    if (error != null) {
	        model.addAttribute("message", "Invalid username or password.");
	    }
	    return "/login/login";
	}

	
	
	@GetMapping("/logout")
	public String logout(){
		
		SecurityContextHolder.clearContext();
		httpSession.invalidate();
		return "/login/login";
	}
	
}
