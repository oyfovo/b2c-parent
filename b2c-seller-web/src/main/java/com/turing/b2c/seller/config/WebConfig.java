package com.turing.b2c.seller.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author H ovo
 * @date 2021年12月09日 11:45
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 批量配置视图层
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("shoplogin");
        registry.addViewController("/admin/index.html").setViewName("admin/index");
        registry.addViewController("/admin/home.html").setViewName("admin/home");
        registry.addViewController("/admin/seller.html").setViewName("admin/seller");
        registry.addViewController("/admin/password.html").setViewName("admin/password");
        registry.addViewController("/admin/goods.html").setViewName("admin/goods");
        registry.addViewController("/admin/goodsEdit.html").setViewName("admin/goodsEdit");

    }
    //配置FastJSON
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
     *
     * @param converters initially an empty list of converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(mediaTypes);
        //设置json的输出格式
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
        converter.setFastJsonConfig(config);

        converters.add(0,converter);
    }
}
