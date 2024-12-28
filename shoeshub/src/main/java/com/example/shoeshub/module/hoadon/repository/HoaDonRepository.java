package com.example.shoeshub.module.hoadon.repository;

import com.example.shoeshub.module.hoadon.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon,Integer> {

}
