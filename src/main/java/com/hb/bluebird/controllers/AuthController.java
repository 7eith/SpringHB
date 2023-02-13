package com.hb.bluebird.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hb.bluebird.dtos.UserRegisterDTO;
import com.hb.bluebird.services.UserService;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {
	
	private UserService userService;
	
	public AuthController(UserService _userService) {
		this.userService = _userService;
	}

	@GetMapping("register")
	public ModelAndView registerPage() {
		ModelAndView mav = new ModelAndView("register");
		
		mav.addObject("user", new UserRegisterDTO("", ""));
		return mav;
	}
	
	@PostMapping("register")
	public ModelAndView registerUser(@ModelAttribute UserRegisterDTO _user) {		
		
		System.out.println(_user.toString());
		userService.saveUser(_user);
		ModelAndView mav = new ModelAndView("redirect:/login");
		return mav;		
	}
}
