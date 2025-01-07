package com.example.shoeshub.module.chitietsanpham.response;

import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;

import java.util.List;

public class ChiTietSanPhamPageResponse {
    private List<ChiTietSanPham> list;
    private int totalPages;
    private int totalNumbers;

    public ChiTietSanPhamPageResponse(List<ChiTietSanPham> list, int totalPages, int totalNumbers) {
        this.list = list;
        this.totalNumbers = totalNumbers;
        this.totalPages = totalPages;
    }

    public List<ChiTietSanPham> getList() {
        return list;
    }

    public void setList(List<ChiTietSanPham> list) {
        this.list = list;
    }

    public int getTotalNumbers() {
        return totalNumbers;
    }

    public void setTotalNumbers(int totalNumbers) {
        this.totalNumbers = totalNumbers;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
