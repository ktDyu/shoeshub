package com.example.shoeshub.module.khachhang.entity;

import com.example.shoeshub.module.buyer.entity.GioHang;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "khach_hang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "makh")
    private Integer makh;

    @Column(name = "ten_kh", nullable = false)
    private String tenkh;

    @Column(name = "sdt", nullable = false)
    private String sdt;

    @Column(name = "gioi_tinh")
    private boolean gioitinh;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "ngay_sinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaysinh;

    @Column(name = "mat_khau", nullable = false)
    private String password;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangthai;

    @Column(name = "idkh", unique = true, nullable = false)
    private String idkh;

    // Quan hệ One-to-One với GioHang
    @OneToOne(cascade = CascadeType.ALL) // Áp dụng Cascade để tự động lưu KhachHang
    @JoinColumn(name = "magh") // Khoá ngoại liên kết với KhachHang
    private GioHang gioHang;

}
