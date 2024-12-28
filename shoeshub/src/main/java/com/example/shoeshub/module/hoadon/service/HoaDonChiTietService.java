package com.example.shoeshub.module.hoadon.service;

import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.entity.HoaDonChiTiet;
import com.example.shoeshub.module.hoadon.repository.HoaDonChiTietRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HoaDonChiTietService {

    private  final HoaDonChiTietRepository hoaDonChiTietRepository;

    public HoaDonChiTiet add(HoaDonChiTiet hoaDonChiTiet){
        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }


    public List<HoaDonChiTiet> findByHoaDon(HoaDon hoaDon) {
        return hoaDonChiTietRepository.findByHoaDon(hoaDon);
    }
}
