package com.example.shoeshub.module.buyer.service.impl;

import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.buyer.entity.GioHangChiTiet;
import com.example.shoeshub.module.buyer.repository.GioHangChiTietRepository;
import com.example.shoeshub.module.buyer.service.GioHangChiTietService;
import com.example.shoeshub.module.buyer.service.GioHangService;
import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GioHangChiTietServiceImpl implements GioHangChiTietService {

    private final GioHangChiTietRepository gioHangChiTietRepository;

    @Override
    public List<GioHangChiTiet> findByGHActive(GioHang gioHang) {
        return gioHangChiTietRepository.findByTrangthaiAndAndGioHang(1, gioHang);
    }

    @Override
    public GioHangChiTiet findByCTSPActive(ChiTietSanPham chiTietSanPham) {
        return gioHangChiTietRepository.findByTrangthaiAndAndChiTietSanPham(1,chiTietSanPham);
    }

    @Override
    public void addGHCT(GioHangChiTiet gioHangChiTiet) {
        gioHangChiTietRepository.save(gioHangChiTiet);
    }

    @Override
    public GioHangChiTiet findByCTSPActiveAndTrangThai(ChiTietSanPham chiTietSanPham,GioHang gioHang) {
        return gioHangChiTietRepository.findByChiTietSanPhamAndTrangthaiAndGioHang(chiTietSanPham,1, gioHang);
    }



}
