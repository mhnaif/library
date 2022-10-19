package com.library.booksauthors.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {

    private Long authorId;

    private String authorName;

    private String authorAddress;

}
