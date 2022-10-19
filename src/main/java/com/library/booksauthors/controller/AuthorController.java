package com.library.booksauthors.controller;


import com.library.booksauthors.Excepetion.AuthorNotFoundException;
import com.library.booksauthors.request.AuthorRequest;
import com.library.booksauthors.response.AuthorResponse;
import com.library.booksauthors.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("author")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public AuthorResponse addAuthor(@Valid @RequestBody AuthorRequest request) {
        return authorService.addAuthor(request);
    }

    @GetMapping("/{id}")
    public AuthorResponse getAuthor(@PathVariable Long id) throws AuthorNotFoundException {
        return authorService.getAuthor(id);
    }

    @GetMapping
    public List<AuthorResponse> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable Long id) throws AuthorNotFoundException {
        authorService.deleteAuthor(id);
        return "author has been deleted successfully";
    }

    @PutMapping("/{id}")
    public AuthorResponse updateAuthor(@PathVariable Long id, @RequestBody AuthorRequest request) throws AuthorNotFoundException {
        return authorService.updateAuthor(id, request);
    }
}
