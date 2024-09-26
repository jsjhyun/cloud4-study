package com.cloud4.mvc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestRestController {

    @PostMapping("/login/submit")
    public String loginProcess(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        System.out.println("로그인이 성공적으로 완료되었습니다.");
        System.out.println("이름: " + name);
        System.out.println("이메일: " + email);
        System.out.println("비밀번호: " + password);
        return "200 OK";
    }
}