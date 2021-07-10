package com.cable.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cable.daointerface.UserInterfaceDao;
import com.cable.model.UserModel;
import com.cable.securityconfig.JwtTokenUtil;

@RestController
@RequestMapping("UI")
public class UILoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired 
	private UserInterfaceDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public ModelAndView login(UserModel request,HttpServletResponse response) {
		ModelAndView mv = null;
		request.setPassword(request.getFirebaseId());
		System.out.println("Inside Login"+request);
		
		//return new ResponseEntity<List<UserModel>>(Arrays.asList(request),HttpStatus.OK);
		try {
			Authentication authenticate = authenticationManager
					.authenticate(
							new UsernamePasswordAuthenticationToken(
									request.getPhoneNumber(), request.getPassword()
									)
							);

			UserModel user = (UserModel) authenticate.getPrincipal();
			System.out.println("Success"+user);
			
				Cookie cookie = new Cookie("Authorization",jwtTokenUtil.generateAccessToken(user));
		        cookie.setMaxAge(500);
		        cookie.setSecure(false);
		        cookie.setHttpOnly(true);
		        
		        response.addCookie(cookie);
			mv = new ModelAndView("redirect:/UI/home");
			mv.addObject("User", user);
		} catch (BadCredentialsException ex) {
			mv = new ModelAndView("redirect:/UI/loginPage");
		}
		return mv;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid UserModel request) {
		System.out.println(request);
		request.setFirebaseId(passwordEncoder.encode(request.getFirebaseId()));
		userDao.save(request);
		return new ResponseEntity<List<UserModel>>(Arrays.asList(request),HttpStatus.OK);
	}



}
