package com.my.book_store.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.book_store.dto.Book;
import com.my.book_store.repository.BookRepository;

@Repository	
public class BookDao {
	@Autowired
	BookRepository bookRepository;

	public void saveBook(Book book) {
		bookRepository.save(book);
	}
}
