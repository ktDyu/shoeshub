package com.example.shoeshub.module.danhmuc.service.impl;

import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import com.example.shoeshub.module.danhmuc.repository.DanhMucRepository;
import com.example.shoeshub.module.danhmuc.service.DanhMucService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DanhMucServiceImpl implements DanhMucService {

    private final DanhMucRepository danhMucRepository;

    @Override
    public List<DanhMuc> getAll() {
        return danhMucRepository.findAll();
    }

    @Override
    public DanhMuc add(DanhMuc danhMuc) {
        return danhMucRepository.save(danhMuc);
    }

    @Override
    public Optional<DanhMuc> findName(String name) {
        return danhMucRepository.findByTendanhmuc(name);
    }

    @Override
    public Optional<DanhMuc> findId(int id) {
        return danhMucRepository.findById(id);
    }

    @Override
    public Optional<String> delete(int id) {
        var danhMucOptional = danhMucRepository.findById(id);
        if (danhMucOptional.isPresent()) {
            DanhMuc danhMuc = danhMucOptional.get();
            danhMuc.setTrangthai(0); // Đánh dấu là không hoạt động
            danhMucRepository.save(danhMuc);
            return Optional.of("Xóa thành công");
        }
        return Optional.of("Không tìm thấy danh mục");
    }

    @Override
    public List<DanhMuc> getAllTrangThai(int trangthai) {
        return danhMucRepository.findAllByTrangthai(trangthai);
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
                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setTendanhmuc(row.getCell(1).getStringCellValue()); // Cột 0 trong tệp Excel
                danhMuc.setTrangthai(1);
                danhMucRepository.save(danhMuc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần
        }
    }
}
