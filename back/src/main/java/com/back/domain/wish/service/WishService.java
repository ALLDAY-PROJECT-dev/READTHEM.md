package com.back.domain.wish.service;

import com.back.domain.book.entity.Book;
import com.back.domain.member.entity.Member;
import com.back.domain.wish.entity.Wish;
import com.back.domain.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishRepository;

    public List<Wish> findAll() {
        return wishRepository.findAll();
    }

    public List<Wish> findByMember(Member actor) {
        return wishRepository.findByMember(actor);
    }

    public Wish addWish(Member actor, Book book) {
        return wishRepository.save(new Wish(actor, book));
    }

    public void deleteWish(Member actor, Book book) {
        wishRepository.deleteByMemberAndBook(actor, book);
    }
}
