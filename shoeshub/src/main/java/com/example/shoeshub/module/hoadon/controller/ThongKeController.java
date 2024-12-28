package com.example.shoeshub.module.hoadon.controller;

import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThongKeController {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @GetMapping("/thong-ke")
    public String thongKe(Model model) {
        long soLuongHoaDon = hoaDonRepository.count();
        double tongDoanhThu = hoaDonRepository.findAll().stream().mapToDouble(HoaDon::getTongtien).sum();
        model.addAttribute("soLuongHoaDon", soLuongHoaDon);
        model.addAttribute("tongDoanhThu", tongDoanhThu);
        return "thong-ke/index";
    }
}
