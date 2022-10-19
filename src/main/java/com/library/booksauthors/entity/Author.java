package com.library.booksauthors.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long authorId;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_address")
    private String authorAddress;

}
