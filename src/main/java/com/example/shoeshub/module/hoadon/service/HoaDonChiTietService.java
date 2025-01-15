package com.example.shoeshub.module.hoadon.service;

import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.entity.HoaDonChiTiet;

import java.util.List;

public interface HoaDonChiTietService {

    HoaDonChiTiet save(HoaDonChiTiet hoaDonChiTiet);

    List<HoaDonChiTiet> findByHoaDon(HoaDon hoaDon);
}
