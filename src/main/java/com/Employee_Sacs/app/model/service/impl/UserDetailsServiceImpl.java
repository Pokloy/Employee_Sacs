package com.Employee_Sacs.app.model.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.UserDao;
import com.Employee_Sacs.app.model.dao.entity.JoinedUserInfoAccount;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		JoinedUserInfoAccount userInformation = userDao.findUserInfoAndAccountByUsername(username);

	    System.out.println("User: " + userInformation.getUsername());
	    System.out.println("Roles: " + userInformation.getRole());
		return new User(userInformation.getUsername(), 
				userInformation.getPassword(), 
				AuthorityUtils.createAuthorityList(userInformation.getRole()));
	}

}
