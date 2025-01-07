package com.example.shoeshub.module.mausac.service.impl;

import com.example.shoeshub.module.mausac.entity.MauSac;
import com.example.shoeshub.module.mausac.repository.MauSacRepository;
import com.example.shoeshub.module.mausac.response.MauSacPageResponse;
import com.example.shoeshub.module.mausac.service.MauSacService;
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

@Service
@RequiredArgsConstructor
public class MauSacServiceImpl implements MauSacService {

    private final MauSacRepository mauSacRepository;

    @Override
    public List<MauSac> getAll() {
        return mauSacRepository.findAll();
    }

    @Override
    public MauSac add(MauSac mauSac) {
        return mauSacRepository.save(mauSac);
    }

    @Override
    public Optional<MauSac> findName(String name) {
        return mauSacRepository.findByTenmau(name);
    }

    @Override
    public Optional<MauSac> findId(int id) {
        return mauSacRepository.findById(id);
    }

    @Override
    public Optional<String> delete(int id) {
        var mauSacOptional = mauSacRepository.findById(id);
        if (mauSacOptional.isPresent()) {
            MauSac mauSac = mauSacOptional.get();
            mauSac.setTrangthai(0);
            mauSacRepository.save(mauSac);
            return Optional.of("Xóa thành công");
        }
        return Optional.of("Không tìm thấy màu sắc");
    }

    @Override
    public List<MauSac> getAllTrangThai(int trangthai) {
        return mauSacRepository.findAllByTrangthai(trangthai);
    }

    @Override
    public void importDataFromExcel(InputStream excelFile) {
        try (Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                MauSac mauSac = new MauSac();
                mauSac.setTenmau(row.getCell(1).getStringCellValue());
                mauSac.setTrangthai(1);
                mauSacRepository.save(mauSac);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
