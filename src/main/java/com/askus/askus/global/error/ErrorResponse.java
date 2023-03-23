package com.askus.askus.global.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String title;
    private String status;
    private String detail;
}
