package com.example.shoeshub.module.hinhanh.controller;

import com.example.shoeshub.module.hinhanh.entity.HinhAnh;
import com.example.shoeshub.module.hinhanh.service.HinhAnhService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class HinhAnhController {
    private final HinhAnhService hinhAnhService;
    private final HttpSession session;

    @ModelAttribute("dsTrangThai")
    public Map<Integer, String> getDsTrangThai() {
        Map<Integer, String> dsTrangThai = new HashMap<>();
        dsTrangThai.put(1, "Hoạt động");
        dsTrangThai.put(0, "Không hoạt động");
        return dsTrangThai;
    }

    @GetMapping("/hinh-anh")
    public ModelAndView dsHinhAnh(Model model,
                                  @ModelAttribute("message") String message,
                                  @ModelAttribute("urlHinhAnhError") String urlHinhAnhError,
                                  @ModelAttribute("error") String error,
                                  @ModelAttribute("userInput") HinhAnh userInput,
                                  @ModelAttribute("Errormessage") String Errormessage
    ) {
        ModelAndView modelAndView = new ModelAndView("manager/hinh-anh");

        model.addAttribute("hinhAnh", hinhAnhService.getAll());
        model.addAttribute("hinhAnhAdd", new HinhAnh());
        model.addAttribute("message", "true".equals(message));
        model.addAttribute("hinhAnhAdd", userInput != null ? userInput : new HinhAnh());
        model.addAttribute("Errormessage", "true".equals(Errormessage));

        return modelAndView;
    }

    @PostMapping("/hinh-anh/viewAdd/add")
    public String addHinhAnh(
            @RequestParam("anh1") MultipartFile anh1,
            @RequestParam("anh2") MultipartFile anh2,
            @RequestParam("anh3") MultipartFile anh3,
            @RequestParam("anh4") MultipartFile anh4,
            @Valid @ModelAttribute("hinhAnh") HinhAnh hinhAnh, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        HinhAnh hinhAnh1 = new HinhAnh();
        if (anh1.isEmpty() || anh2.isEmpty() || anh3.isEmpty() || anh4.isEmpty()) {
            return "redirect:/manager/hinh-anh";
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
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/manager/hinh-anh";
    }

    @GetMapping("/hinh-anh/delete/{id}")
    public String deleteHinhAnh(@PathVariable int id, RedirectAttributes redirectAttributes) {
        hinhAnhService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/manager/hinh-anh";
    }


    @GetMapping("/hinh-anh/viewUpdate/{id}")
    public String viewUpdateHinhAnh(@PathVariable int id, Model model
            , @ModelAttribute("message") String message
            , @ModelAttribute("error") String error, @ModelAttribute("userInput") HinhAnh userInput, @ModelAttribute("Errormessage") String Errormessage) {
        var hinhAnh = hinhAnhService.findById(id);
        model.addAttribute("hinhAnh", hinhAnh.orElse(null));
        //
        if (message == null || !"true".equals(message)) {
            model.addAttribute("message", false);
        }

        // Kiểm tra xem có dữ liệu người dùng đã nhập không và điền lại vào trường nhập liệu
        if (userInput != null) {
            model.addAttribute("hinhAnhAdd", userInput);
        }
        //
        session.setAttribute("id", id);
        //
        //
        if (Errormessage == null || !"true".equals(Errormessage)) {
            model.addAttribute("Errormessage", false);
        }
        return "manager/update-hinh-anh";
    }

    @PostMapping("/hinh-anh/viewUpdate/{id}")
    public String updateHinhAnh(
            @RequestParam("anh1") MultipartFile anh1,
            @RequestParam("anh2") MultipartFile anh2,
            @RequestParam("anh3") MultipartFile anh3,
            @RequestParam("anh4") MultipartFile anh4,
            @PathVariable int id, @Valid @ModelAttribute("hinhAnh") HinhAnh hinhAnh, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Optional<HinhAnh> hinhAnhDb = hinhAnhService.findById(id);
        int idAnh = (int) session.getAttribute("id");
        String link = "redirect:/manager/hinh-anh/viewUpdate/" + idAnh;
        Path path = Paths.get("src/main/resources/static/images/imgsProducts/");
        //
        hinhAnhDb.ifPresent(c -> {
            try {
                InputStream inputStream = anh1.getInputStream();
                Files.copy(inputStream, path.resolve(Objects.requireNonNull(anh1.getOriginalFilename())),
                        StandardCopyOption.REPLACE_EXISTING);
                c.setUrl1(anh1.getOriginalFilename().toLowerCase());
                //
                inputStream = anh2.getInputStream();
                Files.copy(inputStream, path.resolve(Objects.requireNonNull(anh2.getOriginalFilename())),
                        StandardCopyOption.REPLACE_EXISTING);
                c.setUrl2(anh2.getOriginalFilename().toLowerCase());
                //
                inputStream = anh3.getInputStream();
                Files.copy(inputStream, path.resolve(Objects.requireNonNull(anh3.getOriginalFilename())),
                        StandardCopyOption.REPLACE_EXISTING);
                c.setUrl3(anh3.getOriginalFilename().toLowerCase());
                //
                inputStream = anh4.getInputStream();
                Files.copy(inputStream, path.resolve(Objects.requireNonNull(anh4.getOriginalFilename())),
                        StandardCopyOption.REPLACE_EXISTING);
                c.setUrl4(anh4.getOriginalFilename().toLowerCase());
            } catch (Exception e) {
                e.printStackTrace();
            }
            c.setTrangthai(hinhAnh.getTrangthai());
            hinhAnhService.add(c);
            redirectAttributes.addFlashAttribute("message", true);
        });

        return "redirect:/manager/hinh-anh";
    }


//    @PostMapping("/chatLieu/import")
//    public String importData(@RequestParam("file") MultipartFile file) {
//        if (file != null && !file.isEmpty()) {
//            try {
//                InputStream excelFile = file.getInputStream();
//                chatLieuService.importDataFromExcel(excelFile); // Gọi phương thức nhập liệu từ Excel
//            } catch (Exception e) {
//                e.printStackTrace();
//                // Xử lý lỗi
//            }
//        }
//        return "redirect:/manager/chat-lieu"; // Chuyển hướng sau khi nhập liệu thành công hoặc không thành công
//    }
}
