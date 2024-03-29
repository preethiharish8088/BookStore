package com.my.book_store.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.my.book_store.dao.BookDao;
import com.my.book_store.dto.Book;
import com.my.book_store.dto.User;
import com.my.book_store.service.AdminService;

import jakarta.servlet.http.HttpSession;


@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	Book book;
	
	@Autowired
	BookDao bookDao;

	@Override
	public String loadAdminDashBoard(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			session.setAttribute("failMessage", "Session Expired");
			return "redirect:/signin";
		} else {
			if (user.getRole().equals("ADMIN"))
				return "AdminDashboard";
			else {
				session.setAttribute("failMessage", "You are Unauthorized to use this URL");
				return "redirect:/";
			}
		}
	}

	@Override
	public String addBook(HttpSession session, ModelMap map) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			session.setAttribute("failMessage", "Session Expired");
			return "redirect:/signin";
		} else {
			if (user.getRole().equals("ADMIN")) {
				map.put("book", book);
				return "AddBook";
			} else {
				session.setAttribute("failMessage", "You are Unauthorized to use this URL");
				return "redirect:/";
			}
		}
	}

	@Override
	public String addBook(HttpSession session, Book book, MultipartFile photo, MultipartFile bookPdf,
			BindingResult result) throws IOException {

		bookDao.saveBook(book);

		book.setPicturePath("/images/" + book.getId() + ".jpg");
		book.setDemoPdfPath("/demoPdfs/" + book.getId() + ".pdf");

		String pdfFolderPath = "src/main/resources/static/demoPdfs";
		String pictureFolderPath = "src/main/resources/static/images";

		Path picturePath = Paths.get(pictureFolderPath, book.getId() + ".jpeg");
		Path pdfPath = Paths.get(pdfFolderPath, book.getId() + ".pdf");

		Files.write(picturePath, photo.getBytes());
		Files.write(pdfPath, bookPdf.getBytes());

		bookDao.saveBook(book);
		session.setAttribute("successMessage", "Book Added Success");
		return "redirect:/admin";
	}
	}

	