package com.example.shoeshub.module.chatlieu.service.iml;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.chatlieu.repository.ChatLieuRepository;
import com.example.shoeshub.module.chatlieu.service.ChatLieuService;
import com.example.shoeshub.module.chitietsanpham.repository.ChiTietSanPhamRepository;
import com.example.shoeshub.module.danhmuc.entity.DanhMuc;
import com.example.shoeshub.module.sanpham.entity.SanPham;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatLieuServiceImpl implements ChatLieuService {

    private final ChatLieuRepository chatLieuRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public List<ChatLieu> getAll() {
        return chatLieuRepository.findAll();
    }

    @Override
    public ChatLieu add(ChatLieu chatLieu) {
        return chatLieuRepository.save(chatLieu);
    }

    @Override
    public Optional<ChatLieu> findName(String name) {
        return chatLieuRepository.findByTenchatlieu(name);
    }

    @Override
    public Optional<ChatLieu> findId(int id) {
        return chatLieuRepository.findByMacl(id);
    }

    @Override
    public Optional<String> delete(int id) {
        var chatLieuOptional = chatLieuRepository.findById(id);
        if (chatLieuOptional.isPresent()) {
            ChatLieu chatLieu = chatLieuOptional.get();
            chatLieu.setTrangthai(0);
            chatLieuRepository.save(chatLieu);
            return Optional.of("Xóa thành công");
        }
        return Optional.of("Không tìm thấy vật liệu");
    }

    @Override
    public List<ChatLieu> getAllTrangThai(int trangthai) {
        return chatLieuRepository.findAllByTrangthai(trangthai);
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
                ChatLieu chatLieu = new ChatLieu();
                chatLieu.setTenchatlieu(row.getCell(1).getStringCellValue()); // Cột 0 trong tệp Excel
                chatLieu.setTrangthai(1);
                chatLieuRepository.save(chatLieu);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần
        }
    }


}
