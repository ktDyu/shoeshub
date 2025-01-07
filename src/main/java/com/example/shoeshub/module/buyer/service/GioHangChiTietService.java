package com.example.shoeshub.module.buyer.service;

import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.buyer.entity.GioHangChiTiet;
import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;

import java.util.List;

public interface GioHangChiTietService {
    List<GioHangChiTiet> findByGHActive(GioHang gioHang);

    GioHangChiTiet findByCTSPActive(ChiTietSanPham chiTietSanPham);

    void addGHCT(GioHangChiTiet gioHangChiTiet);

    GioHangChiTiet findByCTSPActiveAndTrangThai(ChiTietSanPham chiTietSanPham,GioHang gioHang);
}
