package com.example.shoeshub.module.size.repository;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import com.example.shoeshub.module.size.entity.Size;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository< Size, Integer> {
    Optional<Size> findByKichthuoc(Integer kichThuoc);

    Optional<Size> findByMasize(Integer masize);

    List<Size> findAllByTrangthai(int trangThai);
}
