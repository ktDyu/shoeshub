package com.example.shoeshub.module.khachhang.entity;


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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dia_chi")
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "madc")
    private int madc;

    @Column(name = "loaidiachi")
    private boolean loaidiachi;

    @Column(name = "trang_thai")
    private int trangthai;

    @ManyToOne
    @JoinColumn(name = "makh", referencedColumnName = "makh")
    private KhachHang khachHang;

    @Column(name = "tendiachi")
    private String tendiachi;

    @Column(name = "tinh_tp")
    private String tinhtp;

    @Column(name = "quan_huyen")
    private String quanhuyen;

    @Column(name = "xa_phuong")
    private String xaphuong;

    @Column(name = "mota")
    private String mota;

    @Column(name = "diachichitiet")
    private String diachichitiet;

    @Column(name = "tennguoinhan")
    private String tennguoinhan;

    @Column(name = "sdtnguoinhan")
    private String sdtnguoinhan;


}
