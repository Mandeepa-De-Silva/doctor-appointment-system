package com.mandeepa.das_backend.server.JwtToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtSignINResponse {
    private String jwtToken;

    public static JwtSignINResponse fromToken(String token) {
        return JwtSignINResponse.builder()
                .jwtToken(token)
                .build();
    }
}
