package com.cable.apicontroller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cable.apivalidator.CableException;
import com.cable.apivalidator.ValidateOperator;
import com.cable.apivalidator.ValidateUser;
import com.cable.apivalidator.ValidateUserRole;
import com.cable.dao.OperatorDao;
import com.cable.dao.UserDao;
import com.cable.dao.UserRoleDao;
import com.cable.daointerface.UserInterfaceDao;
import com.cable.model.OperatorModel;
import com.cable.model.UserModel;
import com.cable.model.UserRoleModel;
import com.cable.securityconfig.JwtTokenUtil;
import com.cable.to.MainModel;
import com.cable.to.Message;
import com.cable.to.OperatorTO;
import com.cable.to.UserRoleTO;
import com.cable.to.UserTO;
import com.cable.utils.StringUtils;

@RestController
@RequestMapping(value="Api")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired 
	private UserInterfaceDao userDao;

	@Autowired 
	private UserRoleDao userRoleDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private OperatorDao operatorDao;

	@Autowired
	private UserRoleDao userRoleDoa;
	
	@GetMapping("/start")
	public String start() {
		return "Started";
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid UserTO request) {
		request.setPassword(request.getFirebaseId());
		Optional<UserModel> optionalUserModel = userDao.findbyPhoneNumber(request.getPhoneNumber());
		MainModel mainModel = new MainModel();
		if(!optionalUserModel.isPresent()) {
			mainModel.setMessage("Given Mobile Number Is Not Registered");
			mainModel.setResponse(false);
		    new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		UserTO userTO =  new UserTO();
		userTO.convertModelToTO(optionalUserModel.get());
		if(StringUtils.isNotEmpty(request.getFirebaseId())) {
			userTO.setFirebaseId(passwordEncoder.encode(request.getFirebaseId()));
		}
		if(StringUtils.isNotEmpty(request.getFirebaseToken())) {
			userTO.setFirebaseToken(request.getFirebaseToken());
		}
		userDao.save(userTO);
		UserModel user = null;
		try {
			Authentication authenticate = authenticationManager
					.authenticate(
							new UsernamePasswordAuthenticationToken(
									request.getPhoneNumber(), request.getPassword()
									)
							);

			user = (UserModel) authenticate.getPrincipal();
			userTO.convertModelToTO(user);
		} catch (BadCredentialsException ex) {
			mainModel.setMessage("Invalid Credentials");
			mainModel.setResponse(false);
		    new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		mainModel.setUserModelList(Arrays.asList(userTO));
		getDashBoardData(mainModel);
		return ResponseEntity.ok()
				.header(
						HttpHeaders.AUTHORIZATION,
						jwtTokenUtil.generateAccessToken(user)
						)
				.body(mainModel);
	}

	private void getDashBoardData(MainModel mainModel) {
		
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid UserTO request) {
		System.out.println(request);
		Optional<UserModel> optionalUserModel = userDao.findbyPhoneNumber(request.getPhoneNumber());
		MainModel mainModel = new MainModel();
		if(optionalUserModel.isPresent()) {
			UserTO userTO =  new UserTO();
			userTO.convertModelToTO(optionalUserModel.get());
			if(Objects.nonNull(request.getFirebaseId())) {
				userTO.setFirebaseId(passwordEncoder.encode(request.getFirebaseId()));
			}
			if(StringUtils.isNotEmpty(request.getFirebaseToken())) {
				userTO.setFirebaseToken(request.getFirebaseToken());
			}
			userDao.save(userTO);
			mainModel.setUserModelList(Arrays.asList(userTO));
		}else {
			if(Objects.nonNull(request.getFirebaseId())) {
				request.setFirebaseId(passwordEncoder.encode(request.getFirebaseId()));
			}
			userDao.save(request);
			mainModel.setUserModelList(Arrays.asList(request));
		}
		mainModel.setResponse(true);
		mainModel.setMessage("Registered Successfully");
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}

	@PostMapping("/checkPhoneNumber")
	public ResponseEntity<?> checkPhoneNumber(@RequestBody @Valid UserTO userTO) {
		ValidateUser validateUser = new ValidateUser(userDao);
		Message message = new Message();
		try {
			validateUser.checkPhoneNumber(userTO.getPhoneNumber());
			message.setMessage("Validated successfully");
			message.setResponse(true);
		}catch(CableException e) {
			message.setMessage(e.getMessage());
			message.setResponse(false);
		}
		return new ResponseEntity<Message>(message,HttpStatus.OK);
	}

	@PostMapping("/createBaseData")
	public ResponseEntity<?> createBaseData() {
		ArrayList<String> roles = new ArrayList<String>(Arrays.asList("ADMIN","OPERATOR","COLLECTOR","CUSTOMER","AUTHORIZED_COLLECTOR"));
		List<UserRoleTO> userRoleModelList = new ArrayList<UserRoleTO>();
		roles.forEach(role -> {
			UserRoleTO roleModel = new UserRoleTO();
			roleModel.setUserRoleDesc(role);
			userRoleModelList.add(roleModel);
		});
		userRoleDao.saveAll(userRoleModelList);
		ArrayList<String> operators = new ArrayList<String>( Arrays.asList("Gowthaman","Simon"));
		List<OperatorTO> operatorTOs = new ArrayList<OperatorTO>();
		operators.forEach(operator -> {
			OperatorTO operatorTO = new OperatorTO();
			operatorTO.setOperatorName(operator);
			operatorTOs.add(operatorTO);
		});
		operatorDao.saveAll(operatorTOs);
		Message message = new Message();
		message.setMessage("User Roles and Operators Created successfully");
		message.setResponse(true);
		return new ResponseEntity<List<Message>>(Arrays.asList(message),HttpStatus.OK);
	}

	@PostMapping("/loadRegisterDetails")
	public ResponseEntity<?> loadRegisterDetails() {
		MainModel mainModel = new MainModel();
		
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}

}
