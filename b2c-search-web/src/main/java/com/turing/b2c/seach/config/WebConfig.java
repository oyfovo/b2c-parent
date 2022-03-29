package com.turing.b2c.search.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制器
 * @author HHF-OVO
 * @date 2021/12/29 11:57
 * @param
 * @return null
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 控制跳转页
     * @author HHF-OVO
     * @date 2021/12/29 11:58
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("search");
    }
    /**
     * 返回jSON格式化
     * @author HHF-OVO
     * @date 2021/12/29 11:58
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> types = new ArrayList<>();
        types.add(MediaType.APPLICATION_JSON);
        types.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(types);
        converters.add(0, converter);
    }

}
