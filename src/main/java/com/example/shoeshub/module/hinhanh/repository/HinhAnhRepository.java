package com.example.shoeshub.module.hinhanh.repository;



import com.example.shoeshub.module.hinhanh.entity.HinhAnh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HinhAnhRepository extends JpaRepository<HinhAnh, Integer> {
    List<HinhAnh> findAllByTrangthai(int trangThai);
}
