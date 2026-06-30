package com.back.domain.review.service;

import com.back.domain.book.entity.Book;
import com.back.domain.member.entity.Member;
import com.back.domain.review.entity.Review;
import com.back.domain.review.repository.ReviewRepository;
import com.back.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final TagService tagService;

    public List<Review> findAllByBook(Book book) {
        return reviewRepository.findByBook(book);
    }

    public void addReview(Book book, Member actor, float rating, String comment, List<String> tags) {
        reviewRepository.save(new Review(book, actor, rating, comment,
                tags.stream().map(tagService::findByNameOrSave).toList()));
    }
}
