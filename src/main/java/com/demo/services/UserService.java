package com.demo.services;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.entities.User;
import com.demo.entities.UserDto;
import com.demo.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
//	@Autowired
//	PasswordEncoder passwordEncoder;
	
	@Autowired
	ModelMapper mapper;
	
	//method for save user in db
	public ResponseEntity<UserDto> saveUser(UserDto dto){
		try {
			User user = mapToUser(dto);
//			user.setPassword(passwordEncoder.encode(dto.getPassword())); //encodes password before saving in db
			User newUser = userRepo.save(user);
			newUser.setPassword("******"); //masked password as ****** , so that response won't show password
			UserDto responseDto = mapToDto(newUser); 
			return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
	}
	
	//method to find user by id
	public ResponseEntity<UserDto> findUserById(long id) {
		
		try {
			
			User user = userRepo.findById(id).orElse(null);
			if(user!=null) {
				UserDto responseDto = mapToDto(user);
				responseDto.setPassword("******");
				return new ResponseEntity<>(responseDto,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
	}
	
	//method to find user by username
	public ResponseEntity<UserDto> findUserByUsername(String name) {
		try {
			User user = userRepo.findByUsername(name).orElse(null);
			if(user!=null) {
				UserDto responseDto = mapToDto(user);
				responseDto.setPassword("******");
				return new ResponseEntity<>(responseDto,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
	}
	
	//method to find user by email
	public ResponseEntity<UserDto> findUserByEmail(String email) {
		try {
			User user = userRepo.findByEmail(email).orElse(null);
			if(user!=null) {
				UserDto responseDto = mapToDto(user);
				responseDto.setPassword("******");  
				return new ResponseEntity<>(responseDto,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);	
	}
	
	//method to get all users as a list
	public ResponseEntity<List<UserDto>>  getAllUsers(){
		try {
			List<User> userList = userRepo.findAll();
			List<UserDto> dtoList = new ArrayList<>();
			if(userList!=null) {
				for(User user : userList) {
					UserDto userDto = mapToDto(user);
					userDto.setPassword("******");
					dtoList.add(userDto);
				}
				return new ResponseEntity<>(dtoList,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
	}
	
	//method to update user
	public ResponseEntity<UserDto> updateUser(UserDto dto ,Long id) {
		
		try {
			User existingUser = userRepo.findById(id).orElse(null);
			User newUser = mapToUser(dto);
			if(existingUser!=null) {
				existingUser.setEmail(newUser.getEmail());
				existingUser.setUsername(newUser.getUsername());
				existingUser.setPassword(newUser.getPassword());
				
				User updatedUser =  userRepo.save(existingUser);
				UserDto responseDto = mapToDto(updatedUser); 
				responseDto.setPassword("******");
				return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
			}
			else {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);  //write appropriate exception when user does not exist
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
		
		
		
	
	}
	
	//method to delete user from db
	public ResponseEntity<String> deleteUser(Long userId) {
		try {
			if (userRepo.existsById(userId)) {
	            userRepo.deleteById(userId);
	            return new ResponseEntity<>("user deleted succefully!",HttpStatus.OK);
	        } else {
	        	return new ResponseEntity<>("user with given id not found",HttpStatus.NOT_FOUND);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>("bad request",HttpStatus.BAD_REQUEST);
	
	}
	
	
	//mapping methods to map user and userDto
	public UserDto mapToDto(User User) {
		UserDto dto = mapper.map(User, UserDto.class);
		return dto;
	}
	public User mapToUser(UserDto dto) {
		User user = mapper.map(dto, User.class);
		return user;
	}
}
