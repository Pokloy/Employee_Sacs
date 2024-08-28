package com.Employee_Sacs.app.model.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public interface UserDetailsService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
