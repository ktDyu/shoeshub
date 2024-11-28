package com.example.shoeshub.module.sanpham.service.impl;

import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import com.example.shoeshub.module.danhmuc.service.DanhMucService;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import com.example.shoeshub.module.sanpham.repository.SanPhamRepository;
import com.example.shoeshub.module.sanpham.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {
    private final SanPhamRepository sanPhamRepository;
    private final DanhMucService danhMucService;

    @Override
    public List<SanPham> getAll() {
        return sanPhamRepository.findAll();
    }

    @Override
    public SanPham add(SanPham sanPham) {
        return sanPhamRepository.save(sanPham);
    }

    @Override
    public Optional<SanPham> findName(String name) {
        return sanPhamRepository.findByTensp(name);
    }

    @Override
    public Optional<SanPham> findId(int id) {
        return sanPhamRepository.findByMasp(id);
    }

    @Override
    public Optional<String> delete(int id) {
        var y = sanPhamRepository.findById(id);
        SanPham sanPham = y.get();
        sanPham.setTrangthai(0);
        sanPhamRepository.save(sanPham);
        return Optional.of("Xóa thành công");

    }

    @Override
    public void importDataFromExcel(InputStream excelFile) {

    }
    @Override
    public Optional<SanPham> findByDanhMuc(DanhMuc danhMuc) {
        return sanPhamRepository.findByDanhmuc(danhMuc);
    }

    @Override
    public Optional<SanPham> findByTrangThai(int trangThai) {
        return sanPhamRepository.findByTrangthai(trangThai);
    }

    @Override
    public List<SanPham> getAllTrangThai(int trangthai) {
        return sanPhamRepository.findAllByTrangthai(trangthai);
    }


//    public Optional<SanPham> detail(int id){
//        var u = sanPhamRepository.findById(id);
////        if (u.isPresent()) {
//        return Optional.of(u.get());
////        }
//
//    }
//    public boolean hasFilter(Integer macl, String tenchatlieu) {
//        return macl != null || (tenchatlieu != null && !tenchatlieu.trim().isEmpty());
//    }

//    public List<SanPham> filterChatLieu(Integer macl, String tenchatlieu) {
//        if (macl != null && tenchatlieu != null && !tenchatlieu.isEmpty()) {
//            return sanPhamRepository.findByMaclOrTenchatlieu(macl, tenchatlieu);
//        } else if (macl != null) {
//            return sanPhamRepository.findByMaspIn(macl);
//        } else if (tenchatlieu != null && !tenchatlieu.isEmpty()) {
//            return sanPhamRepository.findByTensp(tenchatlieu);
//        } else {
//            return sanPhamRepository.findAll();
//        }
//    }

}
