package com.example.shoeshub.module.hoadon.repository;

import com.example.shoeshub.module.hoadon.DTO.TopNguoiMuaHangDTO;
import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon,Integer> {
    List<HoaDon> findByKhachHang(KhachHang khachHang);
    List<HoaDon> findByKhachHangAndTrangThai(KhachHang khachHang, int trangthai);
    List<HoaDon> findByTgttBetween(Date startDate, Date endDate);

    @Query("SELECT h FROM HoaDon h WHERE " +
            "(:keyword IS NULL OR h.tenhd LIKE %:keyword% OR h.tennguoinhan LIKE %:keyword% OR h.diachinguoinhan LIKE %:keyword%) " +
            "ORDER BY h.tgtt desc ")
    List<HoaDon> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT SUM(h.tongtien) FROM HoaDon h WHERE DATE(h.tgtt) = :date")
    BigDecimal thongKeDoanhThuTheoNgay(@Param("date") Date date);

    // Thống kê theo tuần
    @Query("""
        SELECT SUM(h.tongtien) 
        FROM HoaDon h 
        WHERE h.tgtt BETWEEN :startOfWeek AND :endOfWeek
    """)
    BigDecimal thongKeDoanhThuTheoTuan(@Param("startOfWeek") Date startOfWeek, @Param("endOfWeek") Date endOfWeek);

    // Thống kê theo tháng
    @Query("""
        SELECT SUM(h.tongtien) 
        FROM HoaDon h 
        WHERE YEAR(h.tgtt) = :year AND MONTH(h.tgtt) = :month
    """)
    BigDecimal thongKeDoanhThuTheoThang(@Param("year") int year, @Param("month") int month);


    @Query("""
    SELECT new com.example.shoeshub.module.hoadon.DTO.TopNguoiMuaHangDTO(kh.tenkh, SUM(hd.tongtien))
    FROM HoaDon hd
    JOIN hd.khachHang kh
    WHERE hd.trangThai = 3
    GROUP BY kh.makh, kh.tenkh
    ORDER BY SUM(hd.tongtien) DESC
""")
    List<TopNguoiMuaHangDTO> findTopNguoiMuaHang();

    List<HoaDon> findByTgtt(Date tgtt);

    List<HoaDon> findByTennguoinhanContainingOrSdtnguoinhanContaining(String tennguoinhan, String sdtnguoinhan);

    long countByTrangThai(int trangThai);

    List<HoaDon> findByTrangThai(Integer trangThai);

//    @Query("SELECT h.mahd, h.tongsp, h.tongtien, h.hinhthucthanhtoan,h.tgtt,h. FROM HoaDon h " +
//            "ORDER BY h.tgtt desc ")
//    List<HoaDon> finALLL();


}
