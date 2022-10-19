package com.library.booksauthors.service;
import com.library.booksauthors.Excepetion.AuthorNotFoundException;
import com.library.booksauthors.entity.Author;
import com.library.booksauthors.repositroy.AuthorRepository;
import com.library.booksauthors.request.AuthorRequest;
import com.library.booksauthors.response.AuthorResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AuthorServiceTest {

    @MockBean
    private AuthorRepository authorRepository;

    @SpyBean
    private AuthorService authorService;

    @Test
    void addAuthor() {

        Author author = getAuthor1(Author.builder());

        AuthorRequest request = AuthorRequest.builder()
                .authorName(author.getAuthorName())
                .authorAddress(author.getAuthorAddress())
                .build();

        Mockito.when(authorRepository.save(Mockito.any()))
                .thenReturn(author);

        AuthorResponse expected = getAuthorResponse(author);

        AuthorResponse actual = authorService.addAuthor(request);

        assertEquals(expected, actual);

    }

    @Test
    void getAuthor() throws AuthorNotFoundException {

        Author author = getAuthor1(Author.builder());

        AuthorResponse expected = getAuthorResponse(author);

        Mockito.when(authorRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(author));

        AuthorResponse actual = authorService.getAuthor(Mockito.anyLong());

        assertEquals(expected, actual);

    }

    @Test
    void getAllAuthors() {

        List<Author> authors = List.of(
                new Author(1L, "mohammed", "kuwait"),
                new Author(1L, "ahamd", "USA"));

        List<AuthorResponse> expected = authors.stream()
                .map(e -> {
                    return AuthorResponse.builder()
                            .authorAddress(e.getAuthorAddress())
                            .authorName(e.getAuthorName())
                            .authorId(e.getAuthorId())
                            .build();
                }).collect(Collectors.toList());

        Mockito.when(authorRepository.findAll())
                .thenReturn(authors);

        List<AuthorResponse> actual = authorService.getAllAuthors();

        assertEquals(expected, actual);
    }

    @Test
    void deleteAuthor() throws AuthorNotFoundException {
        Author author = getAuthor1(Author.builder()
                .authorId(22L));

        Mockito.when(authorRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(author));

        Mockito.doNothing()
                .when(authorRepository)
                .deleteById(Mockito.anyLong());

        assertDoesNotThrow(()->authorService.deleteAuthor(22L));

    }
    @Test
    void invalidGetAuthor(){
        Mockito.when(authorRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class,()->authorService.getAuthor(22L));
    }
    @Test
    void invalidDeleteAuthor() throws AuthorNotFoundException {
        Mockito.doNothing()
                .when(authorRepository)
                .deleteById(Mockito.anyLong());
        assertThrows(AuthorNotFoundException.class,()->authorService.deleteAuthor(22L));
    }

    @Test
    void updateAuthor() throws AuthorNotFoundException {

        Author author = getAuthor1(Author.builder());

        Mockito.when(authorRepository.findById(Mockito.isNull()))
                .thenReturn(Optional.of(author));

        Author author1 = Author.builder()
                .authorAddress("jordan")
                .authorName("ahmad")
                .build();

        AuthorRequest request = AuthorRequest
                .builder()
                .authorAddress(author1.getAuthorAddress())
                .authorName(author1.getAuthorName())
                .build();

        Mockito.when(authorRepository.save(Mockito.any()))
                .thenReturn(author1);

        AuthorResponse expected = getAuthorResponse(author1);

        AuthorResponse actual = authorService.updateAuthor(author1.getAuthorId(), request);

        assertEquals(expected,actual);
    }

    private AuthorResponse getAuthorResponse(Author author) {
        return AuthorResponse.builder()
                .authorName(author.getAuthorName())
                .authorAddress(author.getAuthorAddress()).build();
    }

    private Author getAuthor1(Author.AuthorBuilder builder) {
        return builder
                .authorName("mohammed")
                .authorAddress("kuwait")
                .build();
    }
}