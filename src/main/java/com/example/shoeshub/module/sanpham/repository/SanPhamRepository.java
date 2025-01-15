package com.example.shoeshub.module.sanpham.repository;

import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import com.example.shoeshub.module.sanpham.response.SanPhamResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT new com.example.shoeshub.module.sanpham.response.SanPhamResponse(" +
            "sp.masp, sp.tensp, sp.trangthai, dm.tendanhmuc, SUM(ctsp.soluong)) " +
            "FROM SanPham sp " +
            "LEFT JOIN sp.danhmuc dm " +
            "LEFT JOIN ChiTietSanPham ctsp ON sp.masp = ctsp.sanPham.masp " +
            "GROUP BY sp.masp, sp.tensp, sp.trangthai, dm.tendanhmuc " +
            "ORDER BY sp.masp")
    List<SanPhamResponse> findSanPhamWithTotalQuantityAndCategoryName();
}
