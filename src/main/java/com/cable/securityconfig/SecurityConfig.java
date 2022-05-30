
package com.cable.securityconfig;

import static java.lang.String.format;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.cable.daointerface.UserInterfaceDao;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
	    securedEnabled = true,
	    jsr250Enabled = true,
	    prePostEnabled = true
	)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private UserInterfaceDao userDao;
	
	@Autowired
	private JwtTokenFilter jwtTokenFilter;


	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(firebaseId -> userDao
            .findbyPhoneNumber(firebaseId)
            .orElseThrow(
                    () -> new UsernameNotFoundException(
                            format("User: %s, not found", firebaseId)
                    )
            
            ));
    }
	
	 // Set password encoding schema
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Enable CORS and disable CSRF
		http = http.cors().and().csrf().disable();

		// Set session management to stateless
		http = http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and();

		// Set unauthorized requests exception handler
		http = http
				.exceptionHandling()
				.authenticationEntryPoint(
						(request, response, ex) -> {
							response.sendError(
									HttpServletResponse.SC_UNAUTHORIZED,
									ex.getMessage()
									);
						}
						)
				.and();
		// Set permissions on endpoints
		http.authorizeRequests()
		// Our public endpoints
		.antMatchers("/*").anonymous()
		.antMatchers(HttpMethod.POST,"/Api/login").anonymous()
		//.antMatchers(HttpMethod.POST,"/Api/register").anonymous()
		.antMatchers(HttpMethod.POST,"/Api/checkPhoneNumber").anonymous()
		.antMatchers(HttpMethod.POST,"/Api/createBaseData").anonymous()
		.antMatchers(HttpMethod.POST,"/Api/loadRegisterDetails").anonymous()
		.antMatchers(HttpMethod.GET,"/Api/start").anonymous()
		.antMatchers(HttpMethod.GET,"/Api/redirect").anonymous()
		
		
		
		// Our private endpoints
		.anyRequest().authenticated();

		// Add JWT token filter
		http.addFilterBefore(
				jwtTokenFilter,
				UsernamePasswordAuthenticationFilter.class
				);
	}

	// Used by spring security if CORS is enabled.
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Arrays.asList("http://localhost:3000/"));
		config.setExposedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Override 
	@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}