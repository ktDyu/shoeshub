package com.example.shoeshub.module.hoadon.response;

public class TopNguoiMuaHangDTO {
    private String tenkh;
    private Double tongtien;

    // Constructor nhận vào tên khách hàng và tổng tiền
    public TopNguoiMuaHangDTO(String tenkh, Double tongtien) {
        this.tenkh = tenkh;
        this.tongtien = tongtien;
    }

    // Getter và Setter
    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public Double getTongtien() {
        return tongtien;
    }

    public void setTongtien(Double tongtien) {
        this.tongtien = tongtien;
    }
}

