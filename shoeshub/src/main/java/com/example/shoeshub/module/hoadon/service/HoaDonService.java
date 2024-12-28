package com.example.shoeshub.module.hoadon.service;

import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;
//    @Autowired
//    private UserRepository userRepository;

    public List<HoaDon> getAllHoaDon() {
        return hoaDonRepository.findAll();
    }

    public HoaDon getHoaDonById(int id) {
        return hoaDonRepository.findById(id).orElse(null);
    }

//    @Autowired
//    private AuthService authService;

    public List<HoaDon> findHoaDonsByUsername(String username) {
        return null;
    }

    public HoaDon add(HoaDon hoaDon){
        return hoaDonRepository.save(hoaDon);
    }

}

