package com.example.shoeshub.module.chitietsanpham.service.impl;

import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import com.example.shoeshub.module.chitietsanpham.repository.ChiTietSanPhamRepository;
import com.example.shoeshub.module.chitietsanpham.request.ChiTietSanPhamRequest;
import com.example.shoeshub.module.chitietsanpham.response.ChiTietSanPhamPageResponse;
import com.example.shoeshub.module.chitietsanpham.service.ChiTietSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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
        if (chiTietSanPham.getSoluong() > 0) {
            chiTietSanPham.setTrangthai(1);
        } else {
            chiTietSanPham.setTrangthai(0);
        }
        return chiTietSanPhamRepository.save(chiTietSanPham);
    }


    @Override
    public Optional<ChiTietSanPham> findId(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<String> delete(int id) {
        var y = chiTietSanPhamRepository.findById(id);
            chiTietSanPhamRepository.delete(y.get());
            return Optional.of("Xóa thành công");
    }

    @Override
    public List<ChiTietSanPham> isDuplicateChiTietGiay(int masp, int masize, int mams, int macl, int maanh) {
        return chiTietSanPhamRepository.findBySanPhamAndSizeAndMauSacAndChatLieuAndHinhAnh(masp, masize,  mams,  macl, maanh);
    }

    @Override
    public void importDataFromExcel(InputStream excelFile) {

    }



//    public Optional<String> update(int id, ChiTietSanPhamRequest chiTietSanPhamRequest) {
//        var u = chiTietSanPhamRepository.findById(id);
//        if (u.isPresent()) {
//            ChiTietSanPham chiTietSanPham = u.get();
//
//            if (chiTietSanPhamRequest.getSoluong() != null) {
//                chiTietSanPham.setSoluong(chiTietSanPhamRequest.getSoluong());
//            }
//            if (chiTietSanPhamRequest.getDongia() != null) {
//                chiTietSanPham.setDongia(chiTietSanPhamRequest.getDongia());
//            }
//            if (StringUtils.hasText(chiTietSanPhamRequest.getMoTa())) {
//                chiTietSanPham.setMoTa(chiTietSanPhamRequest.getMoTa());
//            }
//            if (StringUtils.hasText(chiTietSanPhamRequest.getImageUrl())) {
//                chiTietSanPham.setImageUrl(chiTietSanPhamRequest.getImageUrl());
//            }
//
//            if (StringUtils.hasText(chiTietSanPhamRequest.getSanPham().getTensp())) {
//                chiTietSanPham.setSanPham(chiTietSanPhamRequest.getSanPham());
//            }
//            if (StringUtils.hasText(chiTietSanPhamRequest.getImageUrl())) {
//                chiTietSanPham.setChatLieu(chiTietSanPhamRequest.getChatLieu());
//            }
//            if (StringUtils.hasText(chiTietSanPhamRequest.getImageUrl())) {
//                chiTietSanPham.setSize(chiTietSanPhamRequest.getSize());
//            }
//            if (StringUtils.hasText(chiTietSanPhamRequest.getImageUrl())) {
//                chiTietSanPham.setMauSac(chiTietSanPhamRequest.getMauSac());
//            }
//
//            if (chiTietSanPhamRequest.getSoluong() > 0) {
//                chiTietSanPham.setTrangthai(1);
//            } else {
//                chiTietSanPham.setTrangthai(0);
//            }
//
//            chiTietSanPhamRepository.save(chiTietSanPham);
//            return Optional.of("Sửa thành công");
//        }
//        return Optional.of("Không tìm thấy id chi tiết sản phẩm");
//    }
//
//    public Optional<String> deletee(int id) {
//        var y = chiTietSanPhamRepository.findById(id);
//        if (y.isPresent()) {
//
//            chiTietSanPhamRepository.delete(y.get());
//            return Optional.of("Xóa thành công");
//        }
//        return Optional.of("Không tìm thấy id chất liệu");
//    }
//
//    public Optional<ChiTietSanPham> detail(int id) {
//        var u = chiTietSanPhamRepository.findById(id);
////        if (u.isPresent()) {
//        return Optional.of(u.get());
////        }
//
//    }
}
