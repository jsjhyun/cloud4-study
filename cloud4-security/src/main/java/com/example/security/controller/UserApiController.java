package com.example.security.controller;

import com.example.security.dto.request.AddUserRequest;
import com.example.security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> signup(@RequestBody AddUserRequest request){
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        userService.userSignUp(request);
        return ResponseEntity.ok("회원가입 성공");
    }

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(
//                request,
//                response,
//                SecurityContextHolder
//                        .getContext()
//                        .getAuthentication()
//        );
//        return "redirect:/login";
//    }
}