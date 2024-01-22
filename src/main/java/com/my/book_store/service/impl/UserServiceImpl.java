package com.my.book_store.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.my.book_store.dao.UserDao;
import com.my.book_store.dto.User;
import com.my.book_store.helper.AES;
import com.my.book_store.helper.MailHelper;
import com.my.book_store.service.UserService;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	UserDao userDao;
	
	@Autowired
	MailHelper mailHelper;


	@Override
	public String signup(User user, BindingResult result) {
		if (userDao.checkEmailDuplicate(user.getEmail()))
			result.rejectValue("email", "error.email", "Account Already Exists - Check Email");
		if (userDao.checkMobileDuplicate(user.getMobile()))
			result.rejectValue("mobile", "error.mobile", "Account Already Exists - Check Mobile");

		if (result.hasErrors()) {
			return "Signup";
		} else {
			user.setRole("USER");
			user.setPassword(AES.encrypt(user.getPassword(), "123"));
			user.setOtp(new Random().nextInt(100000, 999999));
			userDao.save(user);
		   // mailHelper.sendOtp(user);
			return "redirect:/send-otp/" + user.getId();
		}
	}

	@Override
	public String verifyOtp(int id, int otp, ModelMap map) {
		User user = userDao.findById(id);
		if (user.getOtp() == otp) {
			user.setVerified(true);
			userDao.save(user);
			return "redirect:/signin";
		} else {
			map.put("failMessage", "Invalid Otp, Try Again");
			map.put("id", id);
			return "EnterOtp";
		}
	}

	@Override
	public String resendOtp(int id, ModelMap map) {
		User user = userDao.findById(id);
		user.setOtp(new Random().nextInt(100000, 999999));
		userDao.save(user);
		//mailHelper.sendOtp(user);
		map.put("id", id);
		map.put("successMessage", "Otp Sent Again, Check Email");
		return "EnterOtp";
	}
	@Override
	public String login(String email, String password, HttpSession session) {
		User user = userDao.findByEmail(email);
		if (user == null) {
			session.setAttribute("failMessage", "Invalid Email!!!");
			return "Signin";
		} else {
			if (AES.decrypt(user.getPassword(), "123").equals(password)) {
				session.setAttribute("user", user);
				session.setAttribute("successMessage", "Login Success");
				return "redirect:/";
			} else {
				session.setAttribute("failMessage", "Invalid Password!!!");
				return "Signin";
			}
		}
	}

}

