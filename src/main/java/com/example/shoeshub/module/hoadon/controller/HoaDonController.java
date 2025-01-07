package com.example.shoeshub.module.hoadon.controller;

import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.entity.HoaDonChiTiet;
import com.example.shoeshub.module.hoadon.service.HoaDonChiTietService;
import com.example.shoeshub.module.hoadon.service.HoaDonService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;


    @GetMapping("/hoa-don")
    public String searchHoaDon(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        List<HoaDon> hoaDons;

        // Kiểm tra nếu có keyword và gọi phương thức tìm kiếm theo tên người nhận hoặc SDT
        if (keyword != null && !keyword.isEmpty()) {
            hoaDons = hoaDonService.searchHoaDons(keyword);
        } else if (date != null) {
            hoaDons = hoaDonService.getHoaDonsByDate(date);
        } else {
            hoaDons = hoaDonService.getAllHoaDons(); // Trả về tất cả hóa đơn nếu không có tìm kiếm
        }

        model.addAttribute("hoaDons", hoaDons);
        model.addAttribute("date", date);
        model.addAttribute("keyword", keyword); // Thêm keyword vào model để giữ giá trị trên form

        return "manager/hoa-don"; // Trả về trang hiển thị danh sách hóa đơn
    }

    @GetMapping("/hoa-don/{mahd}")
    public String showHoaDonDetails(@PathVariable("mahd") Integer mahd, Model model) {
        HoaDon hoaDon = hoaDonService.findById(mahd);
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.findByHoaDon(hoaDon);
        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("hoaDonChiTietList", hoaDonChiTietList);
        return "manager/hoa-don-details"; // Trỏ đến file giao diện
    }

    @PostMapping("/hoa-don/update-trang-thai")
    public String updateTrangThai(
            @RequestParam("hoaDonId") int hoaDonId,
            @RequestParam("trangThai") int trangThai,
            RedirectAttributes redirectAttributes) {

        try {
            HoaDon hoaDon = hoaDonService.findById(hoaDonId);

            // Không cho phép cập nhật nếu trạng thái là "Hoàn thành"
            if (hoaDon.getTrangThai() == 2) {
                redirectAttributes.addFlashAttribute("error", "Không thể thay đổi trạng thái 'Hoàn thành'.");
                return "redirect:/manager/hoa-don";
            }

            // Cập nhật trạng thái của hóa đơn
            hoaDon.setTrangThai(trangThai);
            hoaDonService.save(hoaDon); // Lưu lại thay đổi

            redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cập nhật trạng thái thất bại!");
        }

        return "redirect:/manager/hoa-don"; // Quay lại danh sách hóa đơn
    }


    @GetMapping("/hoa-don/filter")
    public String filterHoaDonByTrangThai(@RequestParam(value = "trangThai", required = false) Integer trangThai, Model model) {
        List<HoaDon> hoaDons;
        if (trangThai != null) {
            hoaDons = hoaDonService.getHoaDonByTrangThai(trangThai);
        } else {
            hoaDons = hoaDonService.getAllHoaDons(); // Lấy tất cả nếu không có trạng thái
        }
        model.addAttribute("hoaDons", hoaDons);
        model.addAttribute("trangThai", trangThai); // Để giữ trạng thái đã chọn
        return "manager/hoa-don"; // Tên file HTML trong thư mục templates
    }

    @GetMapping("/hoa-don/export")
    public ResponseEntity<byte[]> exportHoaDonToExcel(
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(value = "keyword", required = false) String keyword) {

        // Lấy danh sách hóa đơn
        List<HoaDon> hoaDons;
        if (keyword != null && !keyword.isEmpty()) {
            hoaDons = hoaDonService.searchHoaDons(keyword);
        } else if (date != null) {
            hoaDons = hoaDonService.getHoaDonsByDate(date);
        } else {
            hoaDons = hoaDonService.getAllHoaDons();
        }

        // Tạo workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hoa Dons");

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã Hóa Đơn", "Ngày Tạo", "Tổng Tiền", "Trạng Thái", "Mã Nhân Viên", "Ghi Chú"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Tạo CellStyle cho ngày tháng
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        // Dữ liệu hóa đơn
        int rowNum = 1;
        for (HoaDon hoaDon : hoaDons) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(hoaDon.getMahd());
            Cell dateCell = row.createCell(1);
            if (hoaDon.getTgtt() != null) {
                dateCell.setCellValue(hoaDon.getTgtt());
                dateCell.setCellStyle(dateCellStyle);
            }
            row.createCell(2).setCellValue(hoaDon.getTongtien());
            row.createCell(3).setCellValue(
                    hoaDon.getTrangThai() != null && hoaDon.getTrangThai() == 1 ? "Đã thanh toán" : "Chưa thanh toán"
            );
            row.createCell(5).setCellValue(hoaDon.getGhiChu() != null ? hoaDon.getGhiChu() : "");
        }

        // Xử lý lưu file vào OutputStream
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            byte[] excelData = byteArrayOutputStream.toByteArray();

            // Tạo response chứa file Excel
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=hoa_don.xlsx");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(excelData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


}
