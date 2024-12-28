package com.example.shoeshub.module.chitietsanpham.repository;

import com.example.shoeshub.module.buyer.entity.CTSPViewModel;
import com.example.shoeshub.module.buyer.entity.SPViewModel;
import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import com.example.shoeshub.module.hinhanh.entity.HinhAnh;
import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    List<ChiTietSanPham> findBySanPhamAndMauSac(SanPham sanPham, MauSac mauSac);

    List<ChiTietSanPham> findBySanPhamAndMauSacAndTrangthaiAndSoluongGreaterThan(SanPham sanPham, MauSac mauSac, int trangthai, int soluong);
    List<ChiTietSanPham> findBySanPhamAndMauSacAndTrangthaiAndSoluong(SanPham sanPham, MauSac mauSac, int trangthai, int soluong);


    @Query(value = "SELECT DISTINCT ctg.hinhAnh FROM ChiTietSanPham ctg WHERE ctg.sanPham = ?1 AND ctg.mauSac = ?2")
    HinhAnh findDistinctHinhAnhByGiay(SanPham sanPham, MauSac mauSac);

    @Query(value = "SELECT DISTINCT ctg.mauSac FROM ChiTietSanPham ctg WHERE ctg.sanPham = ?1 AND ctg.trangthai = 1 ")
    List<MauSac> findDistinctMauSacByGiayAndTrangThai(SanPham sanPham);


    //check trung
    @Query("SELECT c FROM ChiTietSanPham c " +
            "WHERE c.sanPham.masp = :masp AND c.size.masize = :masize " +
            "AND c.mauSac.mams = :mams AND c.chatLieu.macl = :macl " +
            "AND c.hinhAnh.maanh = :maanh AND c.dongia = :dongia " +
            "AND c.mactsp <> :mactsp") // Loại trừ bản ghi hiện tại
    List<ChiTietSanPham> findDuplicateForUpdate(
            @Param("masp") int masp,
            @Param("masize") int masize,
            @Param("mams") int mams,
            @Param("macl") int macl,
            @Param("maanh") int maanh,
            @Param("dongia") float dongia,
            @Param("mactsp") int mactsp);

    @Query("SELECT c FROM ChiTietSanPham c " +
            "WHERE c.sanPham.masp = :masp AND c.size.masize = :masize " +
            "AND c.mauSac.mams = :mams AND c.chatLieu.macl = :macl " +
            "AND c.hinhAnh.maanh = :maanh AND c.dongia = :dongia")
    List<ChiTietSanPham> findDuplicateForAdd(
            @Param("masp") int masp,
            @Param("masize") int masize,
            @Param("mams") int mams,
            @Param("macl") int macl,
            @Param("maanh") int maanh,
            @Param("dongia") float dongia);

    //buyer
    @Query("""
            SELECT NEW com.example.shoeshub.module.buyer.entity.CTSPViewModel
            ( ctsp.sanPham.masp, ctsp.mauSac.mams, ctsp.hinhAnh.maanh, SUM(ctsp.soluong) ,MIN(ctsp.dongia),MAX(ctsp.dongia), sp.tensp, ms.tenmau, ha.url1)
            FROM ChiTietSanPham ctsp 
            LEFT JOIN ctsp.sanPham sp 
            LEFT JOIN ctsp.mauSac ms 
            LEFT JOIN ctsp.hinhAnh ha
            WHERE ctsp.sanPham.masp IS NOT NULL
            AND ctsp.hinhAnh.maanh IS NOT NULL
            AND sp.trangthai = 1
            AND ctsp.trangthai = 1
            
            GROUP BY ctsp.sanPham.masp, ctsp.mauSac.mams, ctsp.hinhAnh.maanh, sp.tensp, ms.tenmau, ha.url1
            """)
    List<CTSPViewModel> getAll();

    @Query("""
            SELECT NEW com.example.shoeshub.module.buyer.entity.CTSPViewModel
            ( ctsp.sanPham.masp, ctsp.mauSac.mams, ctsp.hinhAnh.maanh, SUM(ctsp.soluong) ,MIN(ctsp.dongia),MAX(ctsp.dongia), sp.tensp, ms.tenmau, ha.url1)
            FROM ChiTietSanPham ctsp 
            LEFT JOIN ctsp.sanPham sp 
            LEFT JOIN ctsp.mauSac ms 
            LEFT JOIN ctsp.hinhAnh ha
            WHERE ctsp.sanPham.masp IS NOT NULL
            AND ctsp.hinhAnh.maanh IS NOT NULL
            AND sp.trangthai = 1
            AND ctsp.trangthai = 1
            
            GROUP BY ctsp.sanPham.masp, ctsp.mauSac.mams, ctsp.hinhAnh.maanh, sp.tensp, ms.tenmau, ha.url1
            """)
    Page<CTSPViewModel> getAllPage(Pageable pageable);

    @Query("""
            SELECT NEW com.example.shoeshub.module.buyer.entity.SPViewModel
            (ha.url1, sp.tensp, SUM(ctsp.soluong), MIN(ctsp.dongia), MAX(ctsp.dongia))
            FROM ChiTietSanPham ctsp
            INNER JOIN ctsp.sanPham sp
            INNER JOIN ctsp.hinhAnh ha
            WHERE sp.trangthai = 1
            AND ctsp.trangthai = 1
            GROUP BY ha.url1, sp.tensp
            """)
    List<SPViewModel> getAllGiay();

    @Query("""
            SELECT NEW com.example.shoeshub.module.buyer.entity.CTSPViewModel
            ( ctsp.sanPham.masp, ctsp.mauSac.mams, ctsp.hinhAnh.maanh, SUM(ctsp.soluong) ,MIN(ctsp.dongia),MAX(ctsp.dongia), sp.tensp, ms.tenmau, ha.url1)
            FROM ChiTietSanPham ctsp 
            LEFT JOIN ctsp.sanPham sp 
            LEFT JOIN ctsp.mauSac ms 
            LEFT JOIN ctsp.hinhAnh ha
            WHERE ctsp.sanPham.masp IS NOT NULL
            AND ctsp.hinhAnh.maanh IS NOT NULL
            AND sp.trangthai = 1
            AND ctsp.trangthai = 1
            AND sp.danhmuc.madm = ?1
            GROUP BY ctsp.sanPham.masp, ctsp.mauSac.mams, ctsp.hinhAnh.maanh, sp.tensp, ms.tenmau, ha.url1
            """)

    List<CTSPViewModel> findByDanhMuc(int madm);



//    @Query("""
//            SELECT NEW com.example.shoeshub.module.chitietsanpham.entity.CTSPViewModel
//                                          (ctsp.sanPham.masp,
//                                           ctsp.mauSac.mams,
//                                           ctsp.hinhAnh.maanh,
//                                           SUM(ctsp.soluong),
//                                           NULL,
//                                           sp.tensp,\s
//                                           NULL,
//                                           ha.url1)
//                                      FROM ChiTietSanPham ctsp
//                                      INNER JOIN ctsp.sanPham sp\s
//                                      INNER JOIN ctsp.hinhAnh ha
//                                      WHERE sp.trangthai = 1
//                                        AND ctsp.trangthai = 1
//                                      GROUP BY ctsp.sanPham.masp, ctsp.mauSac.mams, ctsp.hinhAnh.maanh, sp.tensp, ha.url1
//            """)
//    List<SPViewModel> getAllGiay1();

}
