package com.test.bootsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.test.security.TokenAuthenticationProvider;
import com.test.security.web.CustomAuthenticationTokenFilter;
import com.test.security.web.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.antMatchers("/list").hasAnyRole("USER","ADMIN").anyRequest().authenticated()
				.and()
				.anonymous().disable().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint());

		http.addFilterBefore(new CustomAuthenticationTokenFilter(authenticationManager()),
				BasicAuthenticationFilter.class);
	}

	@Bean("restAuthenticationEntryPoint")
	public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
		RestAuthenticationEntryPoint raep = new RestAuthenticationEntryPoint();
		return raep;
	}
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(tokenAuthenticationProvider());
    }
	
	/*@Bean("tokenService")
	public TokenService tokenService() {
		TokenService obj = new TokenService();
		return obj;
	}*/
	
	@Bean("tokenAuthenticationProvider")
	public TokenAuthenticationProvider tokenAuthenticationProvider() {
		TokenAuthenticationProvider raep = new TokenAuthenticationProvider();
		return raep;
	}

}
