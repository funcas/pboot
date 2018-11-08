package com.funcas.pboot.conf;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月12日
 */
//@Configuration
public class FastJson {

//    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue
        );
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        List<MediaType> fastMediaTypes = Lists.newArrayList();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(fastMediaTypes);
        converter.setFastJsonConfig(fastJsonConfig);

        return converter;
    }
}
