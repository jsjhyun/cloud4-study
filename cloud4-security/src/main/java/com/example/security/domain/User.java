package com.example.security.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;

import static lombok.AccessLevel.*;

@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    // ##### OAuth2를 사용하는 경우 provider와 providerId를 저장 #####
//    @Column(name = "provider")
//    @Enumerated(EnumType.STRING)
//    private Provider provider;
//
//    @Column(name = "provider_id")
//    private String providerId;
//
//    @Column(name = "name")
//    private String name;

    // ############################################################

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override //사용자의 id를 반환(고유 값)
    public String getUsername() {
        return email;
    }

    @Override //사용자의 패스워드를 반환
    public String getPassword() {
        return password;
    }

    @Override //계정 만료 여부
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정 잠금 여부 반환
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 패스워드의 만료 여부 반환
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
