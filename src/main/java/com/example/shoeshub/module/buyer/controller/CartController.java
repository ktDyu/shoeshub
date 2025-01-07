package com.example.shoeshub.module.buyer.controller;

import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.buyer.entity.GioHangChiTiet;
import com.example.shoeshub.module.buyer.service.GioHangChiTietService;
import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import com.example.shoeshub.module.chitietsanpham.service.ChiTietSanPhamService;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.mausac.service.MauSacService;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import com.example.shoeshub.module.sanpham.service.SanPhamService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class CartController {

    private final HttpServletRequest request;

    private final HttpSession session;

    private final GioHangChiTietService gioHangChiTietService;

    private final ChiTietSanPhamService chiTietSanPhamService;

//    private final HoaDonService hoaDonService;

//    private final HoaDonChiTietService hoaDonChiTietService;

    private final MauSacService mauSacService;

    private final SanPhamService sanPhamService;

    @GetMapping("/cart")
    private String getShoppingCart(Model model){
        model.addAttribute("reLoadPageCart", true);
        showDataBuyer(model);

        return "/online/shopping-cart";
    }

    @PostMapping("/cart/updateQuantity")
    @ResponseBody
    public void updateQuantity(@RequestParam int mactsp, @RequestParam int quantity) {

        GioHang gioHang = (GioHang) session.getAttribute("GHLogged") ;
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findId(mactsp).orElse(null);

        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.findByCTSPActiveAndTrangThai(chiTietSanPham, gioHang);
        gioHangChiTiet.setSoluong(quantity);
        gioHangChiTiet.setDongia( quantity * chiTietSanPham.getDongia());
        gioHangChiTietService.addGHCT(gioHangChiTiet);

    }

    @GetMapping("/cart/delete/{mactsp}")
    private String deleteInCard(Model model, @PathVariable int mactsp){

        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findId(mactsp).orElse(null);
        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.findByCTSPActive(chiTietSanPham);
        gioHangChiTiet.setTrangthai(0);
        gioHangChiTietService.addGHCT(gioHangChiTiet);
        showDataBuyer(model);

        return "redirect:/buyer/cart";
    }

    @GetMapping("/cart/option/{masp}/{mams}/{mactsp}")
    private String getOptionProduct(Model model, @PathVariable int masp, @PathVariable int mams, @PathVariable int mactsp){


        MauSac mauSac = mauSacService.findId(mams).orElse(null);
        SanPham sanPham = sanPhamService.findId(masp).orElse(null);

        if(mauSac == null){
            sanPham = sanPhamService.findId(mams).orElse(null);
            mauSac = mauSacService.findId(masp).orElse(null);
        }

        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findId(mactsp).orElse(null);

        session.removeAttribute("ctgChangeSize");
        session.setAttribute("ctgChangeSize", chiTietSanPham);

        List<ChiTietSanPham> listCTGByGiay = chiTietSanPhamService.findByGiayAndMau(sanPham, mauSac);

//        List<GioHangChiTiet> listGHCTActive = ghctService.findByGHActive(idProuduct);
//        Integer sumProductInCart = listGHCTActive.size();

        model.addAttribute("reLoadPage", true);
        showDataBuyer(model);
        model.addAttribute("showModalChooseSize", true);
        model.addAttribute("nameProduct", sanPham.getTensp());
        model.addAttribute("listCTGByGiay", listCTGByGiay);
        return "/online/shopping-cart";
    }

    @PostMapping("/cart/change/size")
    private String changeSize(Model model,@RequestParam("selectedValues") List<Integer> selectedValues){

        ChiTietSanPham chiTietSanPham = (ChiTietSanPham) session.getAttribute("ctgChangeSize");

        int idCTGChangeSize = selectedValues.get(0);

        ChiTietSanPham chiTietGiayChangeSize = chiTietSanPhamService.findId(idCTGChangeSize).orElse(null);

        GioHang gioHang = (GioHang) session.getAttribute("GHLogged") ;

        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.findByCTSPActiveAndTrangThai(chiTietSanPham, gioHang);

        gioHangChiTiet.setChiTietSanPham(chiTietGiayChangeSize);
        gioHangChiTiet.setDongia(gioHangChiTiet.getSoluong() * chiTietSanPham.getDongia());
        gioHangChiTietService.addGHCT(gioHangChiTiet);

        showDataBuyer(model);
        return "/online/shopping-cart";
    }



    private void showDataBuyer(Model model){
        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");
        GioHang gioHang = (GioHang) session.getAttribute("GHLogged") ;

        List<GioHangChiTiet> listGHCTActive = gioHangChiTietService.findByGHActive(gioHang);
        Integer sumProductInCart = listGHCTActive.size();

        if (listGHCTActive != null){
            for (GioHangChiTiet gioHangChiTiet: listGHCTActive) {
                gioHangChiTiet.setDongia(gioHangChiTiet.getChiTietSanPham().getDongia()* gioHangChiTiet.getSoluong());
                gioHangChiTietService.addGHCT(gioHangChiTiet);
            }
        }
        model.addAttribute("fullNameLogin", khachHang.getTenkh());
        model.addAttribute("sumProductInCart", sumProductInCart);
        model.addAttribute("listCartDetail", listGHCTActive);
    }

}
