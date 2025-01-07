package com.example.shoeshub;

import com.example.shoeshub.module.chitietsanpham.repository.ChiTietSanPhamRepository;
import com.example.shoeshub.module.chitietsanpham.service.impl.ChiTietSanPhamServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class ShoeshubApplication {
    public final ChiTietSanPhamRepository chiTietSanPhamRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShoeshubApplication.class, args);
    }

    @Bean
    public ChiTietSanPhamServiceImpl chiTietSanPhamService (){
        return new ChiTietSanPhamServiceImpl(chiTietSanPhamRepository);
    }

}
