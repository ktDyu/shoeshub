
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
            @RequestParam(value = "year", required = false, defaultValue = "2025") int year,
            Model model) {
        Map<String, Long> thongKe = thongKeService.thongKeDoanhThuTheoTrangThai();
        List<TopNguoiMuaHangDTO> topNguoiMuaHang = thongKeService.getTopNguoiMuaHang();

        // Lấy dữ liệu thống kê doanh thu
        Map<String, Object> thongKeNam = thongKeService.thongKeDoanhThuTheoNam(year);
        List<BigDecimal> doanhThuTheoThang = (List<BigDecimal>) thongKeNam.get("doanhThuTheoThang");
        long soLuongDonHang = (long) thongKeNam.get("soLuongDonHang");
        BigDecimal tongDoanhThu = (BigDecimal) thongKeNam.get("tongDoanhThu");

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
        model.addAttribute("tongDoanhThu", tongDoanhThu);
        model.addAttribute("soLuongDonHang", soLuongDonHang);

        return "manager/thong-ke";
    }



}
