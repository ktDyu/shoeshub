package com.example.shoeshub.module.khachhang.service.impl;

import com.example.shoeshub.module.khachhang.entity.DiaChi;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.khachhang.repo.DiaChiRepository;
import com.example.shoeshub.module.khachhang.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaChiServiceImpl implements DiaChiService {
    @Autowired
    DiaChiRepository diaChiRepository;

    @Override
    public DiaChi findDCDefaulByKhachHang(KhachHang khachHang) {
        return diaChiRepository.findByKhachHangAndLoaidiachiTrueAndTrangthai(khachHang, 1);
    }

    @Override
    public List<DiaChi> findbyKhachHangAndLoaiAndTrangThai(KhachHang khachHang) {
        return diaChiRepository.findByKhachHangAndLoaidiachiFalseAndTrangthai(khachHang, 1);
    }

    @Override
    public List<DiaChi> findbyKhachHangAndTrangThai(KhachHang khachHang) {
        return diaChiRepository.findByKhachHangAndTrangthai(khachHang, 1);
    }

    @Override
    public List<DiaChi> getAll() {
        return diaChiRepository.findAll();
    }

    @Override
    public DiaChi save(DiaChi diaChi) {
        return diaChiRepository.save(diaChi);
    }

    @Override
    public DiaChi findId(int madc) {
        return diaChiRepository.findById(madc).orElse(null);
    }
}
