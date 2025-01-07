package com.example.shoeshub.module.khachhang.service;

import com.example.shoeshub.module.khachhang.entity.DiaChi;
import com.example.shoeshub.module.khachhang.entity.KhachHang;

import java.util.List;

public interface DiaChiService {
    DiaChi findDCDefaulByKhachHang(KhachHang khachHang);

    List<DiaChi> findbyKhachHangAndLoaiAndTrangThai(KhachHang khachHang);

    List<DiaChi> findbyKhachHangAndTrangThai(KhachHang khachHang);

    List<DiaChi> getAll();

    DiaChi save(DiaChi diaChi);

    DiaChi findId(int madc);

    List<DiaChi> getAddressesByCustomerId(Integer customerId);

    void deleteAddress(Integer addressId); // Thêm phương thức xóa địa chỉ

}
