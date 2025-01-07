package com.example.shoeshub.module.danhmuc.controller;

import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import com.example.shoeshub.module.danhmuc.service.DanhMucService;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class DanhMucController {

    private final DanhMucService danhMucService;
    private final HttpSession session;

    @ModelAttribute("dsTrangThai")
    public Map<Integer, String> getDsTrangThai() {
        Map<Integer, String> dsTrangThai = new HashMap<>();
        dsTrangThai.put(1, "Hoạt động");
        dsTrangThai.put(0, "Không hoạt động");
        return dsTrangThai;
    }

    @GetMapping("/danh-muc")
    public ModelAndView dsDanhMuc(Model model,
                                  @ModelAttribute("message") String message,
                                  @ModelAttribute("tenDanhMucError") String tenDanhMucError,
                                  @ModelAttribute("error") String error,
                                  @ModelAttribute("userInput") DanhMuc userInput,
                                  @ModelAttribute("Errormessage") String Errormessage
    ) {
        ModelAndView modelAndView = new ModelAndView("manager/danh-muc");

        model.addAttribute("danhMuc", danhMucService.getAll());
        model.addAttribute("danhMucAdd", new DanhMuc());
        model.addAttribute("message", "true".equals(message));
        model.addAttribute("tenDanhMucError", "tenDanhMucError".equals(error));
        model.addAttribute("danhMucAdd", userInput != null ? userInput : new DanhMuc());
        model.addAttribute("Errormessage", "true".equals(Errormessage));

        return modelAndView;
    }

    @PostMapping("/danh-muc/viewAdd/add")
    public String addDanhMuc(@Valid @ModelAttribute("danhMuc") DanhMuc danhMuc,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tendanhmuc")) {
                redirectAttributes.addFlashAttribute("userInput", danhMuc);
                redirectAttributes.addFlashAttribute("error", "tenDanhMucError");
            }
            return "redirect:/manager/danh-muc";
        }

        var existingDanhMuc = danhMucService.findName(danhMuc.getTendanhmuc());
        if (existingDanhMuc.isPresent()) {
            redirectAttributes.addFlashAttribute("userInput", danhMuc);
            redirectAttributes.addFlashAttribute("Errormessage", true);
            return "redirect:/manager/danh-muc";
        }

        danhMuc.setTrangthai(1);
        danhMucService.add(danhMuc);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/manager/danh-muc";
    }

    @GetMapping("/danh-muc/viewUpdate/{id}")
    public String viewUpdateDanhMuc(@PathVariable int id, Model model,
                                    @ModelAttribute("message") String message,
                                    @ModelAttribute("tenDanhMucError") String tenDanhMucError,
                                    @ModelAttribute("error") String error,
                                    @ModelAttribute("userInput") DanhMuc userInput,
                                    @ModelAttribute("Errormessage") String Errormessage) {
        var danhMuc = danhMucService.findId(id);
        model.addAttribute("danhMuc", danhMuc.orElse(null));
        model.addAttribute("message", "true".equals(message));
        model.addAttribute("tenDanhMucError", "tenDanhMucError".equals(error));
        model.addAttribute("danhMucAdd", userInput != null ? userInput : new DanhMuc());
        model.addAttribute("Errormessage", "true".equals(Errormessage));
        session.setAttribute("id", id);

        return "manager/update-danh-muc";
    }

    @PostMapping("/danh-muc/viewUpdate/{id}")
    public String updateDanhMuc(@PathVariable int id, @Valid @ModelAttribute("chatLieu") DanhMuc danhMuc,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        var danhMucDb = danhMucService.findId(id);
        int idDanhMuc = (int) session.getAttribute("id");
        String link = "redirect:/manager/danh-muc/viewUpdate/" + idDanhMuc;

        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tendanhmuc")) {
                redirectAttributes.addFlashAttribute("userInput", danhMuc);
                redirectAttributes.addFlashAttribute("error", "tenDanhMucError");
            }
            return link;
        }

        var existingDanhMuc = danhMucService.findName(danhMuc.getTendanhmuc());
        if (existingDanhMuc.isPresent()) {
            if (danhMucDb.get().getTendanhmuc() != null) {
                if (!danhMucDb.get().getTendanhmuc().equals(danhMuc.getTendanhmuc())) {
                    redirectAttributes.addFlashAttribute("userInput", danhMuc);
                    redirectAttributes.addFlashAttribute("Errormessage", true);
                    return link;
                }
            }
        }

        danhMucDb.ifPresent(c -> {
            c.setTendanhmuc(danhMuc.getTendanhmuc());
            c.setTrangthai(danhMuc.getTrangthai());
            danhMucService.add(c);
            redirectAttributes.addFlashAttribute("message", true);
        });

        return "redirect:/manager/danh-muc";
    }

    @GetMapping("/danh-muc/delete/{id}")
    public String deleteDanhMuc(@PathVariable int id, RedirectAttributes redirectAttributes) {
        danhMucService.delete(id);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/manager/danh-muc";
    }

    @PostMapping("/danh-muc/import")
    public String importData(@RequestParam("file") MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                InputStream excelFile = file.getInputStream();
                danhMucService.importDataFromExcel(excelFile); // Gọi phương thức nhập liệu từ Excel
            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý lỗi
            }
        }
        return "redirect:/manager/danh-muc"; // Chuyển hướng sau khi nhập liệu thành công hoặc không thành công
    }
}