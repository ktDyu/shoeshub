package com.example.shoeshub.module.khachhang.service.impl;

import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.khachhang.repo.KhachHangRepository;
import com.example.shoeshub.module.khachhang.service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public KhachHang findID(int makh) {
        return khachHangRepository.findById(makh).orElse(null);
    }


    @Override
    public List<KhachHang> searchCustomers(String keyword) {
        return khachHangRepository.findByTenkhContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSdtContaining(keyword, keyword, keyword);
    }

    @Override
    public List<KhachHang> getAllCustomers() {
        return khachHangRepository.findAll();
    }

    @Override
    public void updateCustomerStatus(Integer id, int status) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng với ID: " + id));
        khachHang.setTrangthai(status);
        khachHangRepository.save(khachHang);
    }
}
