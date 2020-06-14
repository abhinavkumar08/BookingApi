package io.bookingapi.dataload.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.bookingapi.dataload.entity.UserDetail;
import io.bookingapi.dataload.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.POST, value="/register")
	public UserDetail registerUser(@RequestBody UserDetail user) {
		
		logger.info("Registering user to the platform.");
		return userService.createUser(user);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<UserDetail> getAllUsers(){
		
		logger.info("Fetching all users");
		return userService.getAllUsers();
		
	}

}
