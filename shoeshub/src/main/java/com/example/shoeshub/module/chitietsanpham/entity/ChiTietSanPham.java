package com.example.shoeshub.module.chitietsanpham.entity;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.hinhanh.entity.HinhAnh;
import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import com.example.shoeshub.module.size.entity.Size;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CHI_TIET_SAN_PHAM")
public class ChiTietSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCTSP")
    private Integer mactsp;

//    @NotNull
    @Column(name = "so_luong")
    private Integer soluong;

//    @NotNull
    @Column(name = "don_gia")
    private Float dongia;

    @Column(name = "trang_thai")
    private Integer trangthai;

    @ManyToOne
    @JoinColumn(name = "ma_anh", referencedColumnName = "ma_anh")
    private HinhAnh hinhAnh;

    @ManyToOne
    @JoinColumn(name = "ma_sp", referencedColumnName = "ma_sp")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "ma_size", referencedColumnName = "ma_size")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "ma_ms", referencedColumnName = "ma_ms")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "ma_cl", referencedColumnName = "macl")
    private ChatLieu chatLieu;

    @Column(name = "mo_ta")
    private String mota;
}
