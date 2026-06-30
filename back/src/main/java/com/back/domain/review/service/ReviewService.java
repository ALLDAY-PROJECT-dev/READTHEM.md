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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final TagService tagService;

    public List<Review> findByBook(Book book) {
        return reviewRepository.findByBook(book);
    }

    public List<Review> findByMember(Member member) {
        return reviewRepository.findByReviewer(member);
    }

    public void addReview(Book book, Member actor, float rating, String comment, List<String> tags) {
        reviewRepository.save(new Review(book, actor, rating, comment,
                tags.stream().map(tagService::findByNameOrSave).toList()));
    }


    public Map<String, Object> getRatingMap(Member member) {

        Map<String, Object> ratings = new HashMap<>();

        ratings.put("average", reviewRepository.getAverageRatingByMember(member));

        for (int i = 0; i < 10; i++) {
            float targetRating = (i + 1) * 0.5f;
            ratings.put("%.1f".formatted(targetRating),
                    reviewRepository.countByReviewerAndRating(member, targetRating));
        }

        return ratings;
    }
}
