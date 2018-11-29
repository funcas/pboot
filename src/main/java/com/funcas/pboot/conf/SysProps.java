package com.funcas.pboot.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月28日
 */
@Data
@Component
@ConfigurationProperties(prefix = "props")
public class SysProps {

    private String appid;
    private String secret;
}
