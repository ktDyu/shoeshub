package com.example.shoeshub.module.buyer.service;

import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.khachhang.entity.KhachHang;

public interface GioHangService {

    GioHang findByKhachHang(KhachHang khachHang);

    void saveGH(GioHang gioHang);
}
