package com.core.principal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseLoginDTO {
    public String token;
    public String idUser;
    public Object dataUser;
}
