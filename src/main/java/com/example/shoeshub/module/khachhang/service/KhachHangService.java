package com.example.shoeshub.module.khachhang.service;

import com.example.shoeshub.module.khachhang.entity.KhachHang;

import java.util.List;
import java.util.Optional;

public interface KhachHangService {
    KhachHang checkLoginEmailAndPassword(String username, String password);

    KhachHang checkEmail(String email);

    KhachHang save(KhachHang khachHang);

    KhachHang save1(KhachHang khachHang);

    KhachHang findID(int makh);

    List<KhachHang> getAllCustomers();

    List<KhachHang> searchCustomers(String keyword);

    void updateCustomerStatus(Integer id, int status);
}
