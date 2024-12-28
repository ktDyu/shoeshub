package com.example.shoeshub.module.chitietsanpham.service.impl;

import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import com.example.shoeshub.module.chitietsanpham.repository.ChiTietSanPhamRepository;
import com.example.shoeshub.module.chitietsanpham.service.ChiTietSanPhamService;
import com.example.shoeshub.module.hinhanh.entity.HinhAnh;
import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public List<ChiTietSanPham> getAll() {
        return chiTietSanPhamRepository.findAll();
    }

    @Override
    public ChiTietSanPham add(ChiTietSanPham chiTietSanPham) {
        return chiTietSanPhamRepository.save(chiTietSanPham);
    }


    @Override
    public Optional<ChiTietSanPham> findId(int id) {
        return chiTietSanPhamRepository.findById(id);
    }

    @Override
    public Optional<String> delete(int id) {
        var y = chiTietSanPhamRepository.findById(id);
        ChiTietSanPham sanPham = y.get();
        sanPham.setTrangthai(0);
        chiTietSanPhamRepository.save(sanPham);
        return Optional.of("Xóa thành công");
    }

    @Override
    public List<ChiTietSanPham> isDuplicateForUpdate(int mactsp, int masp, int masize, int mams, int macl, int maanh, float dongia) {
        List<ChiTietSanPham> duplicates = chiTietSanPhamRepository.findDuplicateForUpdate(masp, masize, mams, macl, maanh, dongia, mactsp);
        return (duplicates == null) ? Collections.emptyList() : duplicates;
    }

    @Override
    public List<ChiTietSanPham> isDuplicateForAdd(int masp, int masize, int mams, int macl, int maanh, float dongia) {
        List<ChiTietSanPham> duplicates = chiTietSanPhamRepository.findDuplicateForAdd(masp, masize, mams, macl, maanh, dongia);
        return (duplicates == null) ? Collections.emptyList() : duplicates; // Trả về true nếu trùng
    }

    @Override
    public void importDataFromExcel(InputStream excelFile) {

    }

    @Override
    public List<ChiTietSanPham> findBySanPhamAndMauSac(SanPham sanPham, MauSac mauSac, int trangThai, int soluong) {
        return chiTietSanPhamRepository.findBySanPhamAndMauSacAndTrangthaiAndSoluongGreaterThan(sanPham, mauSac, trangThai, soluong);
    }

    @Override
    public List<ChiTietSanPham> findBySanPhamAndMauSacSold(SanPham sanPham, MauSac mauSac, int trangThai, int soluong) {
        return chiTietSanPhamRepository.findBySanPhamAndMauSacAndTrangthaiAndSoluong(sanPham, mauSac, trangThai, soluong);
    }


    @Override
    public HinhAnh findDistinctHinhAnhByGiayAndMau(SanPham sanPham, MauSac mauSac) {
        return chiTietSanPhamRepository.findDistinctHinhAnhByGiay(sanPham, mauSac);
    }

    @Override
    public List<MauSac> findDistinctMauSacByGiay(SanPham sanPham) {
        return chiTietSanPhamRepository.findDistinctMauSacByGiayAndTrangThai(sanPham);
    }

    @Override
    public List<ChiTietSanPham> findByGiayAndMau(SanPham sanPham, MauSac mauSac) {
        return chiTietSanPhamRepository.findBySanPhamAndMauSac(sanPham, mauSac);
    }
}
