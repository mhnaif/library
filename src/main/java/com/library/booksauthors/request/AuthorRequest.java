package com.library.booksauthors.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorRequest {

    @NotBlank
    private String authorName;

    @NotBlank
    private String authorAddress;

}
