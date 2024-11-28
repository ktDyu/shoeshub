package com.example.shoeshub.module.chatlieu.service;

import com.example.shoeshub.module.chatlieu.entity.ChatLieu;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ChatLieuService {
    public List<ChatLieu> getAll();

    public ChatLieu add(ChatLieu chatLieu);

    public Optional<ChatLieu> findName(String name);

    public Optional<ChatLieu> findId(int id);

    public Optional<String> delete(int id);

    List<ChatLieu> getAllTrangThai(int trangthai);

    public void importDataFromExcel(InputStream excelFile);
}
