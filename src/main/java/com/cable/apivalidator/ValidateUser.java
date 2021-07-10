package com.cable.apivalidator;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.cable.daointerface.UserInterfaceDao;
import com.cable.model.UserModel;

@Service
public class ValidateUser {
	
	private UserInterfaceDao userInterfaceDao;
	
	public ValidateUser(UserInterfaceDao userInterfaceDao){
		this.userInterfaceDao = userInterfaceDao;
	}

	public void checkPhoneNumber(@Valid String phoneNumber) throws CableException {
		Optional<UserModel> userModel = userInterfaceDao.findbyPhoneNumber(phoneNumber);
		if(userModel!=null) {
			throw new CableException("Phone Number Already Registered");
		}
	}
	
	

}
