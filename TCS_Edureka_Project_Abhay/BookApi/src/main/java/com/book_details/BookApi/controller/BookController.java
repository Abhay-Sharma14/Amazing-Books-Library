package com.book_details.BookApi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book_details.BookApi.exception.BookNotFoundException;
import com.book_details.BookApi.model.Book;
import com.book_details.BookApi.repository.BookRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/library")
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@GetMapping("/book")
	public List<Book> getAllBooks() {
	    return bookRepository.findAll();
	}
	@PostMapping("/book")
	public Book createBook(@Valid @RequestBody Book book) {
	    return bookRepository.save(book);
	}
	
	@PostMapping("/multiplebookSave")
	public String insertBook(@RequestBody List<Book> book) {
		bookRepository.saveAll(book);
		return "Book record has been updated sucessfully !";

	}
	
	@GetMapping("/book/{id}")
	public Book getBookById(@PathVariable(value = "id") Long bookId) {
	    return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book", "id", bookId));
	}
	
	@GetMapping("/title/{title}")
	public Book getBookByTitle(@PathVariable(value = "title") String title) {
	    return bookRepository.findByTitle(title);
	}
	
	@PutMapping("/book/{id}")	
	public Book updateBook(@PathVariable(value = "id") Long bookId,
	                                        @Valid @RequestBody Book bookDetails) {
	    bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book", "id", bookId));
	    bookDetails.setId(bookId);
	    Book updateBook = bookRepository.save(bookDetails);
	    return updateBook;
	}
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long bookId) {
	    Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book", "id", bookId));
	    bookRepository.delete(book);
	    return ResponseEntity.ok().build();
	}
	
	@SuppressWarnings("null")
	@GetMapping("/getAvailableCopies/{id}")
	public String getAvailableCopies(@PathVariable(value = "id") Long bookId) {
		Book book;
		Integer availableCopies = null;
	    book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book", "id", bookId));
	    if(book.getTotalCopies()>=0 || book .getIssuedCopies()>=0){
	    	 if(book.getTotalCopies() >= book .getIssuedCopies()){
	     availableCopies = book.getTotalCopies() - book.getIssuedCopies();
	     return availableCopies.toString();
	    }
	    else {
	    	return "Issued Copies is not greater than Total Copies";
	    	}
	    }
	    else {
	    	return availableCopies.toString();
	    }
	}
	
	@GetMapping("/issueBook/{id}")
	public String issueBook(@PathVariable(value = "id") Long bookId) {
		
		Book book = null;
		String message;
		try {
		book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book", "id", bookId));
		if(book.getTotalCopies()-book.getIssuedCopies() > 0) {
		Integer issueCopies = book.getIssuedCopies() + 1;
//		Integer availableCopies = null;
		book.setIssuedCopies(issueCopies);
	    Book updateBook = bookRepository.save(book);
	    message = "Book is issued";
		}
		else
			message = "Book not available" ;
		}
	    	catch(Exception e) {
	    		message = "Book is not issued";
	    }
		return message;
	}
}
