package com.library.booksauthors.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookRequest {

    @NotBlank
    private String bookName;

    @NotNull
    private BigDecimal bookPrice;

    @NotNull
    private Instant publishedDate;

    @NotNull
    private Long authorId;


}
