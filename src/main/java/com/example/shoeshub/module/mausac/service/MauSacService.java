package com.example.shoeshub.module.mausac.service;

import com.example.shoeshub.module.mausac.entity.MauSac;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface MauSacService {
    List<MauSac> getAll();
    MauSac add(MauSac mauSac);
    Optional<MauSac> findName(String name);
    Optional<MauSac> findId(int id);
    Optional<String> delete(int id);
    List<MauSac> getAllTrangThai(int trangthai);
    void importDataFromExcel(InputStream excelFile);
}
