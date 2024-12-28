package com.example.shoeshub.module.khachhang.repo;

import com.example.shoeshub.module.khachhang.entity.DiaChi;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {
    DiaChi findByKhachHangAndLoaidiachiTrueAndTrangthai(KhachHang khachHang,int trangThai);

    List<DiaChi> findByKhachHangAndLoaidiachiFalseAndTrangthai(KhachHang khachHang, int trangThai);

    List<DiaChi> findByKhachHangAndTrangthai(KhachHang khachHang, int trangThai);
}
