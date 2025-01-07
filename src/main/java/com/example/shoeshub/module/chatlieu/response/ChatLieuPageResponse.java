package com.example.shoeshub.module.chatlieu.response;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;

import java.util.List;

public class ChatLieuPageResponse {
    private List<ChatLieu> list;
    private int totalPage;
    private int totalNumber;

    public ChatLieuPageResponse(List<ChatLieu> list, int totalPage, int totalNumber) {
        this.list = list;
        this.totalPage = totalPage;
        this.totalNumber = totalNumber;
    }

    public List<ChatLieu> getList() {
        return list;
    }

    public void setList(List<ChatLieu> list) {
        this.list = list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }
}
