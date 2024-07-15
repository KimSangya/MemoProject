package com.memo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	// JPQL
	// 내가 쿼리를 만들어내거나 그런거면 따로 만들어줘야하는데 지금은 필요없음
	public UserEntity findByloginId(String loginId);
	
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
	
}
