package com.example.shoeshub.module.buyer.service;


import com.example.shoeshub.module.buyer.entity.CTSPViewModel;
import com.example.shoeshub.module.buyer.entity.SPViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CTSPViewModelService {
    List<CTSPViewModel> getAll();

    List<SPViewModel> getAllGiay();

    Page<CTSPViewModel> getAllPage(Pageable pageable);

    List<CTSPViewModel> findByIDDanhMuc(int madm);
//
//    List<CTSPViewModel> getAllProductPromotion();
//
//    List<CTSPViewModel> getAllProductNonPromotion();
//
//    Page<CTSPViewModel> getAllPage(Pageable pageable);
//
//    List<CTSPViewModel> getAllSoldOff();
//
//    CTSPViewModel findByIDGiayAndMau(UUID idGiay, UUID idMau);
//

//
//    Page<CTSPViewModel> getAllByPriceHighToLow(Pageable pageable);
//
//    Page<CTSPViewModel> getAllByPriceLowToHigh(Pageable pageable);
//
//    List<CTSPViewModel> getAllOrderTgNhap();
//
//    List<CTSPViewModel> getAllOrderBestSeller();
}
