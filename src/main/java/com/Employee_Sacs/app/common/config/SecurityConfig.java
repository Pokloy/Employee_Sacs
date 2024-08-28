package com.Employee_Sacs.app.common.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.Employee_Sacs.app.model.service.impl.UserDetailsServiceImpl;



@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

	/* Encodes hashed codes from data sources. */
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
	/*
	 * Athentication Provider Stores user details for authentication and principal(session).
	 */
    @Bean
    protected AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    
	/*
	 * Authencation Success Handler which handle the behavior of spring security if
	 * the form based log-in is successful. (This part of method is very modifiable. You can customize the behavior of your authentication)
	 */
    @Bean
    protected AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("user")) {
                    response.sendRedirect("/User/home");
                    return;
                } else if (authority.getAuthority().equals("admin")) {
                    response.sendRedirect("/Admin/home");
                    return;
                }
            }
            response.sendRedirect("/login");
        };
    }

	/*
	 * Security Filter Chain which manages the authorization and the authentication
	 * behavior of spring security.
	 */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/login", "/css/**", "/js/**", "/favicon.ico", "/icons/**", "/images/**", "/fonts/**").permitAll()
                .requestMatchers("/home").authenticated()
                .requestMatchers("/Admin/**").hasAuthority("admin")
                .requestMatchers("/User/**").hasAuthority("user")
                .requestMatchers("/visitor/**").hasAuthority("visitor")
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll()
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler()) 
            )
            .logout((logout) -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout=true")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll()
                );
        return http.build();
    }

}
