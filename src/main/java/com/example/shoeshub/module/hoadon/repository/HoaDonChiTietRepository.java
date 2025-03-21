package com.example.shoeshub.module.hoadon.repository;

import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet,Integer> {
    List<HoaDonChiTiet> findByHoaDon(HoaDon hoaDon);
    @Query("SELECT SUM(hdct.soluong) FROM HoaDonChiTiet hdct WHERE hdct.chiTietSanPham.mactsp = :mactsp AND hdct.trangthai = 1")
    Integer getTotalQuantitySoldByProductId(int mactsp);

}
