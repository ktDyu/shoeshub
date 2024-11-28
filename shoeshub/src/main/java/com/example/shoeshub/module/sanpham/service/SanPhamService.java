package com.example.shoeshub.module.sanpham.service;

import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import com.example.shoeshub.module.sanpham.entity.SanPham;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface SanPhamService {
    List<SanPham> getAll();

    SanPham add(SanPham sanPham);

    Optional<SanPham> findName(String name);

    Optional<SanPham> findId(int id);

    Optional<String> delete(int id);

    Optional<SanPham> findByDanhMuc(DanhMuc danhMuc);

    Optional<SanPham> findByTrangThai(int trangThai);

    List<SanPham> getAllTrangThai(int trangthai);


    public void importDataFromExcel(InputStream excelFile);
}
