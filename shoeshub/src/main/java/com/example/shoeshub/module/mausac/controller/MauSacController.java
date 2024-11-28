package com.example.shoeshub.module.mausac.controller;

import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.mausac.service.MauSacService;
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
public class MauSacController {

    private final MauSacService mauSacService;
    private final HttpSession session;

    @ModelAttribute("dsTrangThai")
    public Map<Integer, String> getDsTrangThai() {
        Map<Integer, String> dsTrangThai = new HashMap<>();
        dsTrangThai.put(1, "Hoạt động");
        dsTrangThai.put(0, "Không hoạt động");
        return dsTrangThai;
    }

    @GetMapping("/mau-sac")
    public ModelAndView dsMauSac(Model model,
                                 @ModelAttribute("message") String message,
                                 @ModelAttribute("rgpError") String maMauError,
                                 @ModelAttribute("tenMauError") String tenMauError,
                                 @ModelAttribute("error") String error,
                                 @ModelAttribute("userInput") MauSac userInput,
                                 @ModelAttribute("Errormessage") String Errormessage) {
        ModelAndView modelAndView = new ModelAndView("manager/mau-sac");

        model.addAttribute("mauSac", mauSacService.getAll());
        model.addAttribute("mauSacAdd", new MauSac());
//        model.addAttribute("mauSacAdd", userInput != null ? userInput : new MauSac());
//        model.addAttribute("Errormessage", "true".equals(Errormessage));
        if (message == null || !"true".equals(message)) {
            model.addAttribute("message", false);
        }
        if (maMauError == null || !"rgpError".equals(error)) {
            model.addAttribute("rgpError", false);
        }
        if (tenMauError == null || !"tenMauError".equals(error)) {
            model.addAttribute("tenMauError", false);
        }
        // Kiểm tra xem có dữ liệu người dùng đã nhập không và điền lại vào trường nhập liệu
        if (userInput != null) {
            model.addAttribute("mauSacAdd", userInput);
        }
        //
        if (Errormessage == null || !"true".equals(Errormessage)) {
            model.addAttribute("Errormessage", false);
        }
        return modelAndView;
    }

    @PostMapping("/mau-sac/viewAdd/add")
    public String addMauSac(@Valid @ModelAttribute("mauSac") MauSac mauSac,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasFieldErrors("rgp")) {
            redirectAttributes.addFlashAttribute("userInput", mauSac);
            redirectAttributes.addFlashAttribute("error", "rgpError");
        }

        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tenmau")) {
                redirectAttributes.addFlashAttribute("userInput", mauSac);
                redirectAttributes.addFlashAttribute("error", "tenMauError");
            }
            return "redirect:/manager/mau-sac";
        }

        var existingMauSac = mauSacService.findName(mauSac.getTenmau());
        if (existingMauSac.isPresent()) {
            redirectAttributes.addFlashAttribute("userInput", mauSac);
            redirectAttributes.addFlashAttribute("Errormessage", true);
            return "redirect:/manager/mau-sac";
        }

        mauSac.setTrangthai(1);
        mauSacService.add(mauSac);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/manager/mau-sac";
    }

    @GetMapping("/mau-sac/viewUpdate/{id}")
    public String viewUpdateMauSac(@PathVariable int id, Model model,
                                   @ModelAttribute("message") String message,
                                   @ModelAttribute("tenMauError") String tenMauError,
                                   @ModelAttribute("error") String error,
                                   @ModelAttribute("userInput") MauSac userInput,
                                   @ModelAttribute("Errormessage") String Errormessage) {
        var mauSac = mauSacService.findId(id);
        model.addAttribute("mauSac", mauSac.orElse(null));

        model.addAttribute("message", "true".equals(message));
        model.addAttribute("tenMauError", "tenMauError".equals(error));
        model.addAttribute("mauSacAdd", userInput != null ? userInput : new MauSac());
        model.addAttribute("Errormessage", "true".equals(Errormessage));
        session.setAttribute("id", id);

        return "manager/update-mau-sac";
    }

    @PostMapping("/mau-sac/viewUpdate/{id}")
    public String updateMauSac(@PathVariable int id, @Valid @ModelAttribute("mauSac") MauSac mauSac,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        var mauSacDb = mauSacService.findId(id);
        int idMauSac = (int) session.getAttribute("id");
        String link = "redirect:/manager/mau-sac/viewUpdate/" + idMauSac;

        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tenMau")) {
                redirectAttributes.addFlashAttribute("userInput", mauSac);
                redirectAttributes.addFlashAttribute("error", "tenMauError");
            }
            return link;
        }

        var existingMauSac = mauSacService.findName(mauSac.getTenmau());

        if (existingMauSac.isPresent()) {
            if (mauSacDb.get().getTenmau() != null) {
                if (!mauSacDb.get().getTenmau().equals(mauSac.getTenmau())) {
                    redirectAttributes.addFlashAttribute("userInput", mauSac);
                    redirectAttributes.addFlashAttribute("Errormessage", true);
                    return link;
                }
            }

        }

        mauSacDb.ifPresent(m -> {
            m.setRgp(mauSac.getRgp());
            m.setTenmau(mauSac.getTenmau());
            m.setTrangthai(mauSac.getTrangthai());
            mauSacService.add(m);
            redirectAttributes.addFlashAttribute("message", true);
        });

        return "redirect:/manager/mau-sac";
    }

    @GetMapping("/mau-sac/delete/{id}")
    public String deleteMauSac(@PathVariable int id, RedirectAttributes redirectAttributes) {
        mauSacService.delete(id);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/manager/mau-sac";
    }

    @PostMapping("/mauSac/import")
    public String importData(@RequestParam("file") MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                InputStream excelFile = file.getInputStream();
                mauSacService.importDataFromExcel(excelFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/manager/mau-sac";
    }
}
