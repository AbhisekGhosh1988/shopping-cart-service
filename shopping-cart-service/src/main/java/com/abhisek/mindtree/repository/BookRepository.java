package com.abhisek.mindtree.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhisek.mindtree.entity.Book;


public interface BookRepository extends JpaRepository<Book, Integer> {

}
