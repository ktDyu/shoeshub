package com.example.shoeshub.module.khachhang.repo;

import com.example.shoeshub.module.khachhang.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    KhachHang findByEmailAndPasswordAndTrangthai(String email, String password, int trangthai);

    KhachHang findByEmail(String email);

    List<KhachHang> findByTenkhContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSdtContaining(String tenkh, String email, String sdt);

    Optional<KhachHang> findById(Integer id); // Phương thức tìm kiếm theo ID
}
