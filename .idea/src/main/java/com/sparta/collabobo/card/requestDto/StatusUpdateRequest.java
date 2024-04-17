package com.sparta.collabobo.card.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StatusUpdateRequest {
    @NotBlank
    private String status;
}
