package com.example.shoeshub.module.chitietsanpham.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietSanPhamResponse {
    private Integer mactsp;

    private Integer soluong;

    private Float dongia;

    private String tenSanPham;

    private String tendanhmuc;

    private String tenSize;

    private String tenMauSac;

    private String tenChatLieu;

    private String moTa;
}
