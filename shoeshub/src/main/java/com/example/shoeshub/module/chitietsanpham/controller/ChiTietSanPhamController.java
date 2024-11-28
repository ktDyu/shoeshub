package com.example.shoeshub.module.chitietsanpham.controller;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.chatlieu.service.ChatLieuService;
import com.example.shoeshub.module.chitietsanpham.entity.ChiTietSanPham;
import com.example.shoeshub.module.chitietsanpham.request.ChiTietSanPhamRequest;
import com.example.shoeshub.module.chitietsanpham.response.ChiTietSanPhamPageResponse;
import com.example.shoeshub.module.chitietsanpham.service.ChiTietSanPhamService;
import com.example.shoeshub.module.chitietsanpham.service.impl.ChiTietSanPhamServiceImpl;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ChiTietSanPhamController {
    private final ChiTietSanPhamService chiTietSanPhamService;
    private final HttpSession session;

    private final DanhMucService danhMucService;
    private final SanPhamService sanPhamService;
    private final ChatLieuService chatLieuService;
    private final SizeService sizeService;
    private final MauSacService mauSacService;
    private final HinhAnhService hinhAnhService;


    @GetMapping("/san-pham-chi-tiet")
    public String viewChiTietSanPham( Model model


            , @ModelAttribute("messageChiTietSanPham") String messageChiTietSanPham
            , @ModelAttribute("donGiaError") String donGiaError
            , @ModelAttribute("soLuongError") String soLuongError
            , @ModelAttribute("mauSacError") String mauSacError
            , @ModelAttribute("hinhAnhError") String hinhAnhError
            , @ModelAttribute("sanPhamError") String sanPhamError
            , @ModelAttribute("sizeError") String sizeError
            , @ModelAttribute("chatLieuError") String chatLieuError
            , @ModelAttribute("danhMucError") String danhMucError
            , @ModelAttribute("motaError") String motaError
            , @ModelAttribute("error") String error
            , @ModelAttribute("userInput") ChiTietSanPham userInput

            , @ModelAttribute("messageSanPham") String messageSanPham
            , @ModelAttribute("tenSanPhamError") String tenSanPhamError
            , @ModelAttribute("userInputSanPham") SanPham userInputSanPham

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

            , @ModelAttribute("ErrormessageSanPham") String ErrormessageSanPham
            , @ModelAttribute("ErrormessageChatLieu") String ErrormessageChatLieu
            , @ModelAttribute("ErrormessageMauSac") String ErrormessageMauSac
            , @ModelAttribute("ErrormessageSize") String ErrormessageSize
            , @ModelAttribute("ErrormessageHinhAnh") String ErrormessageHinhAnh)
    {
        List<ChiTietSanPham> chiTietSanPham = chiTietSanPhamService.getAll();
        model.addAttribute("chitietsanpham", chiTietSanPham);

        List<SanPham> sanPhamList = sanPhamService.getAllTrangThai(1);
        model.addAttribute("sanpham", sanPhamList);

        List<HinhAnh> hinhAnhList = hinhAnhService.getAllTrangThai(1);
        model.addAttribute("hinhanh", hinhAnhList);
        //
        List<ChatLieu> chatLieuList = chatLieuService.getAllTrangThai(1);
        model.addAttribute("chatlieu", chatLieuList);
        //
        List<MauSac> mauSacList = mauSacService.getAllTrangThai(1);
        model.addAttribute("mausac", mauSacList);
        //
        List<Size> sizeList = sizeService.getAllTrangThai(1);
        model.addAttribute("size", sizeList);
        //
        List<DanhMuc> danhMucList = danhMucService.getAllTrangThai(1);
        model.addAttribute("danhmuc", danhMucList);
        //
        model.addAttribute("sanPhamAdd", new SanPham());
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
        if (danhMucError == null || !"danhMucError".equals(error)) {
            model.addAttribute("danhMucError", false);
        }
        if (sanPhamError == null || !"sanPhamError".equals(error)) {
            model.addAttribute("sanPhamError", false);
        }
        // Kiểm tra xem có dữ liệu người dùng đã nhập không và điền lại vào trường nhập liệu
//        if (userInput != null) {
//            model.addAttribute("giayChiTietUpdate", userInput);
//        }
        if (messageChiTietSanPham == null || !"true".equals(messageChiTietSanPham)) {
            model.addAttribute("messageChiTietSanPham", false);
        }

        //sp
        if (messageSanPham == null || !"true".equals(messageSanPham)) {
            model.addAttribute("messageSanPham", false);
        }
        if (tenSanPhamError == null || !"tenSanPhamError".equals(error)) {
            model.addAttribute("tenSanPhamError", false);
        }
        // Kiểm tra xem có dữ liệu người dùng đã nhập không và điền lại vào trường nhập liệu
        if (userInputSanPham != null) {
            model.addAttribute("sanPhamAdd", userInputSanPham);
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

        if (ErrormessageSanPham == null || !"true".equals(ErrormessageSanPham)) {
            model.addAttribute("ErrormessageSanPham", false);
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

        return "manager/chi-tiet-san-pham";
    }

    @PostMapping("/san-pham-chi-tiet/add")
    public String addChiTieSanPham(@Valid @ModelAttribute("chiTietSanPham") ChiTietSanPham chiTietSanPham,
                                    @RequestParam("listSize") List<Integer> listSize,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes
    ) {

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
            if (bindingResult.hasFieldErrors("mausac")) {
                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
                redirectAttributes.addFlashAttribute("error", "mauSacError");
            }
            if (bindingResult.hasFieldErrors("hinhAnh")) {
                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
                redirectAttributes.addFlashAttribute("error", "hinhAnhError");
            }
            if (bindingResult.hasFieldErrors("sanpham")) {
                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
                redirectAttributes.addFlashAttribute("error", "giayError");
            }
            if (bindingResult.hasFieldErrors("size")) {
                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
                redirectAttributes.addFlashAttribute("error", "sizeError");
            }
            if (bindingResult.hasFieldErrors("chatlieu")) {
                redirectAttributes.addFlashAttribute("userInput", chiTietSanPham);
                redirectAttributes.addFlashAttribute("error", "chatLieuError");
            }
            return "redirect:/manager/san-pham-chi-tiet";
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
                return "redirect:/manager/san-pham-chi-tiet";
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
        return "redirect:/manager/san-pham-chi-tiet";
    }


    @PostMapping("/san-pham-chi-tiet/san-pham/add")
    public String addGiay(@Valid @ModelAttribute("sanPham") SanPham sanPham, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tensp")) {
                redirectAttributes.addFlashAttribute("userInputSanPham", sanPham);
                redirectAttributes.addFlashAttribute("errorGiay", "tenChatLieuError");
            }
            if (bindingResult.hasFieldErrors("danhmuc")) {
                redirectAttributes.addFlashAttribute("userInputSanPham", sanPham);
                redirectAttributes.addFlashAttribute("errorGiay", "danhMucError");
            }
            return "redirect:/manager/san-pham-chi-tiet";
        }
        //
        var existingSanPham = sanPhamService.findName(sanPham.getTensp());
        if (existingSanPham.isPresent()) {
            redirectAttributes.addFlashAttribute("userInputSanPham", sanPham);
            redirectAttributes.addFlashAttribute("ErrormessageSanPham", true);
            redirectAttributes.addFlashAttribute("isSanPhamModalOpen", true);
            return "redirect:/manager/san-pham-chi-tiet";
        }
        //
        sanPham.setTrangthai(1);
        sanPhamService.add(sanPham);
        redirectAttributes.addFlashAttribute("messageSanPham", true);
        return "redirect:/manager/san-pham-chi-tiet";
    }
    @PostMapping("/san-pham-chi-tiet/chat-lieu/add")
    public String addChatLieu(@Valid @ModelAttribute("chatLieuAdd") ChatLieu chatLieu,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tenchatlieu")) {
                redirectAttributes.addFlashAttribute("userInputChatLieu", chatLieu);
                redirectAttributes.addFlashAttribute("error", "tenChatLieuError");
            }
            return "redirect:/manager/san-pham-chi-tiet";
        }

        var existingChatLieu = chatLieuService.findName(chatLieu.getTenchatlieu());
        if (existingChatLieu.isPresent()) {
            redirectAttributes.addFlashAttribute("userInputChatLieu", chatLieu);
            redirectAttributes.addFlashAttribute("ErrormessageChatLieu", true);
            redirectAttributes.addFlashAttribute("isChatLieuModalOpen", true);
            return "redirect:/manager/san-pham-chi-tiet";
        }

        chatLieu.setTrangthai(1);
        chatLieuService.add(chatLieu);
        redirectAttributes.addFlashAttribute("messageChatLieu", true);
        return "redirect:/manager/san-pham-chi-tiet";
    }
    @PostMapping("/san-pham-chi-tiet/size/add")
    public String addSize(@Valid @ModelAttribute("sizeAdd") Size size,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("kichthuoc")) {
                redirectAttributes.addFlashAttribute("userInputSize", size);
                redirectAttributes.addFlashAttribute("error", "soSizeError");
            }
            return "redirect:/manager/san-pham-chi-tiet";
        }

        var existingSize = sizeService.findKichthuoc(size.getKichthuoc());
        if (existingSize.isPresent()) {
            redirectAttributes.addFlashAttribute("userInputSize", size);
            redirectAttributes.addFlashAttribute("ErrormessageSize", true);
            redirectAttributes.addFlashAttribute("isSizeModalOpen", true);
            return "redirect:/manager/san-pham-chi-tiet";
        }

        size.setTrangthai(1);
        sizeService.add(size);
        redirectAttributes.addFlashAttribute("messageSize", true);
        return "redirect:/manager/san-pham-chi-tiet";
    }

    @PostMapping("/san-pham-chi-tiet/mau-sac/add")
    public String addMauSac(@Valid @ModelAttribute("mauSacAdd") MauSac mauSac,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tenmau")) {
                redirectAttributes.addFlashAttribute("userInputMauSac", mauSac);
                redirectAttributes.addFlashAttribute("error", "tenMauError");
            }
            return "redirect:/manager/san-pham-chi-tiet";
        }

        var existingMauSac = mauSacService.findName(mauSac.getTenmau());
        if (existingMauSac.isPresent()) {
            redirectAttributes.addFlashAttribute("userInputMauSac", mauSac);
            redirectAttributes.addFlashAttribute("ErrormessageMauSac", true);
            redirectAttributes.addFlashAttribute("isMauSacModalOpen", true);
            return "redirect:/manager/san-pham-chi-tiet";
        }

        mauSac.setTrangthai(1);
        mauSacService.add(mauSac);
        redirectAttributes.addFlashAttribute("messageMauSac", true);
        return "redirect:/manager/san-pham-chi-tiet";
    }

    @PostMapping("/san-pham-chi-tiet/hinh-anh/add")
    public String addHinhAnh(
            @RequestParam("anh1") MultipartFile anh1,
            @RequestParam("anh2") MultipartFile anh2,
            @RequestParam("anh3") MultipartFile anh3,
            @RequestParam("anh4") MultipartFile anh4,
            @Valid @ModelAttribute("hinhAnh") HinhAnh hinhAnh, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        HinhAnh hinhAnh1 = new HinhAnh();
        if (anh1.isEmpty() || anh2.isEmpty() || anh3.isEmpty() || anh4.isEmpty()) {
            redirectAttributes.addFlashAttribute("userInputHinhAnh", hinhAnh);
            redirectAttributes.addFlashAttribute("isHinhAnhModalOpen", true);
            return "redirect:/manager/san-pham-chi-tiet";
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
        return "redirect:/manager/san-pham-chi-tiet";
    }
    //nháp
    @PostMapping("/san-pham-chi-tiettt/size/add")
    public String addSizess(@Valid @ModelAttribute("sizeAdd") Size size,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("kichthuoc")) {
                redirectAttributes.addFlashAttribute("userInputSize", size);
                redirectAttributes.addFlashAttribute("error", "soSizeError");
            }
            return "redirect:/manager/san-pham-chi-tiet";
        }

        var existingSize = sizeService.findKichthuoc(size.getKichthuoc());
        if (existingSize.isPresent()) {
            redirectAttributes.addFlashAttribute("userInputSize", size);
            redirectAttributes.addFlashAttribute("ErrormessageSize", true);
            redirectAttributes.addFlashAttribute("isSizeModalOpen", true);
            return "redirect:/manager/san-pham-chi-tiet";
        }

        size.setTrangthai(1);
        sizeService.add(size);
        redirectAttributes.addFlashAttribute("messageSize", true);
        return "redirect:/manager/san-pham-chi-tiet";
    }

}
