package com.library.booksauthors.service;

import com.library.booksauthors.Excepetion.AuthorNotFoundException;
import com.library.booksauthors.Excepetion.BookNotFoundException;
import com.library.booksauthors.entity.Author;
import com.library.booksauthors.entity.Book;
import com.library.booksauthors.repositroy.AuthorRepository;
import com.library.booksauthors.repositroy.BookRepository;
import com.library.booksauthors.request.BookRequest;
import com.library.booksauthors.response.AuthorResponse;
import com.library.booksauthors.response.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;


    public BookResponse addBook(BookRequest request) throws AuthorNotFoundException {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException("author not found"));

        Book book = Book.builder()
                .author(author)
                .bookName(request.getBookName())
                .bookPrice(request.getBookPrice())
                .publishedDate(request.getPublishedDate())

                .build();

        Book save = bookRepository.save(book);

        return getResponse(save);

    }

    public BookResponse getBook(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("book not found"));
        return getResponse(book);

    }

    public void deleteBook(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("book not found"));

        bookRepository.deleteById(book.getBookId());
    }

    public List<BookResponse> getAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(e -> getResponse(e)).collect(Collectors.toList());
    }


    public BookResponse updateBook(Long id, BookRequest request) throws BookNotFoundException {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new BookNotFoundException("author not found"));

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("book not found"));

        if (request.getBookName() != null)
            book.setBookName(request.getBookName());

        if (request.getPublishedDate() != null)
            book.setPublishedDate(request.getPublishedDate());

        if (request.getBookPrice() != null)
            book.setBookPrice(request.getBookPrice());

        book.setAuthor(author);

        Book save = bookRepository.save(book);
        return getResponse(save);
    }

    private BookResponse getResponse(Book save) {
        AuthorResponse authorResponse = AuthorResponse.builder()
                .authorId(save.getAuthor().getAuthorId())
                .authorAddress(save.getAuthor().getAuthorAddress())
                .authorName(save.getAuthor().getAuthorName()).build();

        BookResponse bookResponse = BookResponse.builder()
                .publishedDate(save.getPublishedDate())
                .bookId(save.getBookId())
                .bookName(save.getBookName())
                .bookPrice(save.getBookPrice())
                .authorResponse(authorResponse).build();

        return bookResponse;

    }

}
