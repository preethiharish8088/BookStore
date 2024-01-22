package com.my.book_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.book_store.dto.User;

public interface UserRepository  extends JpaRepository<User, Integer>{

	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);
	User findByEmail(String email);


}
