package io.bookingapi.dataload.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.bookingapi.dataload.entity.UserDetail;
import io.bookingapi.dataload.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public UserDetail createUser(UserDetail user) {
		
		return userRepository.save(user);
		
	}

	public List<UserDetail> getAllUsers() {
		
		List<UserDetail> users = new ArrayList<UserDetail>();
		userRepository.findAll().forEach(users::add);
		
		return users;
	}

}
