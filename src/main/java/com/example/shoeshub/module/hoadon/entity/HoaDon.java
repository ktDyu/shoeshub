package com.example.shoeshub.module.hoadon.entity;

import com.example.shoeshub.module.khachhang.entity.KhachHang;
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

import java.util.Date;

@Entity
@Table(name = "hoa_don")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HoaDon {

    @Id
    @Column(name = "mahd")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mahd;

    @Column(name = "tong_tien")
    private double tongtien;

    @Column(name = "tong_sp")
    private int tongsp;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "ma_kh", referencedColumnName = "makh")
    private KhachHang khachHang;

    @Column(name = "tgtt")
    private Date tgtt;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "tenhd")
    private String tenhd;

    @Column(name = "diachinguoinhan")
    private String diachinguoinhan;

    @Column(name = "sdtnguoinhan")
    private String sdtnguoinhan;

    @Column(name = "tennguoinhan")
    private String tennguoinhan;

    @Column(name = "tienship")
    private double tienship;

    @Column(name = "hinhthucthanhtoan")
    private Integer hinhthucthanhtoan;

    @Column(name = "tghuy")
    private Date tghuy;

    @Column(name = "lydohuy")
    private String lydohuy;
}
