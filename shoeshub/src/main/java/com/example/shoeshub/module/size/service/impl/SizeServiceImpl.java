package com.example.shoeshub.module.size.service.impl;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.size.entity.Size;
import com.example.shoeshub.module.size.repository.SizeRepository;
import com.example.shoeshub.module.size.response.SizePageResponse;
import com.example.shoeshub.module.size.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SizeServiceImpl implements SizeService {
    private final SizeRepository sizeRepository;


    @Override
    public List<Size> getAll() {
        return sizeRepository.findAll();
    }

    @Override
    public Size add(Size size) {
        return sizeRepository.save(size);
    }

    @Override
    public Optional<Size> findKichthuoc(int kichThuoc) {
       return sizeRepository.findByKichthuoc(kichThuoc);
    }

    @Override
    public Optional<Size> findId(int id) {
        return sizeRepository.findByMasize(id);
    }

    @Override
    public Optional<String> delete(int id) {
        var chatLieuOptional = sizeRepository.findById(id);
        if (chatLieuOptional.isPresent()) {
            Size size = chatLieuOptional.get();
            size.setTrangthai(0);
            sizeRepository.save(size);
            return Optional.of("Xóa thành công");
        }
        return Optional.of("Không tìm thấy vật liệu");
    }

    @Override
    public List<Size> getAllTrangThai(int trangthai) {
        return sizeRepository.findByTrangthaiOrderByKichthuoc(trangthai);
    }

    @Override
    public void importDataFromExcel(InputStream excelFile) {

        try (Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên (index 0)

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Bỏ qua hàng đầu tiên nếu nó là tiêu đề
                    continue;
                }
                Size size = new Size();
                size.setKichthuoc(Integer.parseInt(row.getCell(1).getStringCellValue()));; // Cột 0 trong tệp Excel
                size.setTrangthai(1);
                sizeRepository.save(size);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần
        }
    }

}
