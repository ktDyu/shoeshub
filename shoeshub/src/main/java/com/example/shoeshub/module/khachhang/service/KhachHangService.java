package com.example.shoeshub.module.khachhang.service;

import com.example.shoeshub.module.khachhang.entity.KhachHang;

public interface KhachHangService {
    KhachHang checkLoginEmailAndPassword(String username, String password);

    KhachHang checkEmail(String email);

    KhachHang save(KhachHang khachHang);
}
