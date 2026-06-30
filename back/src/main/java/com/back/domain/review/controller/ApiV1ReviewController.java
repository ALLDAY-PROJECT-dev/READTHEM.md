package com.back.domain.review.controller;

import com.back.domain.book.entity.Book;
import com.back.domain.book.repository.BookRepository;
import com.back.domain.book.service.BookService;
import com.back.domain.member.entity.Member;
import com.back.domain.member.service.MemberService;
import com.back.domain.review.dto.ReviewDto;
import com.back.domain.review.entity.Review;
import com.back.domain.review.service.ReviewService;
import com.back.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ApiV1ReviewController {
    private final ReviewService reviewService;
    private final BookRepository bookRepository;
    private final MemberService memberService;

    private final Rq rq;
    // private final BookService bookService;

    @GetMapping("/book/{bookId}")
    public List<ReviewDto> getReviewsByBook(
            @PathVariable @Valid long bookId
    ) {
        Book book = bookRepository.findById(bookId).get();

        return reviewService
                .findByBook(book)
                .stream()
                .map(ReviewDto::new)
                .toList();
    }

    public record ReviewsByMemberDto(
            Map<String, Object> rating,
            List<ReviewDto> results
    ) {

    }

    @GetMapping("/member/{memberId}")
    public ReviewsByMemberDto getReviewsByMember(
            @PathVariable @Valid long memberId
    ) {
        Member member = memberService.findById(memberId);

        return new ReviewsByMemberDto(
                reviewService.getRatingMap(member),
                reviewService
                    .findByMember(member)
                    .stream()
                    .map(ReviewDto::new)
                    .toList());
    }

    @GetMapping("/member/mine")
    public ReviewsByMemberDto mine() {
        return getReviewsByMember(rq.getActor().getId());
    }

}
