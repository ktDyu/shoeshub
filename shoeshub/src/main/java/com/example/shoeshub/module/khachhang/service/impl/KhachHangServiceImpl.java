package com.example.shoeshub.module.khachhang.service.impl;

import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.khachhang.repo.KhachHangRepository;
import com.example.shoeshub.module.khachhang.service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangRepository khachHangRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Override
    public KhachHang checkLoginEmailAndPassword(String username, String rawPassword) {
        KhachHang khachHang = khachHangRepository.findByEmail(username);

        boolean isPasswordMatch = passwordEncoder.matches(rawPassword, khachHang.getPassword());

        if (isPasswordMatch) {
            System.out.println("Password matches!");
        } else {
            System.out.println("Password does not match!");
        }
        // Kiểm tra xem người dùng có tồn tại và mật khẩu có khớp không
        if (khachHang != null && passwordEncoder.matches(rawPassword, khachHang.getPassword())) {
            return khachHang;
        }
        // Nếu không khớp, trả về null
        return null;
    }

    @Override
    public KhachHang checkEmail(String email) {
        return khachHangRepository.findByEmail(email);
    }

    @Override
    public KhachHang save(KhachHang khachHang) {
        String encryptedPassword = passwordEncoder.encode(khachHang.getPassword());
        khachHang.setPassword(encryptedPassword);
        return khachHangRepository.save(khachHang);
    }
}
