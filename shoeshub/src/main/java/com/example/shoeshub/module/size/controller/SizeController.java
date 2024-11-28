package com.example.shoeshub.module.size.controller;


import com.example.shoeshub.module.size.entity.Size;
import com.example.shoeshub.module.size.service.SizeService;
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
@RequiredArgsConstructor
@RequestMapping("/manager")
public class SizeController {
    private final SizeService sizeService;

    private final HttpSession session;

    @ModelAttribute("dsTrangThai")
    public Map<Integer, String> getDsTrangThai() {
        Map<Integer, String> dsTrangThai = new HashMap<>();
        dsTrangThai.put(1, "Hoạt động");
        dsTrangThai.put(0, "Không hoạt động");
        return dsTrangThai;
    }

    @GetMapping("/size")
    public ModelAndView dsSize(Model model,
                                   @ModelAttribute("message") String message,
                                   @ModelAttribute("kichThuocError") String kichThuocError,
                                   @ModelAttribute("error") String error,
                                   @ModelAttribute("userInput") Size userInput,
                                   @ModelAttribute("Errormessage") String Errormessage
                                  ) {
        ModelAndView modelAndView = new ModelAndView("manager/size");

        model.addAttribute("size", sizeService.getAll());
        model.addAttribute("sizeAdd", new Size());
        model.addAttribute("message", "true".equals(message));
        model.addAttribute("kichThuocError", "kichThuocError".equals(error));
        model.addAttribute("sizeAdd", userInput != null ? userInput : new Size());
        model.addAttribute("Errormessage", "true".equals(Errormessage));

        return modelAndView;
    }

    @PostMapping("/size/viewAdd/add")
    public String addSize(@Valid @ModelAttribute("size") Size  size,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("kichthuoc")) {
                redirectAttributes.addFlashAttribute("userInput", size);
                redirectAttributes.addFlashAttribute("error", "kichThuocError");
            }
            return "redirect:/manager/size";
        }

        var existingSize = sizeService.findKichthuoc(size.getKichthuoc());
        if (existingSize.isPresent()) {
            redirectAttributes.addFlashAttribute("userInput", size);
            redirectAttributes.addFlashAttribute("Errormessage", true);
            return "redirect:/manager/size";
        }

        size.setTrangthai(1);
        sizeService.add(size);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/manager/size";
    }

    @GetMapping("/size/viewUpdate/{id}")
    public String viewUpdateSize(@PathVariable int id, Model model,
                                     @ModelAttribute("message") String message,
                                     @ModelAttribute("kichThuocError") String kichThuocError,
                                     @ModelAttribute("error") String error,
                                     @ModelAttribute("userInput") Size userInput,
                                     @ModelAttribute("Errormessage") String Errormessage) {
        var size = sizeService.findId(id);
        model.addAttribute("size", size.orElse(null));

        model.addAttribute("message", "true".equals(message));
        model.addAttribute("kichThuocError", "kichThuocError".equals(error));
        model.addAttribute("sizeAdd", userInput != null ? userInput : new Size());
        model.addAttribute("Errormessage", "true".equals(Errormessage));
        session.setAttribute("id", id);

        return "manager/update-size";
    }

    @PostMapping("/size/viewUpdate/{id}")
    public String updateSize(@PathVariable int id, @Valid @ModelAttribute("size") Size size,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        var size1 = sizeService.findId(id);
        int idSize = (int) session.getAttribute("id");
        String link = "redirect:/manager/size/viewUpdate/" + idSize;

        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("kichthuoc")) {
                redirectAttributes.addFlashAttribute("userInput", size);
                redirectAttributes.addFlashAttribute("error", "kichThuocError");
            }
            return link;
        }
        var existingSize = sizeService.findKichthuoc(size.getKichthuoc());
        if (existingSize.isPresent()) {
            redirectAttributes.addFlashAttribute("userInput", size);
            redirectAttributes.addFlashAttribute("Errormessage", true);
            return link;
        }

        size1.ifPresent(c -> {
            c.setKichthuoc(size.getKichthuoc());
            c.setTrangthai(size.getTrangthai());
            sizeService.add(c);
            redirectAttributes.addFlashAttribute("message", true);
        });

        return "redirect:/manager/size";
    }

    @GetMapping("/size/delete/{id}")
    public String deleteSize(@PathVariable int id, RedirectAttributes redirectAttributes) {
        sizeService.delete(id);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/manager/size";
    }
    @PostMapping("/size/import")
    public String importData(@RequestParam("file") MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                InputStream excelFile = file.getInputStream();
                sizeService.importDataFromExcel(excelFile); // Gọi phương thức nhập liệu từ Excel
            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý lỗi
            }
        }
        return "redirect:/manager/size"; // Chuyển hướng sau khi nhập liệu thành công hoặc không thành công
    }
}
