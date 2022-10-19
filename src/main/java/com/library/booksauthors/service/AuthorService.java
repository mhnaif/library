package com.library.booksauthors.service;
import com.library.booksauthors.Excepetion.AuthorNotFoundException;
import com.library.booksauthors.entity.Author;
import com.library.booksauthors.repositroy.AuthorRepository;
import com.library.booksauthors.request.AuthorRequest;
import com.library.booksauthors.response.AuthorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorResponse addAuthor(AuthorRequest request) {
        Author author = Author.builder()
                .authorName(request.getAuthorName())
                .authorAddress(request.getAuthorAddress())
                .build();
        Author saveAuthor = authorRepository.save(author);
        return getResponse(saveAuthor);
    }

    public AuthorResponse getAuthor(Long id) throws AuthorNotFoundException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("author not found"));
        return getResponse(author);
    }

    public List<AuthorResponse> getAllAuthors() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false)
                .map(e->getResponse(e)).collect(Collectors.toList());
    }

    public void deleteAuthor(Long id) throws AuthorNotFoundException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->  new AuthorNotFoundException("author not found"));
        authorRepository.deleteById(author.getAuthorId());

    }

    public AuthorResponse updateAuthor(Long id, AuthorRequest request) throws AuthorNotFoundException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->  new AuthorNotFoundException("author not found"));

        if (request.getAuthorAddress() != null)
            author.setAuthorAddress(request.getAuthorAddress());
        if (request.getAuthorName() != null)
            author.setAuthorName(request.getAuthorName());
        Author save = authorRepository.save(author);
        return getResponse(save);
    }

    private AuthorResponse getResponse(Author saveAuthor) {
        AuthorResponse authorResponse = AuthorResponse.builder()
                .authorId(saveAuthor.getAuthorId())
                .authorAddress(saveAuthor.getAuthorAddress())
                .authorName(saveAuthor.getAuthorName())
                .build();
        return authorResponse;
    }

}
