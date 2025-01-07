package com.example.shoeshub.module.sanpham.request;

import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SanPhamRequest {
    private Long masp;

    private String tensp;

    private Integer trangthai;

    private DanhMuc danhMuc;
}
