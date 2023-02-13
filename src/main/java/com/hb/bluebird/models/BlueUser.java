package com.hb.bluebird.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class BlueUser {
	
	private Integer id;
	private String role;
	
	private String username;
	private String password;
	private List<String> stacks;
	
	private String message;

}
