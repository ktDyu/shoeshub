package com.example.shoeshub.module.hoadon.service;

import com.example.shoeshub.module.hoadon.DTO.TopNguoiMuaHangDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ThongKeService {
    Map<String, Object> thongKeDoanhThuTheoNam(int year);


    Map<String, Long> thongKeDoanhThuTheoTrangThai();

    List<TopNguoiMuaHangDTO> getTopNguoiMuaHang();
}
