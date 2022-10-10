package com.issuebook.IssueBookApi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.issuebook.IssueBookApi.model.Book;
import com.issuebook.IssueBookApi.repository.BookRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/issue")
public class IssueBookController {

	@Autowired
	BookRepository bookRepository;
	@Autowired
	private RestTemplate restTemplate;

	
	@GetMapping("/getAvailableCopies/{id}")
	public String getAvailableCopies(@PathVariable(value = "id") Long bookId) {
		
		try {
			String uri = "http://localhost:9194/library/getAvailableCopies/" + bookId;
			Object book = restTemplate.getForObject(uri, String.class);
			return book.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
		/*
		 * Book book; Integer availableCopies = null; book =
		 * bookRepository.findById(bookId).orElseThrow(() -> new
		 * BookNotFoundException("Book", "id", bookId)); if(book.getTotalCopies()>=0 ||
		 * book .getIssuedCopies()>=0){ if(book.getTotalCopies() >= book
		 * .getIssuedCopies()){ availableCopies = book.getTotalCopies() -
		 * book.getIssuedCopies(); return availableCopies.toString(); } else { return
		 * "Issued Copies is not greater than Total Copies"; } } else { return
		 * availableCopies.toString(); }
		 */
	}
	 
	@GetMapping("/books/{id}")
	public Book fetchBookDetails(@PathVariable(value = "id") Long bookId) {
		try {
			String uri = "http://localhost:9194/library/book/" + bookId;
			Book book = restTemplate.getForObject(uri, Book.class);
			return book;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/title/{title}")
	public Map fetchBookDetailsByTitle(@PathVariable(value = "title") String title) {
		try {
			String uri = "http://localhost:9194/library/title/" + title;
			Book book = restTemplate.getForObject(uri, Book.class);
			Map<String, Long> map = new HashMap<>();
			map.put("bookId", book.getId());
			map.put("availableCopies", (long) (book.getTotalCopies() - book.getIssuedCopies()));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/issueBook/{id}")
	public String issueBook(@PathVariable(value = "id") Long bookId) {
		

		try {
			String uri = "http://localhost:9194/library/issueBook/" + bookId;
			 return restTemplate.getForObject(uri, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
		/*
		 * Book book = null; String message; try { book =
		 * bookRepository.findById(bookId).orElseThrow(() -> new
		 * BookNotFoundException("Book", "id", bookId));
		 * if(book.getTotalCopies()-book.getIssuedCopies() > 0) { Integer issueCopies =
		 * book.getIssuedCopies() + 1; // Integer availableCopies = null;
		 * book.setIssuedCopies(issueCopies); Book updateBook =
		 * bookRepository.save(book); message = "Book is issued"; } else message =
		 * "Book not available" ; } catch(Exception e) { message = "Book is not issued";
		 * } return message;
		 */
	}

}
