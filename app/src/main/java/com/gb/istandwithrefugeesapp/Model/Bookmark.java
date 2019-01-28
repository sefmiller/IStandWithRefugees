package com.gb.istandwithrefugeesapp.Model;

public class Bookmark {
    private Integer markerId;
    private Integer loginId;
    private BookmarkType bookmarkType;
    private Integer bookmarkId;

    public Integer getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(Integer bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public Integer getMarkerId() {
        return markerId;
    }

    public void setMarkerId(Integer markerId) {
        this.markerId = markerId;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public BookmarkType getBookmarkType() {
        return bookmarkType;
    }

    public void setBookmarkType(BookmarkType bookmarkType) {
        this.bookmarkType = bookmarkType;
    }

    public Bookmark(Integer bookmarkId, Integer markerId, Integer loginId, BookmarkType bookmarkType) {
        this.markerId = markerId;
        this.loginId = loginId;
        this.bookmarkId = bookmarkId;
        this.bookmarkType = bookmarkType;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "markerId=" + markerId +
                ", loginId=" + loginId +
                ", bookmarkType=" + bookmarkType +
                ", bookmarkId=" + bookmarkId +
                '}';
    }
}
