package com.example.shoeshub.module.buyer.entity;

import com.example.shoeshub.module.khachhang.entity.KhachHang;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gio_hang")
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "magh")
    private Integer magh;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangthai;

    // Quan hệ One-to-One với KhachHang
    @OneToOne(mappedBy = "gioHang") // Mối quan hệ One-to-One từ phía KhachHang
    private KhachHang khachHang;

    // Getters và Setters
}
