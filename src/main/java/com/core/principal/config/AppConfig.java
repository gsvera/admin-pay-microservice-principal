package com.core.principal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${app.codefolio}")
    private String appCodeFolio;

    public String getAppCodeFolio() {
        return appCodeFolio;
    }
}
