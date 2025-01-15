package com.example.shoeshub.module.sanpham.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SanPhamResponse {

    private Integer masp;
    private String tensp;
    private Integer trangthai;
    private String tendanhmuc;
    private Long tongsp;

    public SanPhamResponse(Integer masp, String tensp, Integer trangthai, String tendanhmuc, Long tongsp) {
        this.masp = masp;
        this.tensp = tensp;
        this.trangthai = trangthai;
        this.tendanhmuc = tendanhmuc;
        this.tongsp = tongsp;
    }
}
