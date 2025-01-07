package com.example.shoeshub.module.sanpham.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SanPhamResponse {

    private Long masp;

    private String tensp;

    private String ngaynhap;

    private String ngaycapnhat;

    private Integer trangthai;

    private String tendanhmuc;
}
