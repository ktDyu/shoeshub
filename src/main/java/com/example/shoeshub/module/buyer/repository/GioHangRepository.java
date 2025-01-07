package com.example.shoeshub.module.buyer.repository;

import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    Optional<GioHang> findByKhachHang(KhachHang khachHang);


}
