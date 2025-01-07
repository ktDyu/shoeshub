package com.example.shoeshub.module.hoadon.service.impl;

import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.entity.HoaDonChiTiet;
import com.example.shoeshub.module.hoadon.repository.HoaDonChiTietRepository;
import com.example.shoeshub.module.hoadon.repository.HoaDonRepository;
import com.example.shoeshub.module.hoadon.service.HoaDonService;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Override
    public List<HoaDon> findHoaDonByKhachHang(KhachHang khachHang) {
        return hoaDonRepository.findByKhachHang(khachHang);
    }

    @Override
    public List<HoaDon> findByKhachHangAndTrangThai(KhachHang khachHang, int i) {
        return null;
    }

    @Override
    public HoaDon findById(int mahd) {
        return hoaDonRepository.findById(mahd).orElse(null);
    }

    @Override
    public HoaDon save(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    //

    @Override
    public List<HoaDon> getAllHoaDons() {
        return hoaDonRepository.findAll();
    }

    @Override
    public List<HoaDonChiTiet> findHoaDonChiTietById(Integer id) {
        HoaDon hoaDon = findById(id);  // Tái sử dụng phương thức tìm hóa đơn theo ID
        return hoaDonChiTietRepository.findByHoaDon(hoaDon); // Tìm chi tiết hóa đơn từ HoaDon
    }

    public List<HoaDon> getHoaDonsByDate(Date date) {
        if (date != null) {
            return hoaDonRepository.findByTgtt(date);
        }
        return new ArrayList<>();
    }
    public List<HoaDon> searchHoaDons(String keyword) {
        return hoaDonRepository.findByTennguoinhanContainingOrSdtnguoinhanContaining(keyword, keyword);
    }
    public List<HoaDon> getHoaDonByTrangThai(Integer trangThai) {
        return hoaDonRepository.findByTrangThai(trangThai);
    }

}

