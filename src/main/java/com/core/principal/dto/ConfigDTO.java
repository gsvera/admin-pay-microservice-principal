package com.core.principal.dto;

import com.core.principal.entity.Config;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigDTO {
    private Long id;
    private String configCode;
    private String configName;
    private String configValueString;
    private int configValueInt;
    public ConfigDTO(Config config) {
        this.id = config.getId();
        this.configCode = config.getConfigCode();
        this.configName = config.getConfigName();
        this.configValueString = config.getConfigValueString();
        this.configValueInt = config.getConfigValueInt();
    }
}
