package com.library.booksauthors.response;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookResponse {

    private Long bookId;

    private String bookName;

    private BigDecimal bookPrice;

    private Instant publishedDate;

    private AuthorResponse authorResponse;



}
