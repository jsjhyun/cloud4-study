package com.cloud4.post.dto;

public class PostDTO {
    // Object 와 Object 사이에 데이터를 전달하기 위함
    private Long id;
    private String title;
    private String content;

    public PostDTO() {
    }

    public PostDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
