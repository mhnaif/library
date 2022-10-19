package com.library.booksauthors.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_price")
    private BigDecimal bookPrice;

    @Column(name = "published_date")
    private Instant publishedDate;

    @ManyToOne(targetEntity = Author.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;


}
