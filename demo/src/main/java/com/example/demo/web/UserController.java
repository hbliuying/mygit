package com.example.demo.web;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/find")
	public List<User> findAll() {
		return userService.search();
	}
	@GetMapping(value="show")
	public String show() {
		return "hello world!";
		
	}
	@GetMapping(value="uoload")
	public void upload() {
		File file = new File("d:\\my.txt");
	}
	
}
