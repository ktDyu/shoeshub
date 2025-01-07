package com.example.shoeshub.module.buyer.entity;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gio_hang_chi_tiet")
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maghct")
    private Integer maghct;

    @ManyToOne
    @JoinColumn(name = "ma_ctsp", referencedColumnName = "mactsp")
    private ChiTietSanPham chiTietSanPham;

    @ManyToOne
    @JoinColumn(name = "ma_gh", referencedColumnName = "magh")
    private GioHang gioHang;

    @Column(name = "don_gia")
    private Float dongia;

    @Column(name = "so_luong")
    private Integer soluong;

    @Column(name = "trang_thai")
    private Integer trangthai;
}
