package com.example.shoeshub.module.size.service;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.size.entity.Size;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface SizeService {
    public List<Size> getAll();

    public Size add(Size size);

    public Optional<Size> findKichthuoc(int kichThuoc);

    public Optional<Size> findId(int id);

    public Optional<String> delete(int id);

    List<Size> getAllTrangThai(int trangthai);

    public void importDataFromExcel(InputStream excelFile);
}
