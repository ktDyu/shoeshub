package com.example.shoeshub.module.danhmuc.repository;

import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {
    Optional<DanhMuc> findByTendanhmuc(String tenDanhMuc);
    List<DanhMuc> findAllByTrangthai(int trangThai);
}
