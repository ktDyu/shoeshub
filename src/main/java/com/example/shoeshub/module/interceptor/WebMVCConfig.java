package com.example.shoeshub.module.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private BuyerLoginInterceptor buyerLoginInterceptor;

    @Autowired
    private ManageLoginInterceptor manageLoginInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Disable caching for all resources
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(buyerLoginInterceptor)
                .addPathPatterns("/buyer/cart/**", "/buyer/checkout/**", "/buyer/addresses/**", "/buyer/setting/**", "/buyer/purchase/**", "/buyer/shop/addProductCart/**", "/buyer/shop/buyNowButton/**")
                .excludePathPatterns("/buyer/login/**", "/buyer/register/**");
//        registry.addInterceptor(manageLoginInterceptor)
//                .addPathPatterns("/manager/hoa-don/**","/manager/thongke/**", "/manager/khachhang/**", "/manager/san-pham/**", "/manager/chat-lieu/**", "/manager/danh-muc/**", "/manager/mau-sac/**", "/manager/size/**", "/manager/hinh-anh/**")
//                .excludePathPatterns("/manager/login/**");
    }

    @Bean("messageSource")
    public MessageSource loadMessageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();

        source.setBasenames(
                "classpath:/message/valiate",
                "classpath:/i18n/home"
        );
        source.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return source;
    }

    @Bean("localeResolver")
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieMaxAge(60);
        cookieLocaleResolver.setCookiePath("/");
        cookieLocaleResolver.setDefaultLocale(new Locale("en"));
        return cookieLocaleResolver;
    }


}
