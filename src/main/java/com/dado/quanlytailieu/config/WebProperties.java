package com.dado.quanlytailieu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "web")
@Validated
public record WebProperties(
        String imageItemLocation,
        String pluginsLocation,
        String appUrl,
        String adminUrl
) {

}
