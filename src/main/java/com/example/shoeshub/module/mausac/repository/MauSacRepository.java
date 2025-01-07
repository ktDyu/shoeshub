package com.example.shoeshub.module.mausac.repository;

import com.example.shoeshub.module.mausac.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    Optional<MauSac> findByTenmau(String tenMau);
    List<MauSac> findAllByTrangthai(int trangThai);
}
