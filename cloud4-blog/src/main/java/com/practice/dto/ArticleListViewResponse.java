package com.practice.dto;

import com.practice.domain.Article;
import lombok.Getter;

@Getter
public class ArticleListViewResponse {
    // 전체 페이지 표시
    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
