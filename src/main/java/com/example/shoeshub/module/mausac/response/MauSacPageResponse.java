package com.example.shoeshub.module.mausac.response;

import com.example.shoeshub.module.mausac.entity.MauSac;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MauSacPageResponse {

    private List<MauSac> list;

    private int totalPage;

    private int totalNumber;

    public MauSacPageResponse(List<MauSac> list, int totalPage, int totalNumber) {
        this.list = list;
        this.totalPage = totalPage;
        this.totalNumber = totalNumber;
    }

}
