package com.my.book_store.service;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.my.book_store.dto.User;

import jakarta.servlet.http.HttpSession;

public interface UserService {

	public String signup(User user,BindingResult result);

	public String verifyOtp(int id, int otp, ModelMap map);

	public String resendOtp(int id, ModelMap map);
	public String login(String email, String password, HttpSession session);
}
