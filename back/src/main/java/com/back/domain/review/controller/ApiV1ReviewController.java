package com.back.domain.review.controller;

import com.back.domain.book.entity.Book;
import com.back.domain.book.repository.BookRepository;
import com.back.domain.book.service.BookService;
import com.back.domain.review.dto.ReviewDto;
import com.back.domain.review.entity.Review;
import com.back.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ApiV1ReviewController {
    private final ReviewService reviewService;
    private final BookRepository bookRepository;
    // private final BookService bookService;

    @GetMapping("/book/{bookId}")
    public List<ReviewDto> getReviewsByBook(
            @PathVariable @Valid long bookId
    ) {
        Book book = bookRepository.findById(bookId).get();

        return reviewService
                .findAllByBook(book)
                .stream()
                .map(ReviewDto::new)
                .toList();
    }
}
