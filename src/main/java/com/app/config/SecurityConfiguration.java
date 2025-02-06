package com.app.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.app.filter.CsrfCookieFilter;
import com.app.filter.TokenAuthenticationFilter;
import com.app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
 
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		//spring tạo ra CsrfToken
//		//cần class này để hanlde CsrfToken
//		CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
//		requestAttributeHandler.setCsrfRequestAttributeName("_csrf");
//		
//		http.securityContext().requireExplicitSave(false)
//			.and().sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//			.cors().configurationSource(new CorsConfigurationSource() {
//				@Override
//				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//					CorsConfiguration configuration = new CorsConfiguration();
//					configuration.setAllowCredentials(true);
//					configuration.setAllowedMethods(Collections.singletonList("*"));
//					configuration.setAllowedHeaders(Collections.singletonList("*"));
//					configuration.setAllowedMethods(Collections.singletonList("http://localhost:4200"));
//					configuration.setMaxAge(3600L);
//					return configuration;
//				}
//		}).and().csrf(csrf -> csrf.csrfTokenRequestHandler(requestAttributeHandler).ignoringRequestMatchers("/auth/**")
//					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				)
//		.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//		.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
//		.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
//		.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
//		.authorizeHttpRequests()
//			.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//			.requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
//			.requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//			.requestMatchers("/myCards").hasAuthority("VIEWCARDS")
//			.requestMatchers("/user").authenticated()
//			.requestMatchers("/notices", "/contact", "/auth/**").permitAll()
//			.and().formLogin()
//			.and().httpBasic();
//		return http.build();
//	}
	
	//config using basic authentication
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		//spring tạo ra CsrfToken
//		//cần class này để hanlde CsrfToken
//		CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
//		requestAttributeHandler.setCsrfRequestAttributeName("_csrf");
//		
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // xử lý request độc lập, ko tạo ra seassion phù hợp vs rest api
//			.cors().configurationSource(new CorsConfigurationSource() {
//				@Override
//				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//					CorsConfiguration configuration = new CorsConfiguration();
//					configuration.setAllowCredentials(true);
//					configuration.setAllowedMethods(Collections.singletonList("*"));
//					configuration.setAllowedHeaders(Collections.singletonList("*"));
//					configuration.setAllowedMethods(Collections.singletonList("http://localhost:4200"));
//					configuration.setExposedHeaders(Arrays.asList("Authorization")); // bật để cho mỗi response về FE, FE có thể đọc được field này.
//					configuration.setMaxAge(3600L);
//					return configuration;
//				}
//		}).and().csrf(csrf -> csrf.csrfTokenRequestHandler(requestAttributeHandler).ignoringRequestMatchers("/auth/**")
//					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				)
//		.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//		.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
//		.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
//		.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
//        .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
//        .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//		.authorizeHttpRequests( (requests ) ->  requests
//			.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//			.requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
//			.requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//			.requestMatchers("/myCards").hasAuthority("VIEWCARDS")
//			.requestMatchers("/user").authenticated()
//			.requestMatchers("/notices", "/contact", "/auth/**").permitAll())
//		.formLogin(Customizer.withDefaults())
//		.httpBasic(Customizer.withDefaults());
//		return http.build();
//	}
	
	
	
//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource); //su dung jdbc de load tu db len
//	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() { 
//		return NoOpPasswordEncoder.getInstance(); //default, so sanh pwd mac dinh
//	}

	private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**", "/unsecured/v1/unsecure-example", "/notices", "/contact", "/auth/**" };

	private final UserService userService;
	private final LogoutHandler logoutHandler;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//spring tạo ra CsrfToken
		//cần class này để hanlde CsrfToken
		CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
		requestAttributeHandler.setCsrfRequestAttributeName("_csrf");
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // xử lý request độc lập, ko tạo ra seassion phù hợp vs rest api
			.cors().configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration configuration = new CorsConfiguration();
					configuration.setAllowCredentials(true);
					configuration.setAllowedMethods(Collections.singletonList("*"));
					configuration.setAllowedHeaders(Collections.singletonList("*"));
					configuration.setAllowedMethods(Collections.singletonList("http://localhost:4200"));
					configuration.setExposedHeaders(Arrays.asList("Authorization")); // bật để cho mỗi response về FE, FE có thể đọc được field này.
					configuration.setMaxAge(3600L);
					return configuration;
				}
		}).and().csrf(csrf -> csrf.csrfTokenRequestHandler(requestAttributeHandler).ignoringRequestMatchers("/auth/**")
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
		.authenticationProvider(authenticationProvider())
		.addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(new TokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
		.authorizeHttpRequests((requests ) ->  requests
			.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
			.requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
			.requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
			.requestMatchers("/myCards/**", "/user").authenticated()
			.requestMatchers(WHITE_LIST_URL).permitAll())
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults())
        .logout(logout ->
        		logout.logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        )
        .exceptionHandling(customizer -> customizer.accessDeniedHandler(accessDeniedHandler()));
		//.rememberMe();
		return http.build();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return webSecurity -> webSecurity.ignoring().requestMatchers("/actuator/**");
	}
	
	//tạo bean để mặc định provider có tên DaoAuthenticationProvider dùng
	// hoặc một provider nào muốn dùng
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userService);
		return daoAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationEventPublisher authenticationEventPublisher
	        (ApplicationEventPublisher applicationEventPublisher) {
	    return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
	}
	
	@Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
	
}
