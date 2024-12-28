package com.example.shoeshub.module.buyer.service.impl;

import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.buyer.repository.GioHangRepository;
import com.example.shoeshub.module.buyer.service.GioHangService;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GioHangServiceImpl implements GioHangService {

    private final GioHangRepository gioHangRepository;

    @Override
    public GioHang findByKhachHang(KhachHang khachHang) {
        return gioHangRepository.findByKhachHang(khachHang).orElse(null);
    }

    @Override
    public void saveGH(GioHang gioHang) {
        gioHangRepository.save(gioHang);
    }
}
