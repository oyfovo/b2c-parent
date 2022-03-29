package com.turing.b2c.manager.web.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 跳转控制器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 跳转控制器
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/admin/index.html").setViewName("admin/index");
        registry.addViewController("/admin/home.html").setViewName("admin/home");
        registry.addViewController("/admin/brand.html").setViewName("admin/brand");
        registry.addViewController("/admin/specification.html").setViewName("admin/specification");
        registry.addViewController("/admin/typeTemplate.html").setViewName("admin/typeTemplate");
    }

    /**
     * Configure the {@link HttpMessageConverter HttpMessageConverter}s for
     * reading from the request body and for writing to the response body.
     * <p>By default, all built-in converters are configured as long as the
     * corresponding 3rd party libraries such Jackson JSON, JAXB2, and others
     * are present on the classpath.
     * <p><strong>Note</strong> use of this method turns off default converter
     * registration. Alternatively, use
     * {@link #extendMessageConverters(List)} to modify that default
     * list of converters.
     *  把Jackson替换成Fastjson
     * @param converters initially an empty list of converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>();
        //这是一个JSON
        mediaTypes.add(MediaType.APPLICATION_JSON);
        //编码格式
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(mediaTypes);
        //0表示优先级最高，就可以替换掉默认的jackson
        converters.add(0,converter);
    }

}
