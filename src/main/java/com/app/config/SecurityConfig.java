package com.app.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.app.filter.CsrfFilter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {
 
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//spring tạo ra CsrfToken
		//cần class này để hanlde CsrfToken
		CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
		requestAttributeHandler.setCsrfRequestAttributeName("_csrf");
		
		http.securityContext().requireExplicitSave(false)
			.and().sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.cors().configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration configuration = new CorsConfiguration();
					configuration.setAllowCredentials(true);
					configuration.setAllowedMethods(Collections.singletonList("*"));
					configuration.setAllowedHeaders(Collections.singletonList("*"));
					configuration.setAllowedMethods(Collections.singletonList("http://localhost:4200"));
					configuration.setMaxAge(3600L);
					return configuration;
				}
		}).and().csrf(csrf -> csrf.csrfTokenRequestHandler(requestAttributeHandler).ignoringRequestMatchers("/auth/**")
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				)
		.addFilterAfter(new CsrfFilter(), BasicAuthenticationFilter.class)
		.authorizeHttpRequests()
			.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
			.requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
			.requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
			.requestMatchers("/myCards").hasAuthority("VIEWCARDS")
			.requestMatchers("/user").authenticated()
			.requestMatchers("/notices", "/contact", "/auth/**").permitAll()
			.and().formLogin()
			.and().httpBasic();
		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource); //su dung jdbc de load tu db len
//	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() { 
//		return NoOpPasswordEncoder.getInstance(); //default, so sanh pwd mac dinh
//	}
	
	//tạo bean để mặc định provider có tên DaoAuthenticationProvider dùng
	// hoặc một provider nào muốn dùng
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
