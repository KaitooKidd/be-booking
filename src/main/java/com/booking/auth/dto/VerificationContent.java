package com.booking.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class VerificationContent {
    private String token;
    private VerificationLinks link;
    private String content;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VerificationLinks {
        private String vn;
        private String en;
    }
}
