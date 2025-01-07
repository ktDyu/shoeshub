package com.example.shoeshub.module.buyer.service.impl;

import com.example.shoeshub.module.buyer.entity.SPViewModel;
import com.example.shoeshub.module.buyer.service.CTSPViewModelService;
import com.example.shoeshub.module.buyer.entity.CTSPViewModel;
import com.example.shoeshub.module.chitietsanpham.repository.ChiTietSanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CTSPViewModelServiceImpl implements CTSPViewModelService {
//    private final CTSPViewModelRepository ctspViewModelRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public List<CTSPViewModel> getAll() {
        return chiTietSanPhamRepository.getAll();
    }

    @Override
    public List<SPViewModel> getAllGiay() {
        return chiTietSanPhamRepository.getAllGiay();
    }

    @Override
    public Page<CTSPViewModel> getAllPage(Pageable pageable) {
        return chiTietSanPhamRepository.getAllPage(pageable);
    }

    @Override
    public List<CTSPViewModel> findByIDDanhMuc(int madm) {
        return chiTietSanPhamRepository.findByDanhMuc(madm);
    }
}
