package com.example.shoeshub.module.khachhang.repo;

import com.example.shoeshub.module.khachhang.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    KhachHang findByEmailAndPasswordAndTrangthai(String email, String password, int trangthai);
    KhachHang findByEmail(String email);

}
