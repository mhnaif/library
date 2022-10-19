package com.library.booksauthors.controller;


import com.library.booksauthors.Excepetion.AuthorNotFoundException;
import com.library.booksauthors.Excepetion.BookNotFoundException;
import com.library.booksauthors.request.BookRequest;
import com.library.booksauthors.response.BookResponse;
import com.library.booksauthors.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public BookResponse addBook(@Valid @RequestBody BookRequest request) throws AuthorNotFoundException {
        return bookService.addBook(request);
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable("id") Long id) throws BookNotFoundException {
        return bookService.getBook(id);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Long id) throws BookNotFoundException {
        bookService.deleteBook(id);
        return "book has been deleted successfully";
    }

    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable("id") Long id, @RequestBody BookRequest request) throws BookNotFoundException {
        return bookService.updateBook(id, request);
    }
}
