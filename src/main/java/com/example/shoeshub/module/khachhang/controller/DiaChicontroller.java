package com.example.shoeshub.module.khachhang.controller;

import com.example.shoeshub.module.khachhang.entity.DiaChi;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.khachhang.service.DiaChiService;
import com.example.shoeshub.module.khachhang.service.KhachHangService;
import com.example.shoeshub.module.khachhang.service.impl.DiaChiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager/khachhang")
public class DiaChicontroller {

    @Autowired
    private DiaChiService diaChiService;
    @Autowired
    private KhachHangService khachHangService;

    // Hiển thị danh sách địa chỉ của khách hàng
    @GetMapping("/{id}/addresses")
    public String viewAddresses(@PathVariable("id") Integer customerId, Model model) {
        KhachHang khachHang = khachHangService.findID(customerId);
        if (khachHang != null) {
            List<DiaChi> addresses = diaChiService.getAddressesByCustomerId(customerId);
            model.addAttribute("customer", khachHang);
            model.addAttribute("addresses", addresses);
        } else {
            model.addAttribute("error", "Khách hàng không tồn tại.");
        }
        return "manager/quan-ly-dia-chi"; // Trang hiển thị danh sách địa chỉ
    }

    @GetMapping("/{customerId}/addresses/delete/{addressId}")
    public String deleteAddress(@PathVariable("customerId") Integer customerId,
                                @PathVariable("addressId") Integer addressId,
                                Model model) {
        diaChiService.deleteAddress(addressId); // Gọi service để xóa địa chỉ

        // Sau khi xóa, trả về danh sách địa chỉ của khách hàng
        return "redirect:/manager/khachhang/" + customerId + "/addresses"; // Điều hướng lại về danh sách địa chỉ của khách hàng
    }

    @GetMapping("/diachi/{customerId}/addresses/add")
    public String showAddAddressForm(@PathVariable("customerId") Integer customerId, Model model) {
        KhachHang optionalCustomer = khachHangService.findID(customerId);

        if (optionalCustomer != null) {
            KhachHang customer = optionalCustomer;

            // Tạo một đối tượng DiaChi mới và thêm vào model
            DiaChi newAddress = new DiaChi();
            model.addAttribute("customer", customer);
            model.addAttribute("address", newAddress);  // Đảm bảo rằng đối tượng được đặt là "address"
        } else {
            model.addAttribute("errorMessage", "Customer not found.");
        }

        return "manager/them-dia-chi";  // Trả về trang form
    }


    @PostMapping("/diachi/{customerId}/addresses/add")
    public String addAddress(@PathVariable("customerId") Integer customerId,
                             DiaChi diaChi, Model model) {
        KhachHang khachHang = khachHangService.findID(customerId);
        if (khachHang != null) {
            diaChi.setKhachHang(khachHang); // Gán khách hàng cho địa chỉ
            diaChiService.save(diaChi); // Lưu địa chỉ vào database
            return "redirect:/manager/khachhang/" + customerId + "/addresses"; // Quay lại trang danh sách địa chỉ
        } else {
            model.addAttribute("error", "Khách hàng không tồn tại.");
            return "redirect:/manager/khachhang"; // Trở lại danh sách khách hàng
        }
    }
    // Các phương thức khác để quản lý địa chỉ có thể thêm vào nếu cần
}
