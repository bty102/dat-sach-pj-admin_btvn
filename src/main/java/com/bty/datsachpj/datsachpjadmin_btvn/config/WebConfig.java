package com.bty.datsachpj.datsachpjadmin_btvn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${image.book.location}")
    private String IMAGE_BOOK_LOCATION;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image_sach/**")
                .addResourceLocations("file:" + IMAGE_BOOK_LOCATION);
    }
}
