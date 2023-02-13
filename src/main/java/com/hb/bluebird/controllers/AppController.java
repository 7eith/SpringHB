package com.hb.bluebird.controllers;

import java.util.Arrays;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hb.bluebird.models.AvailableStacksEnum;
import com.hb.bluebird.models.BlueUser;
import com.hb.bluebird.repositories.UserRepository;
import com.hb.bluebird.services.UserService;

@Controller
@RequestMapping(value = "/app")
public class AppController {
	
	private UserRepository userRepository;
	private UserService userService;

	public AppController(UserRepository _userRepository, UserService _userService) {
		this.userRepository = _userRepository;
		this.userService = _userService;
	}

	@GetMapping("")
	public ModelAndView appPage() {
		Object _currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User springUser = (User)_currentUser;
		
		BlueUser user = userRepository.getUserUsingUsername(springUser.getUsername());
		
		if (user == null)
			return null;
		
		ModelAndView vue = new ModelAndView("app");
		vue.addObject("stacks", user.getStacks());
		vue.addObject("message", user.getMessage());
		
		vue.addObject("availableStacks", Arrays.asList(AvailableStacksEnum.values()));
		
		return vue;
	}
	
	@PostMapping("/profile/updateStack")
	public ModelAndView updateStack(@RequestParam String stackName) {
		
		Object _currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User springUser = (User)_currentUser;
		
		BlueUser user = userRepository.getUserUsingUsername(springUser.getUsername());
		
		if (user == null)
			return null;

		userService.updateUserStack(user.getUsername(), stackName);
		
		ModelAndView mav = new ModelAndView("redirect:/app"); // force refresh
		return mav;		
	}
	
	@PostMapping("/profile/updateMessage")
	public ModelAndView updateMessage(@RequestParam String message) {
		
		Object _currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User springUser = (User)_currentUser;
		
		BlueUser user = userRepository.getUserUsingUsername(springUser.getUsername());
		
		if (user == null)
			return null;

		userService.updateUserMessage(user.getUsername(), message);
		
		ModelAndView mav = new ModelAndView("redirect:/app"); // force refresh
		return mav;		
	}
}
