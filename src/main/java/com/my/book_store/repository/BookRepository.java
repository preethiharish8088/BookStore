package com.my.book_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.book_store.dto.Book;

public interface BookRepository extends JpaRepository<Book, Integer>  {

}
