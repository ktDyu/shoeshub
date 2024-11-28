package com.example.shoeshub.module.chitietsanpham.repository;

import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {
//    Optional<ChiTietSanPham> findByTensp(String ten);
//
//    Optional<ChiTietSanPham> findByMasp(Integer masp);
//
//    Optional<ChiTietSanPham> findByDanhmuc(DanhMuc danhMuc);
//
//    Optional<ChiTietSanPham> findByTrangthai(int trangThai);

    @Query("SELECT c FROM ChiTietSanPham c WHERE c.sanPham.masp = :masp AND c.size.masize = :masize AND c.mauSac.mams = :mams AND c.chatLieu.macl = :macl AND c.hinhAnh.maanh = :maanh")
    List<ChiTietSanPham> findBySanPhamAndSizeAndMauSacAndChatLieuAndHinhAnh(
            @Param("masp") int masp,
            @Param("masize") int masize,
            @Param("mams") int mams,
            @Param("macl") int macl,
            @Param("maanh") int maanh
    );
}
