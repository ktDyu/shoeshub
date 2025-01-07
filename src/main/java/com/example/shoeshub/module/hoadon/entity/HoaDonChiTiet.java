package com.example.shoeshub.module.hoadon.entity;


import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet {

    @Id
    @Column(name = "mahdct")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mahdct;

    @ManyToOne
    @JoinColumn(name = "mahd", referencedColumnName = "mahd")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "mactsp", referencedColumnName = "mactsp")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "so_luong")
    private int soluong;

    @Column(name = "don_gia")
    private float dongia;

    @Column(name = "trang_thai")
    private Integer trangthai;

}