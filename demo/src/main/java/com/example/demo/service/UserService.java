package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;


@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	public List<User> search() {
		List<User> result = new ArrayList<>();
		List<UserEntity> findAll = this.repository.findAll();
		for (UserEntity userEntity : findAll) {
			User user = new User();
			BeanUtils.copyProperties(userEntity, user);
			result.add(user);
		}
		return result;
	}
	public void save() {
		UserEntity entity = new UserEntity();
		this.repository.save(entity);
	}
}
