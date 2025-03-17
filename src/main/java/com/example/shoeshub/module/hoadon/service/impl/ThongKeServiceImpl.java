package com.example.shoeshub.module.hoadon.service.impl;

import com.example.shoeshub.module.hoadon.response.TopNguoiMuaHangDTO;
import com.example.shoeshub.module.hoadon.entity.HoaDon;
import com.example.shoeshub.module.hoadon.repository.HoaDonChiTietRepository;
import com.example.shoeshub.module.hoadon.repository.HoaDonRepository;
import com.example.shoeshub.module.hoadon.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ThongKeServiceImpl implements ThongKeService {

    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Override
    public Map<String, Object> thongKeDoanhThuTheoNam(int year) {
        List<BigDecimal> doanhThuTheoThang = new ArrayList<>();
        long soLuongDonHang = 0;
        BigDecimal tongDoanhThu = BigDecimal.ZERO;

        for (int month = 1; month <= 12; month++) {
            Date startDate = getStartOfMonth(year, month);
            Date endDate = getEndOfMonth(year, month);
            List<HoaDon> hoaDons = hoaDonRepository.findByTgttBetween(startDate, endDate);

            BigDecimal totalRevenue = BigDecimal.ZERO;
            for (HoaDon hoaDon : hoaDons) {
                if (hoaDon.getTrangThai() == 3) {
                    totalRevenue = totalRevenue.add(BigDecimal.valueOf(hoaDon.getTongtien()));
                    soLuongDonHang++;
                }
            }
            doanhThuTheoThang.add(totalRevenue);
            tongDoanhThu = tongDoanhThu.add(totalRevenue);
        }

        Map<String, Object> thongkeNam = new HashMap<>();
        thongkeNam.put("doanhThuTheoThang", doanhThuTheoThang);
        thongkeNam.put("soLuongDonHang", soLuongDonHang);
        thongkeNam.put("tongDoanhThu", tongDoanhThu);

        return thongkeNam;
    }



    // Hàm lấy ngày đầu tháng
    private Date getStartOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // Hàm lấy ngày cuối tháng
    private Date getEndOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    @Override
    public List<TopNguoiMuaHangDTO> getTopNguoiMuaHang() {
        return hoaDonRepository.findTopNguoiMuaHang();
    }

    public BigDecimal thongKeDoanhThuTheoNgay(Date date) {
        return hoaDonRepository.thongKeDoanhThuTheoNgay(date);
    }

    // Doanh thu theo tuần
    public BigDecimal thongKeDoanhThuTheoTuan(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Lấy ngày đầu tuần (Chủ nhật)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        // Lấy ngày cuối tuần (Thứ 7)
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();

        return hoaDonRepository.thongKeDoanhThuTheoTuan(startOfWeek, endOfWeek);
    }

    // Doanh thu theo tháng
    public BigDecimal thongKeDoanhThuTheoThang(int year, int month) {
        return hoaDonRepository.thongKeDoanhThuTheoThang(year, month);
    }

    @Override
    public Map<String, Long> thongKeDoanhThuTheoTrangThai() {
        Map<String, Long> thongKe = new HashMap<>();

        // Đếm số lượng đơn hàng theo trạng thái
        long hoanThanh = hoaDonRepository.countByTrangThai(3); // Trạng thái 1 là hoàn thành
        long daHuy = hoaDonRepository.countByTrangThai(4);    // Trạng thái 0 là đã hủy

        thongKe.put("HoanThanh", hoanThanh);
        thongKe.put("DaHuy", daHuy);

        return thongKe;
    }
}