package com.library.booksauthors.repositroy;

import com.library.booksauthors.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
