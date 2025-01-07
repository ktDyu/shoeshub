package com.example.shoeshub.module.buyer.controller;



import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.buyer.entity.GioHangChiTiet;
import com.example.shoeshub.module.buyer.service.CTSPViewModelService;
import com.example.shoeshub.module.buyer.entity.CTSPViewModel;
import com.example.shoeshub.module.buyer.service.GioHangChiTietService;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/buyer")
public class HomeController {

    private final CTSPViewModelService ctspViewModelService;
    private final HttpSession session;
    private final GioHangChiTietService ghctService;


    @RequestMapping(value = {"", "/", "/home"})
    public String home(Model model){
        KhachHang khachHang = (KhachHang) session.getAttribute("KhachHangLogin");
        if (khachHang != null){
            String fullName = khachHang.getTenkh();
            model.addAttribute("fullNameLogin", fullName);
            GioHang gioHang = (GioHang) session.getAttribute("GHLogged") ;

            int soThongBao = 0;
            List<GioHangChiTiet> listGHCTActive = ghctService.findByGHActive(gioHang);

            Integer sumProductInCart = listGHCTActive.size();
            model.addAttribute("sumProductInCart", sumProductInCart);
            model.addAttribute("heartLogged", true);
            model.addAttribute("soThongBao", soThongBao);
//            model.addAttribute("listThongBao", thongBaoKhachHangs);

        }else {
            model.addAttribute("messageLoginOrSignin", true);
        }

        List<CTSPViewModel> listCTSPModel = ctspViewModelService.getAll();
        model.addAttribute("listCTSPModel",listCTSPModel);
        return "online/index";

    }



}
