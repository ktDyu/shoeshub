package com.example.shoeshub.module.chatlieu.repository;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Integer> {

        Optional<ChatLieu> findByTenchatlieu(String ten);

        Optional<ChatLieu> findByMacl(Integer macl);

        List<ChatLieu> findAllByTrangthai(int trangThai);


}

