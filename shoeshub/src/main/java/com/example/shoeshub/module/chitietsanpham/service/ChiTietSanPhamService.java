package com.example.shoeshub.module.chitietsanpham.service;


import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChiTietSanPhamService {
    List<ChiTietSanPham> getAll();

    ChiTietSanPham add(ChiTietSanPham chiTietSanPham);

//    Optional<ChiTietSanPham> findName(String name);

    Optional<ChiTietSanPham> findId(int id);

    Optional<String> delete(int id);
    List<ChiTietSanPham> isDuplicateChiTietGiay(int masp, int masize, int mams, int macl, int maanh);

//    Optional<ChiTietSanPham> findByDanhMuc(DanhMuc danhMuc);
//
//    Optional<ChiTietSanPham> findByTrangThai(int trangThai);

    public void importDataFromExcel(InputStream excelFile);
}
