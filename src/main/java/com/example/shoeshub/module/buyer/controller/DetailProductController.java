package com.example.shoeshub.module.buyer.controller;

import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.buyer.entity.GioHangChiTiet;
import com.example.shoeshub.module.buyer.service.CTSPViewModelService;
import com.example.shoeshub.module.buyer.service.GioHangChiTietService;
import com.example.shoeshub.module.buyer.service.GioHangService;
import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import com.example.shoeshub.module.chitietsanpham.service.ChiTietSanPhamService;
import com.example.shoeshub.module.hinhanh.entity.HinhAnh;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.mausac.service.MauSacService;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import com.example.shoeshub.module.sanpham.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/buyer")
public class DetailProductController {
    private final SanPhamService sanPhamService;
    private final ChiTietSanPhamService chiTietSanPhamService;
    private final MauSacService mauSacService;
    private final HttpSession session;
    private final CTSPViewModelService ctspViewModelService;
    private final GioHangChiTietService gioHangChiTietService;
    private final GioHangService gioHangService;

    @GetMapping("/shop-details/{idGiay}/{idMau}")
    public String getFormDetail(Model model, @PathVariable int idGiay, @PathVariable int idMau){
        //
        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        SanPham sanPham = sanPhamService.findId(idGiay).orElse(null);
        MauSac mauSac = mauSacService.findId(idMau).orElse(null);

        if (sanPham == null){
            sanPham = sanPhamService.findId(idMau).orElse(null);
            mauSac = mauSacService.findId(idGiay).orElse(null);
        }
        checkKHLogged(model, khachHang);

        // danh sach giay
        List<ChiTietSanPham> listCTSPByGiay = chiTietSanPhamService.findBySanPhamAndMauSac(sanPham, mauSac,1, 0);
        List<ChiTietSanPham> listCTSPByGiaySold = chiTietSanPhamService.findBySanPhamAndMauSacSold(sanPham, mauSac,1, 0);
        //size
        List<Object[]> allSizeByGiay = new ArrayList<>();
        List<Object[]> allSizeByGiaySold = new ArrayList<>();

        String showReceiveMail = "true";
        for (ChiTietSanPham x : listCTSPByGiay) {
            if (x.getSoluong()>0){
                showReceiveMail = "false";
            }
            allSizeByGiay.add(new Object[] { x.getSize().getKichthuoc(), x.getMactsp(), showReceiveMail});
        }
        for (ChiTietSanPham x : listCTSPByGiaySold) {
            if (x.getSoluong()==0){
                showReceiveMail = "true";
            }
            allSizeByGiaySold.add(new Object[] { x.getSize().getKichthuoc(), x.getMactsp(), showReceiveMail});
        }
        allSizeByGiay.addAll(allSizeByGiaySold);
        allSizeByGiay.sort(Comparator.comparingInt(obj -> ((Integer) obj[0])));


        List<MauSac> listMauSacByGiay = chiTietSanPhamService.findDistinctMauSacByGiay(sanPham);
        if (listMauSacByGiay.size() == 1){
            model.addAttribute("CTGBy1Mau", true);
            model.addAttribute("tenMau", mauSac.getTenmau());
        }else {
            model.addAttribute("CTGByMoreMau", true);
            model.addAttribute("listMauSacByGiay", listMauSacByGiay);
        }

        //Infor begin
        Optional<Float> maxPriceByGiay = listCTSPByGiay.stream()
                .map(ChiTietSanPham :: getDongia)
                .max(Float :: compare);

        Float maxPrice = maxPriceByGiay.get();

        int sumCTSPByGiay = listCTSPByGiay.stream()
                .mapToInt(ChiTietSanPham::getSoluong)
                .sum();

        Optional<Float> minPriceByGiay = listCTSPByGiay.stream()
                .map(ChiTietSanPham :: getDongia)
                .min(Float :: compare);

        Float minPrice = minPriceByGiay.get();

        //Infor end

        HinhAnh hinhAnhByGiayAndMau = chiTietSanPhamService.findDistinctHinhAnhByGiayAndMau(sanPham, mauSac);

        model.addAttribute("product", sanPham);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sunProductAvaible", sumCTSPByGiay);
        model.addAttribute("hinhAnh", hinhAnhByGiayAndMau);
        model.addAttribute("idHeartMau", mauSac.getMams());
        model.addAttribute("listMauSacByGiay", listMauSacByGiay);
        model.addAttribute("listSizeCTG", allSizeByGiay);
        model.addAttribute("listGiavaSize", listCTSPByGiay);

        return "online/detail-product";
    }

    @GetMapping("/shop/buyNowButton")
    public String buyNow(@RequestParam("idDetailProduct") int idDProduct, @RequestParam("quantity") int quantity, Model model){

        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findId(idDProduct).orElse(null);

        GioHang gioHang = (GioHang) session.getAttribute("GHLogged") ;

        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.findByCTSPActive(chiTietSanPham);

        if (gioHangChiTiet != null){
            gioHangChiTiet.setSoluong(gioHangChiTiet.getSoluong() + quantity);
            gioHangChiTiet.setDongia(quantity * chiTietSanPham.getDongia() + gioHangChiTiet.getDongia());
            gioHangChiTietService.addGHCT(gioHangChiTiet);
        }else {
            GioHangChiTiet gioHangChiTietNew = new GioHangChiTiet();
            gioHangChiTietNew.setChiTietSanPham(chiTietSanPham);
            gioHangChiTietNew.setGioHang(gioHang);
            gioHangChiTietNew.setSoluong(quantity);
            gioHangChiTietNew.setDongia(quantity * chiTietSanPham.getDongia());
            gioHangChiTietNew.setTrangthai(1);
            gioHangChiTietService.addGHCT(gioHangChiTietNew);
        }

        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");

        List<GioHangChiTiet> listGHCTActive = gioHangChiTietService.findByGHActive(gioHang);

        Integer sumProductInCart = listGHCTActive.size();
        String idBuyNow = String.valueOf(chiTietSanPham.getMactsp());

        model.addAttribute("fullNameLogin", khachHang.getTenkh());
        model.addAttribute("sumProductInCart", sumProductInCart);
        model.addAttribute("listCartDetail", listGHCTActive);
        model.addAttribute(idBuyNow, true);
        model.addAttribute("chiTietGiay", chiTietSanPham);

        return "/online/shopping-cart";

    }

    @GetMapping("/shop/addProductCart")
    public String handleAddToCart(@RequestParam("idDetailProduct") int idDProduct, @RequestParam("quantity") int quantity, Model model) {

        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findId(idDProduct).orElse(null);

        GioHang gioHang = (GioHang) session.getAttribute("GHLogged") ;

        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.findByCTSPActive(chiTietSanPham);

        if (gioHangChiTiet != null){
            gioHangChiTiet.setSoluong(gioHangChiTiet.getSoluong() + quantity);
            gioHangChiTiet.setDongia(quantity * chiTietSanPham.getDongia() + gioHangChiTiet.getDongia());
            gioHangChiTietService.addGHCT(gioHangChiTiet);
        }else {
            GioHangChiTiet gioHangChiTietNew = new GioHangChiTiet();
            gioHangChiTietNew.setChiTietSanPham(chiTietSanPham);
            gioHangChiTietNew.setGioHang(gioHang);
            gioHangChiTietNew.setSoluong(quantity);
            gioHangChiTietNew.setDongia(quantity * chiTietSanPham.getDongia());
            gioHangChiTietNew.setTrangthai(1);
            gioHangChiTietService.addGHCT(gioHangChiTietNew);

        }

        String idGiay = String.valueOf(chiTietSanPham.getSanPham().getMasp());

        String idMau = String.valueOf(chiTietSanPham.getMauSac().getMams());

        String link = idGiay +"/" +idMau;
        return "redirect:/buyer/shop-details/" + link;
    }

    public void checkKHLogged(Model model, KhachHang khachHang){
        if (khachHang != null){
            String fullName = khachHang.getTenkh();
            model.addAttribute("fullNameLogin", fullName);
            GioHang gioHang = (GioHang) session.getAttribute("GHLogged") ;

            List<GioHangChiTiet> listGHCTActive = gioHangChiTietService.findByGHActive(gioHang);
            model.addAttribute("heartLogged", true);

            Integer sumProductInCart = listGHCTActive.size();
            model.addAttribute("sumProductInCart", sumProductInCart);
            model.addAttribute("buyNowAddCartLogged", true);

        }else {
            model.addAttribute("messageLoginOrSignin", true);
        }
    }


}
