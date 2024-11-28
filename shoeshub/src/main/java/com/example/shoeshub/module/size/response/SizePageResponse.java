package com.example.shoeshub.module.size.response;

import com.example.shoeshub.module.size.entity.Size;

import java.util.List;

public class SizePageResponse {
    private List<Size> list;
    private int totalPages;
    private int totalNumbers;

    public SizePageResponse(List<Size> list, int totalPages, int totalNumbers) {
        this.list = list;
        this.totalPages = totalPages;
        this.totalNumbers = totalNumbers;
    }

    public List<Size> getList() {
        return list;
    }

    public void setList(List<Size> list) {
        this.list = list;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalNumbers() {
        return totalNumbers;
    }

    public void setTotalNumbers(int totalNumbers) {
        this.totalNumbers = totalNumbers;
    }
}
