package com.example.shoeshub.module.buyer.controller;


import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.buyer.entity.GioHangChiTiet;
import com.example.shoeshub.module.buyer.service.GioHangChiTietService;
import com.example.shoeshub.module.buyer.service.GioHangService;
import com.example.shoeshub.module.chitietsanpham.service.ChiTietSanPhamService;
import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.service.HoaDonChiTietService;
import com.example.shoeshub.module.hoadon.service.HoaDonService;
import com.example.shoeshub.module.khachhang.entity.DiaChi;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.khachhang.service.DiaChiService;
import com.example.shoeshub.module.khachhang.service.KhachHangService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class UserController {

    private final HttpSession session;

    private final GioHangChiTietService gioHangChiTietService;

    private final HttpServletRequest request;

    private final HoaDonChiTietService hoaDonChiTietService;

    private final HoaDonService hoaDonService;

    private final DiaChiService diaChiKHService;

    private final GioHangService gioHangService;

    private final ChiTietSanPhamService chiTietSanPhamService;

    private final KhachHangService khachHangService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/setting")
    private String getSettingAccount(Model model) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        UserForm(model, khachHang);
        model.addAttribute("userLogin", khachHang);
        model.addAttribute("pagesettingAccount", true);
//        Timestamp timestamp = Timestamp.valueOf(khachHang.getNgaysinh());
//
//        // Convert to LocalDate
//        LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();

        // Add to the model
//        model.addAttribute("ngaySinh", localDate);

        model.addAttribute("ngaysinh", khachHang.getNgaysinh());
        return "online/user";
    }

    @PostMapping("/users/update")
    public String updateUser(@RequestParam String gioitinh,
                             @RequestParam String ngaysinh,
                             @RequestParam String sdt) throws ParseException {
        KhachHang existingKhachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        // Cập nhật thông tin
        existingKhachHang.setGioitinh(Boolean.parseBoolean(gioitinh));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // Chuyển đổi String sang Date
        Date date = format.parse(ngaysinh);
        existingKhachHang.setNgaysinh(date);

        existingKhachHang.setSdt(sdt);
        khachHangService.save1(existingKhachHang);

        return "redirect:/buyer/setting";
    }

    @PostMapping("/users/updatepassword")
    public String updatePassword(@RequestParam String password,
                                 @RequestParam String passwordnew) {
        KhachHang existingKhachHang = (KhachHang) session.getAttribute("KhachHangLogin");


        if (!passwordEncoder.matches(password, existingKhachHang.getPassword())) {
            return "redirect:/buyer/setting";
//            model.addAttribute("detailBillPay", true);
        }
        // Cập nhật mật khẩu mới
        existingKhachHang.setPassword(passwordEncoder.encode(passwordnew));

        khachHangService.save1(existingKhachHang);

        return "redirect:/buyer/setting";
    }


    @GetMapping("/addresses")
    private String getAddressAccount(Model model) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        UserForm(model, khachHang);

        DiaChi diaChiKHDefault = diaChiKHService.findDCDefaulByKhachHang(khachHang);
        List<DiaChi> diaChiKHList = diaChiKHService.findbyKhachHangAndLoaiAndTrangThai(khachHang);

        model.addAttribute("pageAddressesUser", true);
        model.addAttribute("addNewAddressSetting", true);

        if (diaChiKHDefault == null) {
            model.addAttribute("diaChiShowNull", true);
        } else {
            model.addAttribute("diaChiShow", true);
            model.addAttribute("addressKHDefault", diaChiKHDefault);
            model.addAttribute("listCartDetail", diaChiKHList);

        }

        return "online/user";
    }

    @PostMapping("/addresses/add")
    private String addnewAddress(Model model, @RequestParam(name = "defaultSelected", defaultValue = "false") boolean defaultSelected) {
        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        String nameAddress = request.getParameter("nameAddress");
        String fullName = request.getParameter("fullName");
        String phoneAddress = request.getParameter("phoneAddress");
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String description = request.getParameter("description");

        String diaChiChiTiet = description + ", " + ward + ", " + district + ", " + city;

        DiaChi diaChiKH = new DiaChi();
        diaChiKH.setDiachichitiet(diaChiChiTiet);
        diaChiKH.setMota(description);
        diaChiKH.setKhachHang(khachHang);
        diaChiKH.setTrangthai(1);
        diaChiKH.setSdtnguoinhan(phoneAddress);
        diaChiKH.setQuanhuyen(district);
        diaChiKH.setTendiachi(nameAddress);
        diaChiKH.setTinhtp(city);
        diaChiKH.setTennguoinhan(fullName);
        diaChiKH.setXaphuong(ward);
        diaChiKH.setLoaidiachi(defaultSelected);

        diaChiKHService.save(diaChiKH);

        return "redirect:/buyer/addresses";
    }

    @GetMapping("/addresses/edit/{madc}")
    public String editAddress(@PathVariable int madc, Model model) {

        DiaChi diaChi = diaChiKHService.findId(madc);
        model.addAttribute("editAddressSetting", true);

        if (diaChi != null) {
            model.addAttribute("diaChi", diaChi);
        } else {
            model.addAttribute("error", "Address not found.");
        }

        return "online/addresses-up";
    }

    @PostMapping("/addresses/update/{madc}")
    public String updateAddress(@PathVariable int madc, @RequestParam String nameAddress, @RequestParam String fullName,
                                @RequestParam String phoneAddress, @RequestParam String city, @RequestParam String district,
                                @RequestParam String ward, @RequestParam String description,
                                @RequestParam(name = "defaultSelected", defaultValue = "false") boolean defaultSelected) {
        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");
        DiaChi diaChiKHDefaultList = diaChiKHService.findDCDefaulByKhachHang(khachHang);


        DiaChi diaChi = diaChiKHService.findId(madc);
        diaChi.setTendiachi(nameAddress);
        diaChi.setTennguoinhan(fullName);
        diaChi.setSdtnguoinhan(phoneAddress);
        diaChi.setTinhtp(city);
        diaChi.setQuanhuyen(district);
        diaChi.setXaphuong(ward);
        diaChi.setMota(description);
        if (defaultSelected == true) {
            diaChiKHDefaultList.setLoaidiachi(false);
            diaChiKHService.save(diaChiKHDefaultList);
            diaChi.setLoaidiachi(true);
        }

        String diaChiChiTiet = description + ", " + ward + ", " + district + ", " + city;
        diaChi.setDiachichitiet(diaChiChiTiet);
        diaChiKHService.save(diaChi);
        return "redirect:/buyer/addresses";
    }

    @GetMapping("/addresses/delete/{madc}")
    private String deleteAddress(Model model, @PathVariable int madc) {

        DiaChi diaChiKH = diaChiKHService.findId(madc);
        diaChiKH.setTrangthai(0);
        diaChiKHService.save(diaChiKH);

        return "redirect:/buyer/addresses";
    }

    @GetMapping("/addresses/setDefault/{madc}")
    private String setDefaultAddress(Model model, @PathVariable int madc) {
        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        DiaChi diaChiKH = diaChiKHService.findId(madc);
        DiaChi diaChiKHDefaultList = diaChiKHService.findDCDefaulByKhachHang(khachHang);

        diaChiKHDefaultList.setLoaidiachi(false);
        diaChiKHService.save(diaChiKHDefaultList);

        diaChiKH.setLoaidiachi(true);
        diaChiKHService.save(diaChiKH);

        return "redirect:/buyer/addresses";
    }

    @GetMapping("/notification")
    private String getNotidicationAccount(Model model) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        UserForm(model, khachHang);

        model.addAttribute("pageNotificationUser", true);
        return "online/user";
    }

    @GetMapping("/purchase")
    private String getPurchaseAccount(Model model) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        UserForm(model, khachHang);

        List<HoaDon> listHoaDonByKhachHang = hoaDonService.findHoaDonByKhachHang(khachHang);

        List<HoaDon> listHoaDonChoThanhToan = hoaDonService.findByKhachHangAndTrangThai(khachHang, 0);

        model.addAttribute("pagePurchaseUser", true);
        model.addAttribute("purchaseAll", true);
        model.addAttribute("listAllHDByKhachHang", listHoaDonByKhachHang);
        model.addAttribute("listHoaDonChoThanhToan", listHoaDonChoThanhToan);

        model.addAttribute("type1", "active");

        return "online/user";
    }

    @GetMapping("/purchase/pay")
    private String getPurchasePay(Model model) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        UserForm(model, khachHang);

        List<HoaDon> listHoaDonByKhachHang = hoaDonService.findByKhachHangAndTrangThai(khachHang, 0);

        model.addAttribute("listAllHDByKhachHang", listHoaDonByKhachHang);

        model.addAttribute("pagePurchaseUser", true);
        model.addAttribute("purchasePay", true);
        model.addAttribute("type2", "active");
        return "online/user";
    }

    @GetMapping("/purchase/ship")
    private String getPurchaseShip(Model model) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        UserForm(model, khachHang);

        List<HoaDon> listHoaDonByKhachHang = hoaDonService.findByKhachHangAndTrangThai(khachHang, 1);


        model.addAttribute("listAllHDByKhachHang", listHoaDonByKhachHang);
        model.addAttribute("pagePurchaseUser", true);
        model.addAttribute("purchaseShip", true);
        model.addAttribute("type3", "active");

        return "online/user";
    }

    @GetMapping("/purchase/receive")
    private String getPurchaseReceive(Model model) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        UserForm(model, khachHang);

        List<HoaDon> listHoaDonByKhachHang = hoaDonService.findByKhachHangAndTrangThai(khachHang, 2);

        model.addAttribute("listAllHDByKhachHang", listHoaDonByKhachHang);

        model.addAttribute("pagePurchaseUser", true);
        model.addAttribute("purchaseReceive", true);
        model.addAttribute("type4", "active");
        return "online/user";
    }

    @GetMapping("/purchase/completed")
    private String getPurchaseCompleted(Model model) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        UserForm(model, khachHang);

        List<HoaDon> listHoaDonByKhachHang = hoaDonService.findByKhachHangAndTrangThai(khachHang, 3);

        model.addAttribute("listAllHDByKhachHang", listHoaDonByKhachHang);

        model.addAttribute("pagePurchaseUser", true);
        model.addAttribute("purchaseCompleted", true);
        model.addAttribute("type5", "active");
        return "online/user";
    }

    @GetMapping("/purchase/cancel")
    private String getPurchaseCancel(Model model) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        UserForm(model, khachHang);

        List<HoaDon> listHoaDonByKhachHang = hoaDonService.findByKhachHangAndTrangThai(khachHang, 4);

        model.addAttribute("listAllHDByKhachHang", listHoaDonByKhachHang);

        model.addAttribute("pagePurchaseUser", true);
        model.addAttribute("purchaseCancel", true);
        model.addAttribute("type6", "active");
        return "online/user";
    }

    @GetMapping("/purchase/bill/detail/{mahd}")
    private String getDetailForm(Model model, @PathVariable int mahd) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");
        List<DiaChi> diaChiKHList = diaChiKHService.findbyKhachHangAndLoaiAndTrangThai(khachHang);
        DiaChi diaChiKHDefault = diaChiKHService.findDCDefaulByKhachHang(khachHang);

        UserForm(model, khachHang);

        HoaDon hoaDon = hoaDonService.findById(mahd);

        int trangThai = hoaDon.getTrangThai();
        if (trangThai == 0) {
            model.addAttribute("detailBillPay", true);
            model.addAttribute("modalThayDoiPhuongThucThanhToan", true);
            model.addAttribute("billDetailPay", hoaDon);

        } else if (trangThai == 1) {

            model.addAttribute("modalThayDoiPhuongThucThanhToan", true);
            model.addAttribute("detailBillShip", true);
            model.addAttribute("billDetailShip", hoaDon);

        } else if (trangThai == 2) {

            model.addAttribute("detailBillRecieve", true);
            model.addAttribute("billDetailRecieve", hoaDon);

        } else if (trangThai == 3) {

            model.addAttribute("detailBillCompleted", true);
            model.addAttribute("billDetailCompleted", hoaDon);
        } else if (trangThai == 4) {
            model.addAttribute("detailBillCancel", true);
            model.addAttribute("billDetailCancel", hoaDon);

        }

        model.addAttribute("listAddressKH", diaChiKHList);
        model.addAttribute("diaChiKHDefault", diaChiKHDefault);

        return "online/user";
    }

    @GetMapping("/purchase/bill/detail/cancel/{mahd}")
    private String getDetailCancelForm(Model model, @PathVariable int mahd) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");
        List<DiaChi> diaChiKHList = diaChiKHService.findbyKhachHangAndLoaiAndTrangThai(khachHang);
        DiaChi diaChiKHDefault = diaChiKHService.findDCDefaulByKhachHang(khachHang);

        UserForm(model, khachHang);

        HoaDon hoaDon = hoaDonService.findById(mahd);

        int trangThai = hoaDon.getTrangThai();
        if (trangThai == 0) {
            model.addAttribute("modalHuyHoaDonInDetailBillPay", true);
            model.addAttribute("modalThayDoiPhuongThucThanhToan", true);

            model.addAttribute("detailBillPay", true);
            model.addAttribute("billDetailPay", hoaDon);


        } else if (trangThai == 1) {
            model.addAttribute("modalHuyHoaDonInDetailBillPay", true);
            model.addAttribute("modalThayDoiPhuongThucThanhToan", true);

            model.addAttribute("detailBillShip", true);
            model.addAttribute("billDetailShip", hoaDon);


        } else if (trangThai == 2) {

            model.addAttribute("detailBillRecieve", true);
            model.addAttribute("billDetailRecieve", hoaDon);

        } else if (trangThai == 3) {

            model.addAttribute("detailBillCompleted", true);
            model.addAttribute("billDetailCompleted", hoaDon);
        } else if (trangThai == 4) {

            model.addAttribute("detailBillCancel", true);
            model.addAttribute("billDetailCancel", hoaDon);
        }

        model.addAttribute("listAddressKH", diaChiKHList);
        model.addAttribute("diaChiKHDefault", diaChiKHDefault);

        return "online/user";
    }

    @PostMapping("/purchase/pay/change/payment/{mahd}")
    private String changePaymentMethod(Model model, @PathVariable int mahd) {

        HoaDon hoaDon = (HoaDon) session.getAttribute("hoaDonPayDetail");
        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        String hinhThucThayDoi = request.getParameter("paymentMethod");

        if (hinhThucThayDoi.equals("qrCodeBanking")) {
            UserForm(model, khachHang);
            hoaDon.setHinhthucthanhtoan(1);
            hoaDon.setTrangThai(0);
            hoaDonService.save(hoaDon);

            return "redirect:/buyer/purchase/pay";
        } else {
            UserForm(model, khachHang);

            hoaDon.setHinhthucthanhtoan(0);
            hoaDon.setTrangThai(1);
            hoaDonService.save(hoaDon);

            return "redirect:/buyer/purchase/ship";
        }

    }

    @PostMapping("/purchase/bill/pay/cancel/{mahd}")
    private String cancelBillPay(Model model, @PathVariable int mahd) {
        String lyDoHuy = request.getParameter("lydohuy");

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        HoaDon hoaDonHuy = hoaDonService.findById(mahd);

        if (lyDoHuy.equals("changeSize")) {
            lyDoHuy = "Tôi muốn thay đổi size/ màu";
            hoaDonHuy.setLydohuy(lyDoHuy);
            hoaDonHuy.setTghuy(new Date());
            hoaDonHuy.setTrangThai(4);
            hoaDonService.save(hoaDonHuy);


        } else if (lyDoHuy.equals("changeProduct")) {
            lyDoHuy = "Tôi muốn thay đổi sản phẩm";
            hoaDonHuy.setLydohuy(lyDoHuy);
            hoaDonHuy.setTghuy(new Date());
            hoaDonHuy.setTrangThai(4);
            hoaDonService.save(hoaDonHuy);

        } else if (lyDoHuy.equals("none")) {
            lyDoHuy = "Tôi không  có nhu cầu mua nữa";
            hoaDonHuy.setLydohuy(lyDoHuy);
            hoaDonHuy.setTghuy(new Date());
            hoaDonHuy.setTrangThai(4);
            hoaDonService.save(hoaDonHuy);

        } else if (lyDoHuy.equals("lyDoKhac")) {
            lyDoHuy = "Lý do khác";
            hoaDonHuy.setTrangThai(4);
            hoaDonHuy.setLydohuy(lyDoHuy);
            hoaDonHuy.setTghuy(new Date());
        }

        return "redirect:/buyer/purchase/cancel";

    }

    @GetMapping("/purchase/bill/complete/{mahd}")
    private String completeBill(@PathVariable int mahd) {
        HoaDon hoaDon = hoaDonService.findById(mahd);
        hoaDon.setTrangThai(3);
        hoaDonService.save(hoaDon);
        return "redirect:/buyer/purchase/receive";
    }


    private void UserForm(Model model, KhachHang khachHang) {
        GioHang gioHang = (GioHang) session.getAttribute("GHLogged");
        model.addAttribute("fullNameLogin", khachHang.getTenkh());

        List<GioHangChiTiet> listGHCTActive = gioHangChiTietService.findByGHActive(gioHang);
        Integer sumProductInCart = listGHCTActive.size();
        model.addAttribute("sumProductInCart", sumProductInCart);
    }

    public String generateRandomNumbers() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int randomNumber = random.nextInt(10); // Tạo số ngẫu nhiên từ 0 đến 9
            sb.append(randomNumber);
        }
        return sb.toString();
    }

}

