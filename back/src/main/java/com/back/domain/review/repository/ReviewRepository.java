package com.back.domain.review.repository;

import com.back.domain.book.entity.Book;
import com.back.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBook(Book book);
}
