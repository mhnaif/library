package com.library.booksauthors.repositroy;

import com.library.booksauthors.entity.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author,Long> {
}
