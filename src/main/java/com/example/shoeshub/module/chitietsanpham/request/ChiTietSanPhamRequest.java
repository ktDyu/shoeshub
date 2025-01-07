package com.example.shoeshub.module.chitietsanpham.request;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import com.example.shoeshub.module.size.entity.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChiTietSanPhamRequest {

    private Integer mactsp;

    private Integer soluong;

    private Float dongia;

    private Integer trangthai;

    private String imageUrl;

    private SanPham sanPham;

    private Size size;

    private MauSac mauSac;

    private ChatLieu chatLieu;

    private String moTa;
}
