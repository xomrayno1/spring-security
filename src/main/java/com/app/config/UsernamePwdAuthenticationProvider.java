package com.app.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.model.Authority;
import com.app.model.Customer;
import com.app.repository.CustomerRepository;
/**
 * 
 * Implement provider cho spring security <br>
 * Mặc định implement và đánh dấu bean thì nó sẽ dùng luôn prodiver này
 * Nếu ko implmenent provider nào hết nó sẽ dùng DaoAuthenticationProvider, DaoAuthenticationProvider có dùng UserDetailsService vì thế nên cần implement thêm Interface này
 * */
@Component
public class UsernamePwdAuthenticationProvider implements AuthenticationProvider{
	
	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;

	public UsernamePwdAuthenticationProvider(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		List<Customer> customers = customerRepository.findByEmail(username);
		if(customers.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		
		Customer customer = customers.get(0);
		if(!passwordEncoder.matches(pwd, customer.getPwd())) {
			throw new BadCredentialsException("Invalid Password");
		}
		 
		return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(customer.getAuthorities())); // constructor này có đánh dấu đã authenticated
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities){
		List<GrantedAuthority> grantedAuthorities = new ArrayList();
		for(Authority authority: authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		}
		return grantedAuthorities;
	}

	/**
	 * Xác định loại Authentication mà prodiver này hỗ trợ <br>
	 * provider hỗ trợ xác thực mật khẩu và username
	 * */
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
