package com.back.domain.member.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.UUID;

@NoArgsConstructor
@Entity
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    @Column(unique = true)
    private String refreshToken;

    public Member(long id, String username, String name) {
        setId(id);
        this.username = username;
        setName(name);
    }

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.refreshToken = UUID.randomUUID().toString();
    }

    public String getName() {
        return nickname;
    }
    public void setName(String name) {
        this.nickname = name;
    }

    public void modifyRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isAdmin() {
        if ("system".equals(username)) return true;
        return "admin".equals(username);
    }

}
