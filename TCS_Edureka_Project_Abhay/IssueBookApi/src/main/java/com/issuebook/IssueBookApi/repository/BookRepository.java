package com.issuebook.IssueBookApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.issuebook.IssueBookApi.model.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
