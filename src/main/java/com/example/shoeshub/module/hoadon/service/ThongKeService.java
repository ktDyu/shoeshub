package com.example.shoeshub.module.hoadon.service;

import com.example.shoeshub.module.hoadon.response.TopNguoiMuaHangDTO;

import java.util.List;
import java.util.Map;

public interface ThongKeService {
    Map<String, Object> thongKeDoanhThuTheoNam(int year);


    Map<String, Long> thongKeDoanhThuTheoTrangThai();

    List<TopNguoiMuaHangDTO> getTopNguoiMuaHang();
}
