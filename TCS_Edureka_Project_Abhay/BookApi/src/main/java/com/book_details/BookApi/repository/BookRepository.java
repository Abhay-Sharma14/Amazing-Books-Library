package com.book_details.BookApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book_details.BookApi.model.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	
	public Book findByTitle(String title);

}
