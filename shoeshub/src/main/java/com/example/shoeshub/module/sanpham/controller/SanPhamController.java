package com.example.shoeshub.module.sanpham.controller;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.chatlieu.service.ChatLieuService;
import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import com.example.shoeshub.module.chitietsanpham.service.ChiTietSanPhamService;
import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import com.example.shoeshub.module.danhmuc.service.DanhMucService;
import com.example.shoeshub.module.hinhanh.entity.HinhAnh;
import com.example.shoeshub.module.hinhanh.service.HinhAnhService;
import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.mausac.service.MauSacService;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import com.example.shoeshub.module.sanpham.service.SanPhamService;
import com.example.shoeshub.module.size.entity.Size;
import com.example.shoeshub.module.size.service.SizeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.el.stream.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class SanPhamController {

    private final SanPhamService sanPhamService;
    private final DanhMucService danhMucService;
    private final ChiTietSanPhamService chiTietSanPhamService;
    private final ChatLieuService chatLieuService;
    private final MauSacService mauSacService;
    private final SizeService sizeService;
    private final HinhAnhService hinhAnhService;
    private final HttpSession session;

    @ModelAttribute("dsTrangThai")
    public Map<Integer, String> getDsTrangThai() {
        Map<Integer, String> dsTrangThai = new HashMap<>();
        dsTrangThai.put(1, "Hoạt động");
        dsTrangThai.put(0, "Không hoạt động");
        return dsTrangThai;
    }

    @GetMapping("/san-pham")
    public String dsGiay(Model model
            , @ModelAttribute("messageSanPham") String messageSanPham
            , @ModelAttribute("tenSanPhamError") String tenSanPhamError
            , @ModelAttribute("errorSanPham") String errorSanPham
            , @ModelAttribute("userInput") SanPham userInputSanPham
            , @ModelAttribute("ErrormessageSanPham") String ErrormessageSanPham
            , @ModelAttribute("danhMucError") String danhMucError

            , @ModelAttribute("messageDanhMuc") String messageDanhMuc
            , @ModelAttribute("tenDanhMucError") String tenDanhMucError
            , @ModelAttribute("errorDanhMuc") String errorDanhMuc
            , @ModelAttribute("userInput") DanhMuc userInputDanhMuc
            , @ModelAttribute("ErrormessageDanhMuc") String ErrormessageDanhMuc) {

        List<SanPham> list = sanPhamService.getAll();
        model.addAttribute("sanPham", list);

        List<DanhMuc> listdm = danhMucService.getAllTrangThai(1);
        model.addAttribute("danhmuc", listdm);

        model.addAttribute("sanPhamAdd", new SanPham());
        model.addAttribute("danhMucAdd", new DanhMuc());

        if (messageSanPham == null || !"true".equals(messageSanPham)) {
            model.addAttribute("messageSanPham", false);
        }
        if (tenSanPhamError == null || !"tenSanPhamError".equals(errorSanPham)) {
            model.addAttribute("tenSanPhamError", false);
        }
        if (danhMucError == null || !"danhMucError".equals(errorSanPham)) {
            model.addAttribute("danhMucError", false);
        }
        if (userInputSanPham != null) {
            model.addAttribute("sanPhamAdd", userInputSanPham);
        }
        //
        if (messageDanhMuc == null || !"true".equals(messageDanhMuc)) {
            model.addAttribute("messageDanhMuc", false);
        }
        if (tenDanhMucError == null || !"tenDanhMucError".equals(errorDanhMuc)) {
            model.addAttribute("tenDanhMucError", false);
        }
        if (userInputDanhMuc != null) {
            model.addAttribute("danhMucAdd", userInputDanhMuc);
        }
        //
        if (ErrormessageSanPham == null || !"true".equals(ErrormessageSanPham)) {
            model.addAttribute("ErrormessageSanPham", false);
        }
        if (ErrormessageDanhMuc == null || !"true".equals(ErrormessageDanhMuc)) {
            model.addAttribute("ErrormessageDanhMuc", false);
        }

        return "manager/san-pham";
    }

    @PostMapping("/san-pham/viewAdd/add")
    public String addGiay(@Valid @ModelAttribute("sanPham") SanPham sanPham, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tensp")) {
                redirectAttributes.addFlashAttribute("userInput", sanPham);
                redirectAttributes.addFlashAttribute("errorGiay", "tenChatLieuError");
            }
            if (bindingResult.hasFieldErrors("danhmuc")) {
                redirectAttributes.addFlashAttribute("userInput", sanPham);
                redirectAttributes.addFlashAttribute("errorGiay", "danhMucError");
            }
            return "redirect:/manager/san-pham";
        }
        //
        var existingSanPham = sanPhamService.findName(sanPham.getTensp());
        if (existingSanPham.isPresent()) {
            redirectAttributes.addFlashAttribute("userInput", sanPham);
            redirectAttributes.addFlashAttribute("ErrormessageSanPham", true);
            return "redirect:/manager/san-pham";
        }
        //
        sanPham.setTrangthai(1);
        sanPhamService.add(sanPham);
        redirectAttributes.addFlashAttribute("messageSanPham", true);
        return "redirect:/manager/san-pham";
    }

    @PostMapping("/san-pham/danh-muc/viewAdd/add")
    public String addDanhMuc(@Valid @ModelAttribute("danhMucAdd") DanhMuc danhMuc,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tendanhmuc")) {
                redirectAttributes.addFlashAttribute("userInput", danhMuc);
                redirectAttributes.addFlashAttribute("error", "tenDanhMucError");
            }
            return "redirect:/manager/san-pham";
        }

        var existingDanhMuc = danhMucService.findName(danhMuc.getTendanhmuc());
        if (existingDanhMuc.isPresent()) {
            redirectAttributes.addFlashAttribute("userInput", danhMuc);
            redirectAttributes.addFlashAttribute("ErrormessageDanhMuc", true);
            return "redirect:/manager/san-pham";
        }

        danhMuc.setTrangthai(1);
        danhMucService.add(danhMuc);
        redirectAttributes.addFlashAttribute("messageDanhMuc", true);
        return "redirect:/manager/san-pham";
    }

    @GetMapping("/san-pham/viewUpdate/{id}")
    public String viewUpdateSanPham(@PathVariable int id, Model model,
                                    @ModelAttribute("message") String message,
                                    @ModelAttribute("tenSanPhamError") String tenSanPhamError,
                                    @ModelAttribute("error") String error,
                                    @ModelAttribute("userInput") SanPham userInput,
                                    @ModelAttribute("Errormessage") String Errormessage) {
        var sanPham = sanPhamService.findId(id);
        model.addAttribute("sanPham", sanPham.orElse(null));
        List<DanhMuc> listdm = danhMucService.getAllTrangThai(1);
        model.addAttribute("danhmuc", listdm);
        model.addAttribute("message", "true".equals(message));
        model.addAttribute("tenSanPhamError", "tenSanPhamError".equals(error));
        model.addAttribute("sanPhamAdd", userInput != null ? userInput : new ChatLieu());
        model.addAttribute("Errormessage", "true".equals(Errormessage));
        session.setAttribute("id", id);

        return "manager/update-san-pham";
    }

    @PostMapping("/san-pham/viewUpdate/{id}")
    public String updateSanPham(@PathVariable int id, @Valid @ModelAttribute("sanPham") SanPham sanPham,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        var sanPhamDb = sanPhamService.findId(id);
        int idSanPham = (int) session.getAttribute("id");
        String link = "redirect:/manager/san-pham/viewUpdate/" + idSanPham;

        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tensp")) {
                redirectAttributes.addFlashAttribute("userInput", sanPham);
                redirectAttributes.addFlashAttribute("error", "tenSanPhamError");
            }
            return link;
        }

        var existingSanPham = sanPhamService.findName(sanPham.getTensp());
        if (existingSanPham.isPresent()) {
            if (sanPhamDb.get().getTensp() != null) {
                if (!sanPhamDb.get().getTensp().equals(sanPham.getTensp())) {
                    redirectAttributes.addFlashAttribute("userInput", sanPham);
                    redirectAttributes.addFlashAttribute("Errormessage", true);
                    return link;
                }
            }
        }
        sanPhamDb.ifPresent(c -> {
            c.setTensp(sanPham.getTensp());
            c.setDanhmuc(sanPham.getDanhmuc());
            c.setTrangthai(sanPham.getTrangthai());
            sanPhamService.add(c);
            redirectAttributes.addFlashAttribute("message", true);
        });

        return "redirect:/manager/san-pham";
    }

    @GetMapping("/san-pham/delete/{id}")
    public String deleteSanPham(@PathVariable int id, RedirectAttributes redirectAttributes) {
        sanPhamService.delete(id);
        redirectAttributes.addFlashAttribute("messageSanPham", true);
        // Cập nhật trạng thái của tất cả sản phẩm chi tiết của giay thành 0
//        List<ChiTietGiay> chiTietGiays = giayChiTietService.findByGiay(giay);
//        for (ChiTietGiay chiTietGiay : chiTietGiays) {
//            chiTietGiay.setTrangThai(0);
//            giayChiTietService.save(chiTietGiay);
//        }
        return "redirect:/manager/san-pham";
    }


    @GetMapping("/san-pham-chi-tiet/viewAdd/{id}")
    public String viewUpdateChiTietGiay(@PathVariable int id, Model model

            , @ModelAttribute("messageChiTietSanPham") String messageChiTietSanPham
            , @ModelAttribute("donGiaError") String donGiaError
            , @ModelAttribute("soLuongError") String soLuongError
            , @ModelAttribute("mauSacError") String mauSacError
            , @ModelAttribute("hinhAnhError") String hinhAnhError
            , @ModelAttribute("sanPhamError") String sanPhamError
            , @ModelAttribute("sizeError") String sizeError
            , @ModelAttribute("chatLieuError") String chatLieuError
            , @ModelAttribute("motaError") String motaError
            , @ModelAttribute("error") String error
            , @ModelAttribute("userInput") ChiTietSanPham userInput

            , @ModelAttribute("messageSize") String messageSize
            , @ModelAttribute("kichThuocError") String soSizeError
            , @ModelAttribute("userInputSize") Size userInputSize

            , @ModelAttribute("messageMauSac") String messageMauSac
            , @ModelAttribute("maMauError") String maMauError
            , @ModelAttribute("tenMauError") String tenMauError
            , @ModelAttribute("userInputMauSac") MauSac userInputMauSac

            , @ModelAttribute("messageHinhAnh") String messageHinhAnh
            , @ModelAttribute("userInputHinhAnh") HinhAnh userInputHinhAnh

            , @ModelAttribute("messageChatLieu") String messageChatLieu
            , @ModelAttribute("tenChatLieuError") String tenChatLieuError
            , @ModelAttribute("userInputChatLieu") ChatLieu userInputChatLieu

            , @ModelAttribute("ErrormessageChatLieu") String ErrormessageChatLieu
            , @ModelAttribute("ErrormessageMauSac") String ErrormessageMauSac
            , @ModelAttribute("ErrormessageSize") String ErrormessageSize
            , @ModelAttribute("ErrormessageHinhAnh") String ErrormessageHinhAnh

    ) {
        var sanPham = sanPhamService.findId(id);
        model.addAttribute("sanpham", sanPham.orElse(null));

        List<HinhAnh> hinhAnhList = hinhAnhService.getAll();
        model.addAttribute("hinhanh", hinhAnhList);
        //
        List<ChatLieu> chatLieuList = chatLieuService.getAll();
        model.addAttribute("chatlieu", chatLieuList);
        //
        List<MauSac> mauSacList = mauSacService.getAll();
        model.addAttribute("mausac", mauSacList);
        //
        List<Size> sizeList = sizeService.getAll();
        model.addAttribute("size", sizeList);
        //
        model.addAttribute("mauSacAdd", new MauSac());
        model.addAttribute("sizeAdd", new Size());
        model.addAttribute("hinhAnhAdd", new HinhAnh());
        model.addAttribute("chatLieuAdd", new ChatLieu());

        if (donGiaError == null || !"donGiaError".equals(error)) {
            model.addAttribute("donGiaError", false);
        }
        if (soLuongError == null || !"soLuongError".equals(error)) {
            model.addAttribute("soLuongError", false);
        }
        if (mauSacError == null || !"mauSacError".equals(error)) {
            model.addAttribute("mauSacError", false);
        }
        if (hinhAnhError == null || !"hinhAnhError".equals(error)) {
            model.addAttribute("hinhAnhError", false);
        }
        if (sizeError == null || !"sizeError".equals(error)) {
            model.addAttribute("sizeError", false);
        }
        if (chatLieuError == null || !"chatLieuError".equals(error)) {
            model.addAttribute("chatLieuError", false);
        }

        // Kiểm tra xem có dữ liệu người dùng đã nhập không và điền lại vào trường nhập liệu
        if (messageChiTietSanPham == null || !"true".equals(messageChiTietSanPham)) {
            model.addAttribute("messageChiTietSanPham", false);
        }

        // size
        if (messageSize == null || !"true".equals(messageSize)) {
            model.addAttribute("messageSize", false);
        }
        if (soSizeError == null || !"soSizeError".equals(error)) {
            model.addAttribute("soSizeError", false);
        }
        // Kiểm tra xem có dữ liệu người dùng đã nhập không và điền lại vào trường nhập liệu
        if (userInputSize != null) {
            model.addAttribute("sizeAdd", userInputSize);
        }
        //mau
        if (messageMauSac == null || !"true".equals(messageMauSac)) {
            model.addAttribute("messageMauSac", false);
        }
        if (tenMauError == null || !"tenMauError".equals(error)) {
            model.addAttribute("tenMauError", false);
        }
        if (maMauError == null || !"maMauError".equals(error)) {
            model.addAttribute("maMauError", false);
        }
        // Kiểm tra xem có dữ liệu người dùng đã nhập không và điền lại vào trường nhập liệu
        if (userInputMauSac != null) {
            model.addAttribute("mauSacAdd", userInputMauSac);
        }
        // anh
        if (messageHinhAnh == null || !"true".equals(messageHinhAnh)) {
            model.addAttribute("messageHinhAnh", false);
        }
        // Kiểm tra xem có dữ liệu người dùng đã nhập không và điền lại vào trường nhập liệu
        if (userInputHinhAnh != null) {
            model.addAttribute("hinhAnhAdd", userInputHinhAnh);
        }

        // chất liệu
        if (messageChatLieu  == null || !"true".equals(messageChatLieu)) {
            model.addAttribute("messageChatLieu", false);
        }
        if (tenChatLieuError == null || !"tenChatLieuError".equals(error)) {
            model.addAttribute("tenChatLieuError", false);
        }
        // Kiểm tra xem có dữ liệu người dùng đã nhập không và điền lại vào trường nhập liệu
        if (userInputChatLieu != null) {
            model.addAttribute("chatLieuAdd", userInputChatLieu);
        }

        if (ErrormessageChatLieu == null || !"true".equals(ErrormessageChatLieu)) {
            model.addAttribute("ErrormessageChatLieu", false);
        }
        if (ErrormessageMauSac == null || !"true".equals(ErrormessageMauSac)) {
            model.addAttribute("ErrormessageMauSac", false);
        }
        if (ErrormessageSize == null || !"true".equals(ErrormessageSize)) {
            model.addAttribute("ErrormessageSize", false);
        }
        if (ErrormessageHinhAnh == null || !"true".equals(ErrormessageHinhAnh)) {
            model.addAttribute("ErrormessageHinhAnh", false);
        }

        session.removeAttribute("id");
        session.setAttribute("id", id);
        return "manager/add-chi-tiet-san-pham";
    }

    @PostMapping("/san-pham-chi-tiet/viewAdd/add")
    public String addChiTietSanPham(@Valid @ModelAttribute("chiTietSanPham") ChiTietSanPham chiTietSanPham,
                                    @RequestParam("listSize") List<Integer> listSize,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes
    ) {
        //
        int idCTGViewAdd = (int) session.getAttribute("id");
        String link1 = "redirect:/manager/san-pham-chi-tiet/viewAdd/" + idCTGViewAdd;

//        int idGiay = chiTietGiay.getGiay().getIdGiay();
//        //
//        var sanPham = sanPhamService.findId(idGiay);
        //
        if (bindingResult.hasErrors()) {


            if (bindingResult.hasFieldErrors("dongia")) {
                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
                redirectAttributes.addFlashAttribute("error", "donGiaError");
            }
            if (bindingResult.hasFieldErrors("soluong")) {
                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
                redirectAttributes.addFlashAttribute("error", "soLuongError");
            }
            if (bindingResult.hasFieldErrors("mauSac")) {
                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
                redirectAttributes.addFlashAttribute("error", "mauSacError");
            }
            if (bindingResult.hasFieldErrors("hinhAnh")) {
                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
                redirectAttributes.addFlashAttribute("error", "hinhAnhError");
            }
//            if (bindingResult.hasFieldErrors("giay")) {
//                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
//                redirectAttributes.addFlashAttribute("error", "giayError");
//            }
            if (bindingResult.hasFieldErrors("size")) {
                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
                redirectAttributes.addFlashAttribute("error", "sizeError");
            }
            if (bindingResult.hasFieldErrors("chatlieu")) {
                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
                redirectAttributes.addFlashAttribute("error", "chatLieuError");
            }
            return link1;
        }
        //
        for (int x : listSize) {

            var isDuplicate = chiTietSanPhamService.isDuplicateChiTietGiay(
                    chiTietSanPham.getSanPham().getMasp(),
                    x,
                    chiTietSanPham.getMauSac().getMams(),
                    chiTietSanPham.getChatLieu().getMacl(),
                    chiTietSanPham.getHinhAnh().getMaanh()
            );
            if (!isDuplicate.isEmpty()) {
                for (ChiTietSanPham duplicateChiTietGiay : isDuplicate) {
                    System.out.println("ChiTietSanPham đã tồn tại với ID: " + duplicateChiTietGiay.getMactsp());
                    redirectAttributes.addFlashAttribute("userInput", duplicateChiTietGiay.getMactsp());
                }
                // Xử lý sự trùng lặp, ví dụ: hiển thị thông báo và không thêm mới
                redirectAttributes.addFlashAttribute("error", "daTonTai");
                redirectAttributes.addFlashAttribute("updateQuantity", true);
                return link1;
            }

            ChiTietSanPham chiTietSanPham1 = new ChiTietSanPham();

            chiTietSanPham1.setSanPham(chiTietSanPham.getSanPham());
            chiTietSanPham1.setDongia(chiTietSanPham.getDongia());
            chiTietSanPham1.setSoluong(chiTietSanPham.getSoluong());
            chiTietSanPham1.setChatLieu(chiTietSanPham.getChatLieu());
            chiTietSanPham1.setMota(chiTietSanPham.getMota());
            chiTietSanPham1.setMauSac(chiTietSanPham.getMauSac());
            chiTietSanPham1.setHinhAnh(chiTietSanPham.getHinhAnh());
            chiTietSanPham1.setSize(sizeService.findId(x).orElse(null));
            chiTietSanPham1.setTrangthai(1);

            chiTietSanPhamService.add(chiTietSanPham1);
        }

        redirectAttributes.addFlashAttribute("messageChiTietSanPham", true);
        return "redirect:/manager/san-pham";
    }

    @PostMapping("/san-pham-chi-tiet/chat-lieu/viewAdd/add")
    public String addChatLieu(@Valid @ModelAttribute("chatLieuAdd") ChatLieu chatLieu,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        int idCTGViewAdd = (int) session.getAttribute("id");
        String link = "redirect:/manager/san-pham-chi-tiet/viewAdd/" + idCTGViewAdd;
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tenchatlieu")) {
                redirectAttributes.addFlashAttribute("userInputChatLieu", chatLieu);
                redirectAttributes.addFlashAttribute("error", "tenChatLieuError");
            }
            return link;
        }

        var existingChatLieu = chatLieuService.findName(chatLieu.getTenchatlieu());
        if (existingChatLieu.isPresent()) {
            redirectAttributes.addFlashAttribute("userInputChatLieu", chatLieu);
            redirectAttributes.addFlashAttribute("ErrormessageChatLieu", true);
            return link;
        }

        chatLieu.setTrangthai(1);
        chatLieuService.add(chatLieu);
        redirectAttributes.addFlashAttribute("messageChatLieu", true);
        return link;
    }
    @PostMapping("/san-pham-chi-tiet/size/viewAdd/add")
    public String addSize(@Valid @ModelAttribute("sizeAdd") Size size,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        int idCTGViewAdd = (int) session.getAttribute("id");
        String link = "redirect:/manager/san-pham-chi-tiet/viewAdd/" + idCTGViewAdd;
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("kichthuoc")) {
                redirectAttributes.addFlashAttribute("userInputSize", size);
                redirectAttributes.addFlashAttribute("error", "soSizeError");
            }
            return link;
        }

        var existingSize = sizeService.findKichthuoc(size.getKichthuoc());
        if (existingSize.isPresent()) {
            redirectAttributes.addFlashAttribute("userInputSize", size);
            redirectAttributes.addFlashAttribute("ErrormessageSize", true);
            return link;
        }

        size.setTrangthai(1);
        sizeService.add(size);
        redirectAttributes.addFlashAttribute("messageSize", true);
        return link;
    }

    @PostMapping("/san-pham-chi-tiet/mau-sac/viewAdd/add")
    public String addMauSac(@Valid @ModelAttribute("mauSacAdd") MauSac mauSac,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        int idCTGViewAdd = (int) session.getAttribute("id");
        String link = "redirect:/manager/san-pham-chi-tiet/viewAdd/" + idCTGViewAdd;
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tenmau")) {
                redirectAttributes.addFlashAttribute("userInputMauSac", mauSac);
                redirectAttributes.addFlashAttribute("error", "tenMauError");
            }
            return link;
        }

        var existingMauSac = mauSacService.findName(mauSac.getTenmau());
        if (existingMauSac.isPresent()) {
            redirectAttributes.addFlashAttribute("userInputMauSac", mauSac);
            redirectAttributes.addFlashAttribute("ErrormessageMauSac", true);
            return link;
        }

        mauSac.setTrangthai(1);
        mauSacService.add(mauSac);
        redirectAttributes.addFlashAttribute("messageMauSac", true);
        return link;
    }

    @PostMapping("/san-pham-chi-tiet/hinh-anh/viewAdd/add")
    public String addHinhAnh(
            @RequestParam("anh1") MultipartFile anh1,
            @RequestParam("anh2") MultipartFile anh2,
            @RequestParam("anh3") MultipartFile anh3,
            @RequestParam("anh4") MultipartFile anh4,
            @Valid @ModelAttribute("hinhAnh") HinhAnh hinhAnh, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        int idCTGViewAdd = (int) session.getAttribute("id");
        String link = "redirect:/manager/san-pham-chi-tiet/viewAdd/" + idCTGViewAdd;

        HinhAnh hinhAnh1 = new HinhAnh();
        if (anh1.isEmpty() || anh2.isEmpty() || anh3.isEmpty() || anh4.isEmpty()) {
            return link;
        }
        Path path = Paths.get("src/main/resources/static/images/imgsProducts/");

        try {
            InputStream inputStream = anh1.getInputStream();
            Files.copy(inputStream, path.resolve(Objects.requireNonNull(anh1.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING);
            hinhAnh1.setUrl1(anh1.getOriginalFilename().toLowerCase());
            //
            inputStream = anh2.getInputStream();
            Files.copy(inputStream, path.resolve(Objects.requireNonNull(anh2.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING);
            hinhAnh1.setUrl2(anh2.getOriginalFilename().toLowerCase());
            //
            inputStream = anh3.getInputStream();
            Files.copy(inputStream, path.resolve(Objects.requireNonNull(anh3.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING);
            hinhAnh1.setUrl3(anh3.getOriginalFilename().toLowerCase());
            //
            inputStream = anh4.getInputStream();
            Files.copy(inputStream, path.resolve(Objects.requireNonNull(anh4.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING);
            hinhAnh1.setUrl4(anh4.getOriginalFilename().toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        hinhAnh1.setTrangthai(1);
        hinhAnhService.add(hinhAnh1);
        redirectAttributes.addFlashAttribute("messageHinhAnh", true);
        return link;
    }


}

