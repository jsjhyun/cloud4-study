package com.practice.controller;

import com.practice.domain.Article;
import com.practice.dto.ArticleListViewResponse;
import com.practice.dto.ArticleViewResponse;
import com.practice.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    // read
    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll() // Article 가져옴
                .stream() // 스트림 사용하여 dto 객체로 변환
                .map(ArticleListViewResponse::new)
                .toList();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    // 상세 페이지
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable("id") long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }

    // 수정 페이지
    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false, name = "id") Long id, Model model) { // required = false : null이면 null 출력
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article)); // 기존 정보
        }
        return "newArticle"; // 수정 폼으로 이동
    }
}
