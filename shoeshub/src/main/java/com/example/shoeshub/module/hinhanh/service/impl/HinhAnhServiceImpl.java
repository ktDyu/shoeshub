package com.example.shoeshub.module.hinhanh.service.impl;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.hinhanh.entity.HinhAnh;
import com.example.shoeshub.module.hinhanh.repository.HinhAnhRepository;
import com.example.shoeshub.module.hinhanh.service.HinhAnhService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HinhAnhServiceImpl implements HinhAnhService {

    private  final HinhAnhRepository hinhAnhRepository;
    @Override
    public List<HinhAnh> getAll() {
        return hinhAnhRepository.findAll();
    }

    @Override
    public HinhAnh add(HinhAnh hinhAnh) {
        return hinhAnhRepository.save(hinhAnh);
    }

    @Override
    public void deleteById(int id) {
        var anh =  hinhAnhRepository.findById(id);
        HinhAnh hinhAnh = anh.get();
        hinhAnh.setTrangthai(0);
        hinhAnhRepository.save(hinhAnh);
    }

    @Override
    public Optional<HinhAnh> findById(int id) {
        return hinhAnhRepository.findById(id);
    }

    @Override
    public List<HinhAnh> getAllTrangThai(int trangthai) {
        return hinhAnhRepository.findAllByTrangthai(trangthai);
    }

//    @Override
//    public Optional<HinhAnh> findName(String name) {
//       return hinhAnhRepository.findByUrlimage(name);
//    }
}
