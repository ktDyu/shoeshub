package com.example.shoeshub.module.buyer.controller;

import com.example.shoeshub.module.buyer.entity.GioHang;
import com.example.shoeshub.module.buyer.service.GioHangService;
import com.example.shoeshub.module.buyer.service.SendMailService;
import com.example.shoeshub.module.khachhang.entity.KhachHang;
import com.example.shoeshub.module.khachhang.service.KhachHangService;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class AuthController {

    private final KhachHangService khachHangService;
    private final HttpServletRequest request;
    private final GioHangService gioHangService;
    private final HttpSession session;
    private final SendMailService sendMailService;

    private Random random = new Random();

    @GetMapping("/login")
    public String getFormBuyerLogin(){
        return "online/login";
    }

    @PostMapping("/login")
    private String buyerLogin(Model model, HttpSession session) throws MessagingException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        Boolean b = username.matches(EMAIL_REGEX);

//        if (b){
            KhachHang kh  = khachHangService.checkLoginEmailAndPassword(username, password);
            if (kh !=null){

                GioHang gh = gioHangService.findByKhachHang(kh);

                if (gh != null){
                    String fullName = kh.getTenkh();
                    model.addAttribute("fullNameLogin", fullName);
                    session.setAttribute("KhachHangLogin", kh);
                    session.setAttribute("GHLogged", gh);
                    return "redirect:/buyer/";
                }

                GioHang gioHang = new GioHang();
                gioHang.setKhachHang(kh);
                gioHang.setTrangthai(1);
                gioHangService.saveGH(gioHang);

                session.setAttribute("KhachHangLogin", kh);
                session.setAttribute("GHLogged", gioHang);

                return "redirect:/buyer/";

            }else{
                model.addAttribute("messageLogin", "Tài Khoản hoặc Mật Khẩu không chính xác");
                return "online/login";
            }
    }

    @GetMapping("/register")
    public String getFormBuyerRegister(Model model){
        KhachHang khachHang = new KhachHang();
        model.addAttribute("formRegister", true);
        model.addAttribute("userRegister",khachHang);
        return "online/register";
    }


    @PostMapping("/register")
    public String buyerRegister(Model model, RedirectAttributes redirectAttributes){
        String fullName = request.getParameter("fullname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        boolean hasErr = false;

        if (fullName == null || "".equals(fullName)){
            model.addAttribute("messFullname", "Họ Tên không được bỏ trống  !");
            hasErr=true;
        }
        if (email == null || "".equals(email)){
            model.addAttribute("messEmail", "Email không được bỏ trống  !");
            hasErr=true;
        }
        if (hasErr){
            model.addAttribute("messageFullName", fullName);
            model.addAttribute("messageEmail", email);
            model.addAttribute("formRegister", true);
            return "/online/register";
        }

        KhachHang kh = khachHangService.checkEmail(email);
        if (kh != null){
            model.addAttribute("messEmail", "Email đã tồn tại ! ");
            model.addAttribute("messageFullName", fullName);
            model.addAttribute("formRegister", true);
            return "/online/register";
        }
        Date date = new Date();
        String idkh = "KH00" + date.getDate() + generateRandomNumbers();

        KhachHang khachHang = new KhachHang();
        khachHang.setEmail(email);
        khachHang.setTenkh(fullName);
        khachHang.setPassword(password);
        khachHang.setTrangthai(2);
        khachHang.setIdkh(idkh);

         // Liên kết giỏ hàng với khách hàng



        String numberOTP = generateRandomNumbers();

        session.setAttribute("userRegister", khachHang);
        session.setAttribute("OTPRegister", numberOTP);

        String subject="Chào" + " " + fullName + " " + "Đây là OTP để đăng ký tài khoản của bạn : ";

        sendMailService.sendSimpleMail(email, "Xác minh địa chỉ email của bạn"  ,subject + " "+  numberOTP);

        model.addAttribute("formRegister", false);
        model.addAttribute("formOTPRegister", true);

        return "online/register";
    }

    @PostMapping("/register/verify-otp")
    public String buyerRegisterVerifyOTP(Model model){

        String OTPNumber = (String) session.getAttribute("OTPRegister");
        KhachHang KHRegiter = (KhachHang) session.getAttribute("userRegister");

        String OTPUser = request.getParameter("OTPRegister");

        if (OTPNumber.equals(OTPUser)){
            GioHang gioHang = new GioHang();
            gioHang.setTrangthai(1);
            gioHang.setKhachHang(KHRegiter);

            KHRegiter.setGioHang(gioHang);
            KHRegiter.setTrangthai(1);
            khachHangService.save(KHRegiter);
            gioHangService.saveGH(gioHang);
            session.invalidate();

            return"online/login";
        }else {
            model.addAttribute("formRegister", false);
            model.addAttribute("formOTPRegister", true);
            model.addAttribute("messageOTP", "OTP không hợp lệ. Vui lòng thử lại.");
            return "online/register";
        }

    }

    @GetMapping("/register/resened-otp")
    public String resendOTPEmailForm(Model model){
        model.addAttribute("formRegister", false);
        model.addAttribute("formOTPRegister", true);

        KhachHang KHRegiter = (KhachHang) session.getAttribute("userRegister");

        String numberOTPResend = generateRandomNumbers();
        String fullName = KHRegiter.getTenkh();
        String email = KHRegiter.getEmail();
        session.removeAttribute("OTPRegister");
        session.setAttribute("OTPRegisterResend", numberOTPResend);

        String subject="Chào" + " " + fullName + " " + "Đây là OTP để đăng ký tài khoản của bạn : ";

        sendMailService.sendSimpleMail(email, "Xác minh địa chỉ email của bạn"  ,subject + " "+  numberOTPResend);

        return "online/register";
    }

    @GetMapping("/reset")
    public String getFormBuyerResetPass(){

        return "online/reset";
    }

    @GetMapping("/reset/send-otp")
    public String sendOTPResetPassword(@RequestParam("email") String email, Model model) {
        KhachHang khachHang = khachHangService.checkEmail(email);
        if (khachHang == null) {
            model.addAttribute("message", "Email không tồn tại trong hệ thống.");
            return "online/reset";
        }

        String otp = generateRandomNumbers();
        session.setAttribute("OTPReset", otp);
        session.setAttribute("emailReset", email);

        String subject = "Mã OTP đặt lại mật khẩu của bạn";
        sendMailService.sendSimpleMail(email, subject, "OTP của bạn là: " + otp);

        model.addAttribute("message", "OTP đã được gửi tới email của bạn.");
        return "online/reset";
    }



    @GetMapping("/logout")
    public String getFormBuyerLogout(){
        session.invalidate();
        return "redirect:/buyer/";
    }

    public String generateRandomNumbers() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int randomNumber = random.nextInt(10); // Tạo số ngẫu nhiên từ 0 đến 9
            sb.append(randomNumber);
        }
        return sb.toString();
    }


}
