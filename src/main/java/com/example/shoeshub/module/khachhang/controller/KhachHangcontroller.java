package com.example.shoeshub.module.khachhang.controller;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.khachhang.service.KhachHangService;
import com.example.shoeshub.module.khachhang.service.impl.KhachHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager/khachhang")
public class KhachHangcontroller {
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping
    public String viewAllCustomers(Model model) {
        List<KhachHang> customers = khachHangService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "manager/quan-ly-khach-hang";
    }

    @PostMapping("/search")
    public String searchCustomers(@RequestParam("keyword") String keyword, Model model) {
        List<KhachHang> customers = khachHangService.searchCustomers(keyword);
        model.addAttribute("customers", customers);
        return "manager/quan-ly-khach-hang";
    }


    @GetMapping("/change-status/{id}")
    public String changeCustomerStatus(@PathVariable("id") Integer id) {
        int currentStatus = khachHangService.findID(id).getTrangthai();
        int newStatus = (currentStatus == 1) ? 0 : 1;
        khachHangService.updateCustomerStatus(id, newStatus);
        return "redirect:/manager/khachhang";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        KhachHang customerOptional = khachHangService.findID(id);
        if (customerOptional !=null) {
            model.addAttribute("customer", customerOptional);
            return "manager/edit-khach-hang";  // Trang để hiển thị form chỉnh sửa
        } else {
            return "redirect:/manager/khachhang"; // Nếu không tìm thấy khách hàng, quay lại danh sách
        }
    }

    // Xử lý lưu thông tin chỉnh sửa (chỉ cập nhật email và số điện thoại)
    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable("id") Integer id, @ModelAttribute KhachHang customer) {
        KhachHang existingCustomer = khachHangService.findID(id);
        if (existingCustomer !=null) {
            KhachHang updatedCustomer = existingCustomer;
            updatedCustomer.setEmail(customer.getEmail());
            updatedCustomer.setSdt(customer.getSdt());
            // Cập nhật chỉ email và số điện thoại
            khachHangService.save(updatedCustomer);
        }
        return "redirect:/manager/khachhang";  // Quay lại trang danh sách khách hàng
    }



}