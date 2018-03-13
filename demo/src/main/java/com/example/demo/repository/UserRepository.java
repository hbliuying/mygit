package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.UserEntity;
public interface UserRepository extends JpaRepository<UserEntity, Integer> ,JpaSpecificationExecutor<UserEntity>{

}
