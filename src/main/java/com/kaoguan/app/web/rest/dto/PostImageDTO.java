package com.kaoguan.app.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PostImageDTO {

    private Long bookid;
    private String image;

    @JsonCreator
    public PostImageDTO(Long bookid, String image) {
        super();
        this.bookid = bookid;
        this.image = image;
    }

    public Long getProductId() {
        return this.bookid;
    }

    public void setProductId(Long productId) {
        this.bookid = productId;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ImageDTO{" + "productId='" + bookid + '\'' + ", image='" + image + '\'' + '}';
    }
}
