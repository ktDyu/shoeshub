package com.example.shoeshub.module.buyer.controller;

import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.buyer.entity.GioHangChiTiet;
import com.example.shoeshub.module.buyer.service.GioHangChiTietService;
import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import com.example.shoeshub.module.chitietsanpham.service.ChiTietSanPhamService;
import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.entity.HoaDonChiTiet;
import com.example.shoeshub.module.hoadon.service.HoaDonChiTietService;
import com.example.shoeshub.module.hoadon.service.HoaDonService;
import com.example.shoeshub.module.hoadon.service.impl.HoaDonChiTietServiceImpl;
import com.example.shoeshub.module.hoadon.service.impl.HoaDonServiceImpl;
import com.example.shoeshub.module.khachhang.entity.DiaChi;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.khachhang.service.DiaChiService;
import com.example.shoeshub.module.khachhang.service.KhachHangService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class CheckoutController {

    private final HttpServletRequest request;
    private final HttpSession session;
    private final GioHangChiTietService gioHangChiTietService;
    private final HoaDonService hoaDonService;
    private final HoaDonChiTietService hoaDonChiTietService;
    private final ChiTietSanPhamService chiTietSanPhamService;
    private final DiaChiService diaChiService;
    private final KhachHangService khachHangService;

    @PostMapping("/checkout")
    private String checkOutCart(Model model, @RequestParam("selectedProducts") List<Integer> selectedProductIds) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");
        GioHang gioHang = (GioHang) session.getAttribute("GHLogged");

        DiaChi diaChiDefault = diaChiService.findDCDefaulByKhachHang(khachHang);

        List<DiaChi> diaChiList = diaChiService.findbyKhachHangAndLoaiAndTrangThai(khachHang);


        List<GioHangChiTiet> listGHCTActive = gioHangChiTietService.findByGHActive(gioHang);

        List<HoaDonChiTiet> listHDCTCheckOut = new ArrayList<>();

        Integer sumProductInCart = listGHCTActive.size();

        //hoa don
        Date date = new Date();
        HoaDon hoaDon = new HoaDon();
        String tenhd = "HD_" + khachHang.getIdkh() + "_" + date.getDate() + generateRandomNumbers();
        hoaDon.setTenhd(tenhd);
        hoaDon.setKhachHang(khachHang);
        hoaDon.setTgtt(date);
        hoaDon.setTrangThai(0);
        hoaDonService.save(hoaDon);

        if (diaChiDefault != null) {
            hoaDon.setDiachinguoinhan(diaChiDefault.getDiachichitiet());
            hoaDon.setSdtnguoinhan(diaChiDefault.getSdtnguoinhan());
            hoaDon.setTennguoinhan(diaChiDefault.getTennguoinhan());
            session.removeAttribute("diaChiGiaoHang");
            session.setAttribute("diaChiGiaoHang", diaChiDefault);
            hoaDonService.save(hoaDon);
        }
        for (Integer x : selectedProductIds) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            GioHangChiTiet gioHangChiTiet = gioHangChiTietService.findByCTSPActiveAndTrangThai(chiTietSanPhamService.findId(x).orElse(null), gioHang);

            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setChiTietSanPham(chiTietSanPhamService.findId(x).orElse(null));
            hoaDonChiTiet.setDongia(gioHangChiTiet.getDongia());
            hoaDonChiTiet.setSoluong(gioHangChiTiet.getSoluong());
            hoaDonChiTiet.setTrangthai(1);

            hoaDonChiTietService.save(hoaDonChiTiet);

            listHDCTCheckOut.add(hoaDonChiTiet);
        }

        int sumQuantity = listHDCTCheckOut.stream()
                .mapToInt(HoaDonChiTiet::getSoluong)
                .sum();

        double total = listHDCTCheckOut.stream()
                .mapToDouble(HoaDonChiTiet::getDongia)
                .sum();

        if ((diaChiDefault == null) && (diaChiList == null || diaChiList.isEmpty())) {
            model.addAttribute("noAddress", true);
            model.addAttribute("addNewAddressNulll", true);
        } else if (diaChiDefault == null) {
            model.addAttribute("addNewAddressNulll", true);
            model.addAttribute("addNewAddressNull", true);
        } else {
            model.addAttribute("diaChiKHDefault", diaChiDefault);
            model.addAttribute("addNewAddressNotNull", true);
            model.addAttribute("listAddressKH", diaChiList);
        }

        hoaDon.setTongsp(sumQuantity);
        hoaDonService.save(hoaDon);

        model.addAttribute("maHD", hoaDon.getTenhd());
        model.addAttribute("sumQuantity", sumQuantity);
        model.addAttribute("total", total);
        model.addAttribute("listProductCheckOut", listHDCTCheckOut);
        model.addAttribute("fullNameLogin", khachHang.getTenkh());
        model.addAttribute("sumProductInCart", sumProductInCart);
        model.addAttribute("toTalOder", total);

        //
        if (diaChiDefault != null) {
            Double shippingFee = 25000.0;
            hoaDon.setTongtien(total + shippingFee);
            hoaDonService.save(hoaDon);
            model.addAttribute("shippingFee", shippingFee);
            model.addAttribute("toTalOder", total + shippingFee);
        }
        else {
            Double shippingFee = 0.0;
            model.addAttribute("shippingFee", shippingFee);
            model.addAttribute("toTalOder", total);
        }
        session.removeAttribute("hoaDonTaoMoi");

        session.setAttribute("hoaDonTaoMoi", hoaDon);

        return "online/checkout";
    }

    @PostMapping("/checkout/add/address")
    public String addNewAddressPlaceOrder(Model model, @RequestParam(name = "defaultSelected", defaultValue = "false") boolean defaultSelected) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");
        HoaDon hoaDon = (HoaDon) session.getAttribute("hoaDonTaoMoi");
        GioHang gioHang = (GioHang) session.getAttribute("GHLogged");


        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.findByHoaDon(hoaDon);
        List<DiaChi> diaChiKHList = diaChiService.findbyKhachHangAndLoaiAndTrangThai(khachHang);
        List<GioHangChiTiet> listGHCTActive = gioHangChiTietService.findByGHActive(gioHang);

        Integer sumProductInCart = listGHCTActive.size();
        Date date = new Date();

        if (defaultSelected) {
            for (DiaChi x : diaChiService.getAll()) {
                x.setLoaidiachi(false);
                diaChiService.save(x);
            }
        }

        String nameAddress = request.getParameter("nameAddress");
        String fullName = request.getParameter("fullName");
        String phoneAddress = request.getParameter("phoneAddress");
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String description = request.getParameter("description");
        String diaChiChiTiet = description + ", " + ward + ", " + district + ", " + city;

        //dia chi
        DiaChi diaChi = new DiaChi();
        diaChi.setDiachichitiet(diaChiChiTiet);
        diaChi.setTennguoinhan(fullName);
        diaChi.setSdtnguoinhan(phoneAddress);
        diaChi.setMota(description);
        diaChi.setKhachHang(khachHang);
        diaChi.setTrangthai(1);
        diaChi.setQuanhuyen(district);
        diaChi.setTendiachi(nameAddress);
        diaChi.setTinhtp(city);
        diaChi.setXaphuong(ward);
        diaChi.setLoaidiachi(defaultSelected);
        diaChiService.save(diaChi);

        hoaDon.setDiachinguoinhan(diaChiChiTiet);
        hoaDon.setTennguoinhan(fullName);
        hoaDon.setSdtnguoinhan(phoneAddress);
        hoaDonService.save(hoaDon);


        int sumQuantity = hoaDonChiTietList.stream()
                .mapToInt(HoaDonChiTiet::getSoluong)
                .sum();

        double total = hoaDonChiTietList.stream()
                .mapToDouble(HoaDonChiTiet::getDongia)
                .sum();


        Double shippingFee = 25000.0;
        hoaDon.setTongtien(total + shippingFee);
        hoaDonService.save(hoaDon);

        model.addAttribute("sumQuantity", sumQuantity);
        model.addAttribute("total", total);
        model.addAttribute("diaChiKHDefault", diaChi);
        model.addAttribute("listProductCheckOut", hoaDonChiTietList);
        model.addAttribute("listAddressKH", diaChiKHList);
        model.addAttribute("fullNameLogin", khachHang.getTenkh());
        model.addAttribute("sumProductInCart", sumProductInCart);
        model.addAttribute("addNewAddressNotNull", true);
        model.addAttribute("toTalOder", total + shippingFee);

        model.addAttribute("shippingFee", shippingFee);

        session.removeAttribute("diaChiGiaoHang");
        session.setAttribute("diaChiGiaoHang", diaChi);

        return "online/checkout";
    }

    @PostMapping("/checkout/change/address")
    private String changeAddressCheckOut(Model model) {

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");
        HoaDon hoaDon = (HoaDon) session.getAttribute("hoaDonTaoMoi");
        GioHang gioHang = (GioHang) session.getAttribute("GHLogged");

        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.findByHoaDon(hoaDon);


        List<GioHangChiTiet> listGHCTActive = gioHangChiTietService.findByGHActive(gioHang);

        int madc = Integer.parseInt(request.getParameter("madc"));
        DiaChi diaChiKHChange = diaChiService.findId(madc);

        List<DiaChi> diaChiKHList = diaChiService.findbyKhachHangAndTrangThai(khachHang);
        List<DiaChi> diaChiKHListFiltered = diaChiKHList.stream()
                .filter(diaChi -> diaChi.getMadc() != madc) // Loại bỏ địa chỉ được chọn
                .collect(Collectors.toList());

        int sumQuantity = hoaDonChiTietList.stream()
                .mapToInt(HoaDonChiTiet::getSoluong)
                .sum();
        double total = hoaDonChiTietList.stream()
                .mapToDouble(HoaDonChiTiet::getDongia)
                .sum();

        hoaDon.setTennguoinhan(diaChiKHChange.getTennguoinhan());
        hoaDon.setSdtnguoinhan(diaChiKHChange.getSdtnguoinhan());
        hoaDon.setDiachinguoinhan(diaChiKHChange.getDiachichitiet());
        hoaDonService.save(hoaDon);

        Double shippingFee = 25000.0;
        hoaDon.setTongtien(total + shippingFee);
        hoaDonService.save(hoaDon);


//      TODO PASSING DATA BEGIN
        model.addAttribute("sumQuantity", sumQuantity);
        model.addAttribute("total", total);
        model.addAttribute("diaChiKHDefault", diaChiKHChange);
        model.addAttribute("listProductCheckOut", hoaDonChiTietList);
        model.addAttribute("listAddressKH", diaChiKHListFiltered);
        model.addAttribute("fullNameLogin", khachHang.getTenkh());
        model.addAttribute("sumProductInCart", listGHCTActive.size());
        model.addAttribute("addNewAddressNotNull", true);

        model.addAttribute("shippingFee", shippingFee);


        model.addAttribute("toTalOder", total + shippingFee);

//      TODO PASSING DATA END
        session.removeAttribute("diaChiGiaoHang");
        session.setAttribute("diaChiGiaoHang", diaChiKHChange);

        return "online/checkout";
    }

    @PostMapping("/checkout/placeoder")
    public String placeOrder(Model model) {

        HoaDon hoaDon = (HoaDon) session.getAttribute("hoaDonTaoMoi");
        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        String hinhThucThanhToan = request.getParameter("hinhThucThanhToan");

        Double shippingFee = 25000.0;

        hoaDon.setTienship(shippingFee);
        hoaDon.getTongtien();

        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.findByHoaDon(hoaDon);

        for (HoaDonChiTiet xx : hoaDonChiTietList) {
            GioHangChiTiet gioHangChiTiet = gioHangChiTietService.findByCTSPActive(xx.getChiTietSanPham());
            gioHangChiTiet.setTrangthai(0);
            gioHangChiTietService.addGHCT(gioHangChiTiet);

            ChiTietSanPham chiTietSanPham = xx.getChiTietSanPham();
            chiTietSanPham.setSoluong(chiTietSanPham.getSoluong() - xx.getSoluong());
            chiTietSanPhamService.add(chiTietSanPham);
        }

        if (hinhThucThanhToan.equals("thanhToanKhiNhanHang")) {
            hoaDon.setHinhthucthanhtoan(0);
            hoaDon.setTrangThai(1);
            hoaDonService.save(hoaDon);

            UserForm(model);

            return "redirect:/buyer/purchase";

        } else {
            hoaDon.setHinhthucthanhtoan(1);
            hoaDon.setTrangThai(1);
            hoaDonService.save(hoaDon);

            UserForm(model);

            model.addAttribute("maHD", hoaDon.getTenhd());
            model.addAttribute("toTalOder", hoaDon.getTongtien());
            model.addAttribute("thongTinThanhToan", true);

            model.addAttribute("addNewAddressNull", true);
            model.addAttribute("addNewAddressNulll", false);

            return "online/checkout";
        }

    }

    private void UserForm(Model model) {
        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");
        GioHang gioHang = (GioHang) session.getAttribute("GHLogged");
        model.addAttribute("fullNameLogin", khachHang.getTenkh());

        List<GioHangChiTiet> listGHCTActive = gioHangChiTietService.findByGHActive(gioHang);
        Integer sumProductInCart = listGHCTActive.size();
        model.addAttribute("sumProductInCart", sumProductInCart);

        session.removeAttribute("hoaDonTaoMoi");
        session.removeAttribute("diaChiGiaoHang");
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
//    @PostMapping("/payment/callback")
//    public ResponseEntity<?> handlePaymentCallback(@RequestBody PaymentNotification notification) {
//        HoaDon hoaDon = hoaDonService.findByMaHD(notification.getMaHD());
//        if (hoaDon != null && notification.getStatus().equals("SUCCESS")) {
//            hoaDon.setTrangThai(1); // Đã thanh toán
//            hoaDonService.add(hoaDon);
//            return ResponseEntity.ok("Thanh toán thành công.");
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thanh toán thất bại.");
//    }


}
