package com.back.domain.member.controller;

import com.back.domain.member.dto.MemberDto;
import com.back.domain.member.dto.MemberWithUsernameDto;
import com.back.domain.member.entity.Member;
import com.back.domain.member.service.MemberService;
import com.back.global.rq.Rq;
import com.back.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Transactional(readOnly = true)
// @Tag(name = "ApiV1MemberController", description = "API 회원 컨트롤러");
// @SecurityRequirement(name = "bearerAuth")
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/me")
    // @Operation("summary = '내 정보 조회")
    public MemberWithUsernameDto me() {
        Member actor = memberService.findById(rq.getActor().getId()).get();
        return new MemberWithUsernameDto(actor);
    }

    @GetMapping("/{id}")
    // @Operation("summary = '회원 정보 조회")
    public MemberDto getUser(
            @PathVariable @Valid long id
    ) {
        Member member = memberService.findById(id).get();

        return new MemberDto(member);
    }

    @DeleteMapping("/logout")
    public RsData<Void> logout() {
        rq.deleteCookie("refreshToken");
        rq.deleteCookie("accessToken");

        return new RsData<>("200-1", "로그아웃 성공");
    }

}
