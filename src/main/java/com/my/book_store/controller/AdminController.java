package com.my.book_store.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.my.book_store.dto.Book;
import com.my.book_store.service.AdminService;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
@MultipartConfig
public class AdminController {
     @Autowired
	AdminService adminService;
     @GetMapping
 	public String loadAdminDashboard(HttpSession session) {
 		return adminService.loadAdminDashBoard(session);
 	}

 	@GetMapping("/add-book")
 	public String addBook(HttpSession session, ModelMap map) {
 		return adminService.addBook(session, map);
 	}

 	@PostMapping("/add-book")
 	public String addBook(@Valid Book book, BindingResult result, @RequestParam MultipartFile photo,
 			@RequestParam MultipartFile bookPdf, HttpSession session) throws IOException {
 		if (result.hasErrors())
 			return "AddBook";
 		else
 			return adminService.addBook(session, book, photo, bookPdf, result);
 	}
 }
    
    