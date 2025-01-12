package com.example.shoeshub.module.buyer.controller;

import com.example.shoeshub.module.buyer.entity.CTSPViewModel;
import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.buyer.entity.GioHangChiTiet;
import com.example.shoeshub.module.buyer.service.CTSPViewModelService;
import com.example.shoeshub.module.buyer.service.GioHangChiTietService;
import com.example.shoeshub.module.chatlieu.service.ChatLieuService;
import com.example.shoeshub.module.chitietsanpham.service.ChiTietSanPhamService;
import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import com.example.shoeshub.module.danhmuc.service.DanhMucService;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.mausac.service.MauSacService;
import com.example.shoeshub.module.sanpham.service.SanPhamService;
import com.example.shoeshub.module.size.entity.Size;
import com.example.shoeshub.module.size.service.SizeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class ShopController {
    private final SanPhamService sanPhamService;
    private final ChiTietSanPhamService chiTietSanPhamService;
    private final MauSacService mauSacService;
    private final SizeService sizeService;
    private final DanhMucService danhMucService;
    private final ChatLieuService chatLieuService;
    private final GioHangChiTietService gioHangChiTietService;
    private final HttpSession session;
    private final CTSPViewModelService ctspViewModelService;
    private final HttpServletRequest request;

    @PostMapping("/search")
    private String searchBuyer(Model model){
        checkKhachHangLogged(model);
        List<CTSPViewModel> listCTGModel = ctspViewModelService.getAll();
        String keyWord = request.getParameter("keyWord");
        String[] words = keyWord.split(" ");

        List<CTSPViewModel> ctgViewModelList = new ArrayList<>();

        for (String word : words) {
            for (CTSPViewModel xx:listCTGModel) {
                if (xx.getTensp().toLowerCase().contains(word.toLowerCase())){
                    ctgViewModelList.add(xx);
                }
            }
        }

        if (ctgViewModelList.size() == 0){
            model.addAttribute("khongThay", true);
        }
        model.addAttribute("listCTSPModel", ctgViewModelList);
        model.addAttribute("keyword", keyWord);

        showDataBuyerShop(model);
        return "online/shop";
    }

    @GetMapping("/shop")
    private String getShopBuyer(Model model,
                                @RequestParam(name= "pageSize", defaultValue = "9") Integer pageSize,
                                @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum){
        showDataBuyerShop(model);
        checkKhachHangLogged(model);

        List<CTSPViewModel> listCTGModelSold = ctspViewModelService.getAllSold();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<CTSPViewModel> page = ctspViewModelService.getAllPage(pageable);

        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listCTSPModel", page.getContent());
        //
        model.addAttribute("listCTSPModelSoldOff", listCTGModelSold);
        model.addAttribute("pageNumber", true);
        model.addAttribute("showPage", true);
        return "online/shop";
    }

    @GetMapping("/shop/brand/{madm}")
    private String getShopBrandBuyer(Model model,@PathVariable int madm){
        showDataBuyerShop(model);
        checkKhachHangLogged(model);

        List<CTSPViewModel> listCTGByBrand = ctspViewModelService.findByIDDanhMuc(madm);
        int sumProductByHang = listCTGByBrand.size();
        model.addAttribute("sumProduct", sumProductByHang);

        model.addAttribute("listCTSPModel", listCTGByBrand);

//        List<CTSPViewModel> listCTGModelSoldOff = ctspViewModelService.getAllSoldOff();
        model.addAttribute("listCTSPModelSoldOff", listCTGByBrand);
        return "online/shop";
    }

    @GetMapping("/shop/price/type=1")
    private String getShopPriceType1(Model model){

        checkKhachHangLogged(model);

        List<CTSPViewModel> listCTGModel = ctspViewModelService.getAll();

        List<CTSPViewModel> ctgViewModelList = listCTGModel.stream()
                .filter(xx -> xx.getMindongia() != null && xx.getMindongia() < 1_000_000.0)
                .collect(Collectors.toList());

//        model.addAttribute("listCTGModelSoldOff", listCTGModel);
        model.addAttribute("pageNumber", true);
        model.addAttribute("listCTSPModel", ctgViewModelList);

        showDataBuyerShop(model);
        return "online/shop";
    }

    @GetMapping("/shop/price/type=2")
    private String getShopPriceType2(Model model){

        checkKhachHangLogged(model);

        List<CTSPViewModel> listCTGModel = ctspViewModelService.getAll();

        List<CTSPViewModel> ctgViewModelList = new ArrayList<>();


        for (CTSPViewModel xx:listCTGModel) {
            if (xx.getMindongia() >= 1000000.0 && xx.getMindongia() <= 2000000.0){
                ctgViewModelList.add(xx);
            }
        }

//        model.addAttribute("listCTGModelSoldOff", listCTGModel);
        model.addAttribute("pageNumber", true);
        model.addAttribute("listCTSPModel", ctgViewModelList);

        showDataBuyerShop(model);
        return "online/shop";
    }

    @GetMapping("/shop/price/type=3")
    private String getShopPriceType3(Model model){

        checkKhachHangLogged(model);

        List<CTSPViewModel> listCTGModel = ctspViewModelService.getAll();

        List<CTSPViewModel> ctgViewModelList = new ArrayList<>();

        for (CTSPViewModel xx:listCTGModel) {
            if (xx.getMindongia() >= 2000000.0 && xx.getMindongia() <= 3000000.0){
                ctgViewModelList.add(xx);
            }
        }

        model.addAttribute("listCTGModelSoldOff", listCTGModel);
        model.addAttribute("pageNumber", true);
        model.addAttribute("listCTSPModel", ctgViewModelList);

        showDataBuyerShop(model);
        return "online/shop";
    }

    @GetMapping("/shop/price/type=4")
    private String getShopPriceType4(Model model){

        checkKhachHangLogged(model);

        List<CTSPViewModel> listCTGModel = ctspViewModelService.getAll();

        List<CTSPViewModel> ctgViewModelList = new ArrayList<>();

        for (CTSPViewModel xx:listCTGModel) {
            if (xx.getMindongia() >= 3000000.0 && xx.getMindongia() <= 5000000.0){
                ctgViewModelList.add(xx);
            }
        }

        model.addAttribute("listCTSPModelSoldOff", listCTGModel);
        model.addAttribute("pageNumber", true);
        model.addAttribute("listCTSPModel", ctgViewModelList);

        showDataBuyerShop(model);
        return "online/shop";
    }

    @GetMapping("/shop/price/type=5")
    private String getShopPriceType5(Model model){

        checkKhachHangLogged(model);

        List<CTSPViewModel> listCTGModel = ctspViewModelService.getAll();

        List<CTSPViewModel> ctgViewModelList = new ArrayList<>();

        for (CTSPViewModel xx:listCTGModel) {
            if (xx.getMindongia() >= 5000000.0 && xx.getMindongia() <= 8000000.0){
                ctgViewModelList.add(xx);
            }
        }

        model.addAttribute("listCTSPModelSoldOff", listCTGModel);
        model.addAttribute("pageNumber", true);
        model.addAttribute("listCTSPModel", ctgViewModelList);

        showDataBuyerShop(model);
        return "online/shop";
    }

    private void showDataBuyerShop(Model model){

        List<DanhMuc> listDanhMuc = danhMucService.getAllTrangThai(1);
        model.addAttribute("listDanhMuc", listDanhMuc);

        List<Size> listSize = sizeService.getAllTrangThai(1);
        model.addAttribute("listSize", listSize);

        List<MauSac> listColor = mauSacService.getAllTrangThai(1);
        model.addAttribute("listMauSac", listColor);

        List<CTSPViewModel> listCTSPModel = ctspViewModelService.getAll();
        model.addAttribute("sumProduct", listCTSPModel.size());

//        List<CTSPViewModel> ctgViewModelList = new ArrayList<>();
//
//        for (CTSPViewModel xx :listCTSPModel) {
//                ctgViewModelList.add(xx);
//        }
//        session.removeAttribute("listCTGModel");
    }

    private void checkKhachHangLogged(Model model){
        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");
        if (khachHang != null){
            String fullName = khachHang.getTenkh();
            model.addAttribute("fullNameLogin", fullName);

            GioHang gioHang = (GioHang) session.getAttribute("GHLogged") ;

            List<GioHangChiTiet> listGHCTActive = gioHangChiTietService.findByGHActive(gioHang);

            Integer sumProductInCart = listGHCTActive.size();
            model.addAttribute("heartLogged", true);
            model.addAttribute("sumProductInCart", sumProductInCart);

        }else {
            model.addAttribute("messageLoginOrSignin", true);
        }
    }

}
