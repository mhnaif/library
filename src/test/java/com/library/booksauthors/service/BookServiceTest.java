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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@SpringBootTest
class BookServiceTest {

    @SpyBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void addBook() throws BookNotFoundException, AuthorNotFoundException {

        Author author = new Author();
        author.setAuthorId(12L);
        author.setAuthorName("mohammed");
        author.setAuthorAddress("kuwait");

        Mockito.when(authorRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(author));

        Book book = new Book();
        book.setBookPrice(BigDecimal.valueOf(234));
        book.setAuthor(author);
        book.setPublishedDate(Instant.now());
        book.setBookName("bbok1");

        BookRequest request = new BookRequest();
        request.setBookName(book.getBookName());
        request.setBookPrice(book.getBookPrice());
        request.setAuthorId(book.getAuthor().getAuthorId());
        request.setPublishedDate(book.getPublishedDate());


        Mockito.when(bookRepository.save(Mockito.any()))
                .thenReturn(book);

        BookResponse expected = getResponse(book);

        BookResponse actual = bookService.addBook(request);

        assertEquals(expected, actual);

    }

    @Test
    void getBook() throws BookNotFoundException {

        Book book = new Book();
        book.setBookPrice(BigDecimal.valueOf(234));
        book.setBookId(1L);
        book.setAuthor(new Author(12L, "moh", "kwaut"));
        book.setPublishedDate(Instant.now());
        book.setBookName("bbok1");


        Mockito.when(bookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(book));

        BookResponse expected = getResponse(book);

        BookResponse actual = bookService.getBook(1L);

        assertEquals(expected, actual);

    }

    @Test
    void deleteBook() {

        Book book = new Book();
        book.setBookPrice(BigDecimal.valueOf(234));
        book.setBookId(1L);
        book.setAuthor(new Author(12L, "moh", "kwaut"));
        book.setPublishedDate(Instant.now());
        book.setBookName("bbok1");

        Mockito.when(bookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(book));


        Mockito.doNothing()
                .when(bookRepository)
                .deleteById(Mockito.anyLong());
        assertDoesNotThrow(() -> bookService.deleteBook(Mockito.anyLong()));


    }

    @Test
    void getAllBooks() {
        List<Book> books = List.of(
                new Book(12L, "b1", BigDecimal.valueOf(234), Instant.now(), new Author(22L, "mohammed", "kuwait")),
                new Book(13L, "b2", BigDecimal.valueOf(222), Instant.now(), new Author(22L, "mohammed", "kuwait")));

        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<BookResponse> expected = books.stream()
                .map(e -> {
                    AuthorResponse authorResponse = new AuthorResponse();
                    authorResponse.setAuthorId(e.getAuthor().getAuthorId());
                    authorResponse.setAuthorAddress(e.getAuthor().getAuthorAddress());
                    authorResponse.setAuthorName(e.getAuthor().getAuthorName());

                    BookResponse bookResponse = new BookResponse();
                    bookResponse.setBookId(e.getBookId());
                    bookResponse.setBookName(e.getBookName());
                    bookResponse.setBookPrice(e.getBookPrice());
                    bookResponse.setPublishedDate(e.getPublishedDate());
                    bookResponse.setAuthorResponse(authorResponse);


                    return bookResponse;
                }).collect(Collectors.toList());

        List<BookResponse> actual
                = bookService.getAllBooks();

        assertEquals(expected,actual);

    }

    @Test
    void updateBook() throws BookNotFoundException {

        Author author = new Author();
        author.setAuthorId(12L);
        author.setAuthorName("mohammed");
        author.setAuthorAddress("kuwait");

        Mockito.when(authorRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(author));

        Book book = new Book();
        book.setBookPrice(BigDecimal.valueOf(234));
        book.setAuthor(author);
        book.setPublishedDate(Instant.now());
        book.setBookName("bbok1");

        Mockito.when(bookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(book));

        book.setBookPrice(BigDecimal.valueOf(2000));
        book.setAuthor(author);
        book.setPublishedDate(Instant.parse("1970-01-01T00:00:00Z"));
        book.setBookName("bbok13");
        book.setBookId(22L);

        BookRequest request = new BookRequest();
        request.setBookName(book.getBookName());
        request.setBookPrice(book.getBookPrice());
        request.setAuthorId(book.getAuthor().getAuthorId());
        request.setPublishedDate(book.getPublishedDate());

        Mockito.when(bookRepository.save(Mockito.any()))
                .thenReturn(book);

        BookResponse expected = getResponse(book);

        BookResponse actual = bookService.updateBook(22L, request);

        assertEquals(expected,actual);


    }

    private BookResponse getResponse(Book book) {

        AuthorResponse authorResponse = AuthorResponse.builder()
                .authorName(book.getAuthor().getAuthorName())
                .authorAddress(book.getAuthor().getAuthorAddress())
                .authorId(book.getAuthor().getAuthorId())
                .build();

        BookResponse bookResponse = BookResponse.builder()
                .publishedDate(book.getPublishedDate())
                .bookPrice(book.getBookPrice())
                .bookName(book.getBookName())
                .authorResponse(authorResponse)
                .bookId(book.getBookId())
                .build();

        return bookResponse;


    }
}