package com.memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.user.entity.UserEntity;
import com.memo.user.repository.UserRepository;

@Service
public class UserBO {
	
	@Autowired
	private UserRepository userRepository;
	
	// input : login
	// output : userEntity 채워져있거나 null
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByloginId(loginId);
	}
	
	// input : loginId, password
	// output : UserEntity or Null(그 회원 가입된 사람이 없는 것)
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) { // and 생략 가능
		return userRepository.findByLoginIdAndPassword(loginId, password); // 우리가 알아서 만들어야함
				
	}
	
	
	// input : 4파라미터
	// output : UserEntity
	
	public UserEntity addUser(String loginId, String password, String name,String email) {
		return userRepository.save(UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
	}
}
