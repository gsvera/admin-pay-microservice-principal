package com.core.principal.entity;

import com.core.principal.dto.ConfigDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_config")
public class Config {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "config_code")
    private String configCode;
    @Column(name = "config_name")
    private String configName;
    @Column(name = "config_value_string")
    private String configValueString;
    @Column(name = "config_value_int")
    private int configValueInt;
    public Config(ConfigDTO configDTO) {
        this.id = configDTO.getId();
        this.configCode = configDTO.getConfigCode();
        this.configName = configDTO.getConfigName();
        this.configValueString = configDTO.getConfigValueString();
        this.configValueInt = configDTO.getConfigValueInt();
    }
}
