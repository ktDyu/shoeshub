package com.example.shoeshub.module.hoadon.controller;


import com.example.shoeshub.module.hoadon.DTO.TopNguoiMuaHangDTO;
import com.example.shoeshub.module.hoadon.service.ThongKeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager")
public class ThongKeController {
    private final ThongKeService thongKeService;

    public ThongKeController(ThongKeService thongKeService) {
        this.thongKeService = thongKeService;
    }

    @GetMapping("/thongke")
    public String thongKeDoanhThu(
            @RequestParam(value = "year", required = false, defaultValue = "2024") int year,
            Model model) {
        Map<String, Long> thongKe = thongKeService.thongKeDoanhThuTheoTrangThai();

        List<TopNguoiMuaHangDTO> topNguoiMuaHang = thongKeService.getTopNguoiMuaHang();

        // Lấy doanh thu theo năm
        List<BigDecimal> doanhThuTheoThang = thongKeService.thongKeDoanhThuTheoNam(year);

        // Tạo danh sách các tháng
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }

        // Thêm dữ liệu vào model
        model.addAttribute("hoanThanh", thongKe.get("HoanThanh"));
        model.addAttribute("daHuy", thongKe.get("DaHuy"));

        model.addAttribute("doanhThuTheoThang", doanhThuTheoThang);
        model.addAttribute("months", months);
        model.addAttribute("year", year);
        model.addAttribute("topNguoiMuaHang", topNguoiMuaHang);

        return "manager/thong-ke"; // Chuyển về giao diện Thymeleaf
    }


}
