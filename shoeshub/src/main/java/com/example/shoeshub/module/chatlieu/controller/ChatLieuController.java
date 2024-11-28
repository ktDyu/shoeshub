package com.example.shoeshub.module.chatlieu.controller;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.chatlieu.service.ChatLieuService;
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
public class ChatLieuController {

    private final ChatLieuService chatLieuService;
    private final HttpSession session;

    @ModelAttribute("dsTrangThai")
    public Map<Integer, String> getDsTrangThai() {
        Map<Integer, String> dsTrangThai = new HashMap<>();
        dsTrangThai.put(1, "Hoạt động");
        dsTrangThai.put(0, "Không hoạt động");
        return dsTrangThai;
    }

    @GetMapping("/chat-lieu")
    public ModelAndView dsChatLieu(Model model,
                                   @ModelAttribute("message") String message,
                                   @ModelAttribute("tenChatLieuError") String tenChatLieuError,
                                   @ModelAttribute("error") String error,
                                   @ModelAttribute("userInput") ChatLieu userInput,
                                   @ModelAttribute("Errormessage") String Errormessage
                                   ) {
        ModelAndView modelAndView = new ModelAndView("manager/chat-lieu");

        model.addAttribute("chatLieu", chatLieuService.getAll());
        model.addAttribute("chatLieuAdd", new ChatLieu());
        model.addAttribute("message", "true".equals(message));
        model.addAttribute("tenChatLieuError", "tenChatLieuError".equals(error));
        model.addAttribute("chatLieuAdd", userInput != null ? userInput : new ChatLieu());
        model.addAttribute("Errormessage", "true".equals(Errormessage));

        return modelAndView;
    }

    @PostMapping("/chat-lieu/viewAdd/add")
    public String addChatLieu(@Valid @ModelAttribute("chatLieu") ChatLieu chatLieu,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tenchatlieu")) {
                redirectAttributes.addFlashAttribute("userInput", chatLieu);
                redirectAttributes.addFlashAttribute("error", "tenChatLieuError");
            }
            return "redirect:/manager/chat-lieu";
        }

        var existingChatLieu = chatLieuService.findName(chatLieu.getTenchatlieu());
        if (existingChatLieu.isPresent()) {
            redirectAttributes.addFlashAttribute("userInput", chatLieu);
            redirectAttributes.addFlashAttribute("Errormessage", true);
            return "redirect:/manager/chat-lieu";
        }

        chatLieu.setTrangthai(1);
        chatLieuService.add(chatLieu);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/manager/chat-lieu";
    }

    @GetMapping("/chat-lieu/viewUpdate/{id}")
    public String viewUpdateChatLieu(@PathVariable int id, Model model,
                                     @ModelAttribute("message") String message,
                                     @ModelAttribute("tenChatLieuError") String tenChatLieuError,
                                     @ModelAttribute("error") String error,
                                     @ModelAttribute("userInput") ChatLieu userInput,
                                     @ModelAttribute("Errormessage") String Errormessage) {
        var chatLieu = chatLieuService.findId(id);
        model.addAttribute("chatLieu", chatLieu.orElse(null));

        model.addAttribute("message", "true".equals(message));
        model.addAttribute("tenChatLieuError", "tenChatLieuError".equals(error));
        model.addAttribute("chatLieuAdd", userInput != null ? userInput : new ChatLieu());
        model.addAttribute("Errormessage", "true".equals(Errormessage));
        session.setAttribute("id", id);

        return "manager/update-chat-lieu";
    }

    @PostMapping("/chat-lieu/viewUpdate/{id}")
    public String updateChatLieu(@PathVariable int id, @Valid @ModelAttribute("chatLieu") ChatLieu chatLieu,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        var chatLieuDb = chatLieuService.findId(id);
        int idChatLieu = (int) session.getAttribute("id");
        String link = "redirect:/manager/chat-lieu/viewUpdate/" + idChatLieu;

        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("tenchatlieu")) {
                redirectAttributes.addFlashAttribute("userInput", chatLieu);
                redirectAttributes.addFlashAttribute("error", "tenChatLieuError");
            }
            return link;
        }

        var existingChatLieu = chatLieuService.findName(chatLieu.getTenchatlieu());
        if (existingChatLieu.isPresent()) {
                if(chatLieuDb.get().getTenchatlieu() != null) {
                    if (!chatLieuDb.get().getTenchatlieu().equals(chatLieu.getTenchatlieu())) {
                        redirectAttributes.addFlashAttribute("userInput", chatLieu);
                        redirectAttributes.addFlashAttribute("Errormessage", true);
                        return link;
                    }
                }
        }

        chatLieuDb.ifPresent(c -> {
            c.setTenchatlieu(chatLieu.getTenchatlieu());
            c.setTrangthai(chatLieu.getTrangthai());
            chatLieuService.add(c);
            redirectAttributes.addFlashAttribute("message", true);
        });

        return "redirect:/manager/chat-lieu";
    }

    @GetMapping("/chat-lieu/delete/{id}")
    public String deleteChatLieu(@PathVariable int id, RedirectAttributes redirectAttributes) {
        chatLieuService.delete(id);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/manager/chat-lieu";
    }

    @PostMapping("/chatLieu/import")
    public String importData(@RequestParam("file") MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                InputStream excelFile = file.getInputStream();
                chatLieuService.importDataFromExcel(excelFile); // Gọi phương thức nhập liệu từ Excel
            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý lỗi
            }
        }
        return "redirect:/manager/chat-lieu"; // Chuyển hướng sau khi nhập liệu thành công hoặc không thành công
    }
//    @GetMapping("chat-lieu/export/pdf")
//    public void exportToPDFChatLieu(HttpServletResponse response) throws DocumentException, IOException {
//        chatLieuService.exportChatLieuToPDF(response);
//    }

}

