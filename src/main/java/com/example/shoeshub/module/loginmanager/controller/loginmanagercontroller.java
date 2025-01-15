package com.example.shoeshub.module.loginmanager.controller;

import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.khachhang.service.impl.KhachHangServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager")
public class loginmanagercontroller {

    @Autowired
    private KhachHangServiceImpl khachHangServiceImpl;

    private boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(EMAIL_REGEX);
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "manager/login";
    }

    @PostMapping("/login")
    public String managerLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model, HttpSession session) {

        // Email được cấp quyền đăng nhập
        String authorizedEmail = "thuandepzai12122004@gmail.com";

        // Kiểm tra email có phải là email được cấp quyền không
        if (!username.equals(authorizedEmail)) {
            model.addAttribute("messageLogin", "Bạn không có quyền truy cập.");
            return "manager/login";
        }

        // Kiểm tra định dạng email
        if (!isValidEmail(username)) {
            model.addAttribute("messageLogin", "Định dạng email không hợp lệ");
            return "manager/login";
        }

        // Xác thực tài khoản và mật khẩu
        KhachHang kh = khachHangServiceImpl.checkLoginEmailAndPassword(username, password);
        if (kh != null) {
            session.setAttribute("ManagerLogin", kh);
            model.addAttribute("fullNameLogin", kh.getTenkh());
            return "redirect:/manager/hoa-don";
        } else {
            model.addAttribute("messageLogin", "Tài khoản hoặc mật khẩu không chính xác");
            return "manager/login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/manager/login";
    }
}
