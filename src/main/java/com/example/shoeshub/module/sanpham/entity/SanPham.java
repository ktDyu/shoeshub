package com.example.shoeshub.module.sanpham.entity;

import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SAN_PHAM")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_sp")
    private Integer masp;

    @Column(name = "tensp")
    private String tensp;

    @Column(name = "trang_thai")
    private Integer trangthai;

    @ManyToOne
    @JoinColumn(name = "ma_dm" , referencedColumnName = "ma_dm")
    private DanhMuc danhmuc;
}
