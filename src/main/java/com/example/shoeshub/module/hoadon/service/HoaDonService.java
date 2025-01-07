package com.example.shoeshub.module.hoadon.service;

import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.entity.HoaDonChiTiet;
import com.example.shoeshub.module.khachhang.entity.KhachHang;

import java.util.Date;
import java.util.List;

public interface HoaDonService {
    List<HoaDon> findHoaDonByKhachHang(KhachHang khachHang);

    List<HoaDon> findByKhachHangAndTrangThai(KhachHang khachHang, int i);

    HoaDon findById(int mahd);

    HoaDon save(HoaDon hoaDon);

    List<HoaDon> getAllHoaDons();

    List<HoaDonChiTiet> findHoaDonChiTietById(Integer id);

    List<HoaDon> getHoaDonsByDate(Date date);

    List<HoaDon> searchHoaDons(String keyword);

    List<HoaDon> getHoaDonByTrangThai(Integer trangThai);
}
