package com.askus.askus.global.error;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String title;
    private String status;
    private String detail;
}
