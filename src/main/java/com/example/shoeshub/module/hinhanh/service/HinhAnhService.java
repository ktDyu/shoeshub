package com.example.shoeshub.module.hinhanh.service;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import com.example.shoeshub.module.hinhanh.entity.HinhAnh;

import java.util.List;
import java.util.Optional;

public interface HinhAnhService {
    public List<HinhAnh> getAll();

    public HinhAnh add(HinhAnh hinhAnh);

    public void deleteById(int id);

    public Optional<HinhAnh> findById(int id);

    List<HinhAnh> getAllTrangThai(int trangthai);

//    public Optional<HinhAnh> findName(String name);

}
