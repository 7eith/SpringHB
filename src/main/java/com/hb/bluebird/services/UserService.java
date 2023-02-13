package com.hb.bluebird.services;

import java.lang.reflect.Array;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hb.bluebird.dtos.UserRegisterDTO;
import com.hb.bluebird.models.AvailableStacksEnum;
import com.hb.bluebird.models.BlueUser;
import com.hb.bluebird.repositories.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public void updateUserStack(String _userName, String _stack) {
		for (AvailableStacksEnum stackObject: AvailableStacksEnum.values()) {
			if (stackObject.toString().toLowerCase().equalsIgnoreCase(_stack)) {				
				userRepository.updateStacks(_userName, _stack);
			}
		}
	}
	
	public void updateUserMessage(String _userName, String _msg) {
		// ici je peux faire mes checks par exemple si c'est un message pas tolerable
		userRepository.updateMessage(_userName, _msg);
	}
	
	public void saveUser(UserRegisterDTO _dto) {
		BlueUser user = new BlueUser();
		user.setUsername(_dto._username());
		user.setPassword(passwordEncoder.encode(_dto._password()));
		user.setRole("USER");
		userRepository.save(user);		
	}	
}
