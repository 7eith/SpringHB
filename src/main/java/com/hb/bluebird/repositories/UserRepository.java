package com.hb.bluebird.repositories;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.bluebird.models.BlueUser;

@Repository
public class UserRepository {
	
	public List<BlueUser> getUsers() {
		List<BlueUser> users = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		try {
			File resourceJson = new File("src/main/resources/users.json");
			users = mapper.readValue(resourceJson, new TypeReference<List<BlueUser>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}

	public BlueUser getUserUsingUsername(String username) {

		List<BlueUser> users = getUsers();

		for (BlueUser user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	public void updateStacks(String _username, String _stack) {
		List<BlueUser> existingsUsers = getUsers();
		
		for (BlueUser savedUser: existingsUsers) {
			if (savedUser.getUsername().equalsIgnoreCase(_username)) {
				
				List<String> userStacks = savedUser.getStacks();
				
				if (userStacks != null) {					
					Boolean hasFound = false;
					
					for (String currentStack: userStacks) {
						if (currentStack.equalsIgnoreCase(_stack)) {
							hasFound = true;
							userStacks.remove(currentStack);
						}
					}

					if (!hasFound) {
						userStacks.add(_stack);
					}
				}
				else {
					List<String> fakeList = new ArrayList<String>();
					fakeList.add(_stack);
					
					savedUser.setStacks(fakeList);
				}
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			File resourceJson = new File("src/main/resources/users.json");
			String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(existingsUsers);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(resourceJson));
			writer.write(jsonString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void save(BlueUser user) {
		List<BlueUser> existingsUsers = getUsers();
		
		int newId = 0;
		
		for (BlueUser existingUser : existingsUsers) {
			if (existingUser.getId() >= newId) {
				newId = existingUser.getId() + 1;
			}
		}
		
		user.setId(newId);

		existingsUsers.add(user);

		ObjectMapper mapper = new ObjectMapper();
		try {
			File resourceJson = new File("src/main/resources/users.json");
			String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(existingsUsers);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(resourceJson));
			writer.write(jsonString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateMessage(String _username, String _msg) {
		List<BlueUser> existingsUsers = getUsers();
		
		for (BlueUser savedUser: existingsUsers) {
			if (savedUser.getUsername().equalsIgnoreCase(_username)) {
				savedUser.setMessage(_msg);
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			File resourceJson = new File("src/main/resources/users.json");
			String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(existingsUsers);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(resourceJson));
			writer.write(jsonString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}