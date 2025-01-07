package com.example.shoeshub.module.chitietsanpham.service;


import com.example.shoeshub.module.buyer.entity.CTSPViewModel;
import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import com.example.shoeshub.module.hinhanh.entity.HinhAnh;
import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChiTietSanPhamService {
    List<ChiTietSanPham> getAll();

    ChiTietSanPham add(ChiTietSanPham chiTietSanPham);


    Optional<ChiTietSanPham> findId(int id);

    Optional<String> delete(int id);

    public List<ChiTietSanPham> isDuplicateForUpdate(int mactsp, int masp, int masize, int mams, int macl, int maanh, float dongia);

    public List<ChiTietSanPham> isDuplicateForAdd(int masp, int masize, int mams, int macl, int maanh, float dongia);

    public void importDataFromExcel(InputStream excelFile);

    public List<ChiTietSanPham> findBySanPhamAndMauSac(SanPham sanPham, MauSac mauSac, int trangThai, int soluong);
    public List<ChiTietSanPham> findBySanPhamAndMauSacSold(SanPham sanPham, MauSac mauSac, int trangThai, int soluong);

    public HinhAnh findDistinctHinhAnhByGiayAndMau(SanPham sanPham, MauSac mauSac);

    List<MauSac> findDistinctMauSacByGiay(SanPham sanPham);

    List<ChiTietSanPham> findByGiayAndMau(SanPham sanPham, MauSac mauSac);


}
