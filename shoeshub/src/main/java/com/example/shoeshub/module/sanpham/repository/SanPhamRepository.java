package com.example.shoeshub.module.sanpham.repository;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import com.example.shoeshub.module.sanpham.response.SanPhamResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {

    Optional<SanPham> findByTensp(String ten);

    Optional<SanPham> findByMasp(Integer masp);

    Optional<SanPham> findByDanhmuc(DanhMuc danhMuc);

    Optional<SanPham> findByTrangthai(int trangThai);

    List<SanPham> findAllByTrangthai(int trangThai);
}
