package com.example.shoeshub.module.buyer.repository;

import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.buyer.entity.GioHangChiTiet;
import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet ,Integer> {
    List<GioHangChiTiet> findByTrangthaiAndAndGioHang(int trangThai, GioHang gioHang);

    GioHangChiTiet findByTrangthaiAndAndChiTietSanPham(int trangThai, ChiTietSanPham chiTietSanPham);

    GioHangChiTiet findByChiTietSanPhamAndTrangthaiAndGioHang(ChiTietSanPham chiTietSanPham, int trangThai, GioHang gioHang);
}
