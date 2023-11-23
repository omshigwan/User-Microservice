package com.demo.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.demo.entities.UserDto;

public interface UserService {
	
	ResponseEntity<UserDto> saveUser(UserDto dto);
	ResponseEntity<String> authenticate(UserDto dto);
	ResponseEntity<UserDto> findUserById(long id);
	ResponseEntity<UserDto> findUserByUsername(String name);
	ResponseEntity<UserDto> findUserByEmail(String email);
	ResponseEntity<List<UserDto>> getAllUsers();
	ResponseEntity<UserDto> updateUser(UserDto dto, Long id);
	ResponseEntity<String> deleteUser(Long userId);
}
