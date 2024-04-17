package com.sparta.collabobo.card.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CardRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String color;
    @NotBlank
    private String status;


}
