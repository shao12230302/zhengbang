package com.zb.byb.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eas")
@Getter
@Setter
public class EasConfig {
    private String loginUrl;
    private String taskUrl;
    private String batchUrl;
    private String dcName;
    private String userName;
    private String password;
}
