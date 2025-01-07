package com.example.shoeshub.module.danhmuc.service;

import com.example.shoeshub.module.danhmuc.entity.DanhMuc;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface DanhMucService {
    List<DanhMuc> getAll();

    DanhMuc add(DanhMuc danhMuc);

    Optional<DanhMuc> findName(String name);

    Optional<DanhMuc> findId(int id);

    Optional<String> delete(int id);

    List<DanhMuc> getAllTrangThai(int trangthai);

    public void importDataFromExcel(InputStream excelFile);
}