package com.example.shoeshub.module.hoadon.controller;

import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.service.HoaDonService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;



    @GetMapping("/hoa-don")
    public String getHoaDons(Model model, HttpSession session) {
        // Lấy thông tin username từ session
        String username = (String) session.getAttribute("username");
        // Lấy danh sách hóa đơn của user theo username
        List<HoaDon> hoaDons = hoaDonService.findHoaDonsByUsername(username);
        model.addAttribute("hoaDons", hoaDons);

        return "/manager/hoa-don"; // Tên file HTML
    }


    @GetMapping("/hoa-don/{id}")
    public String detailHoaDon(@PathVariable int id, Model model) {
        model.addAttribute("hoaDon", hoaDonService.getHoaDonById(id));
        return "manager/hoa-don-detail";
    }
}
