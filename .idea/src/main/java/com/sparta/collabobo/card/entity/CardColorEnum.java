package com.sparta.collabobo.card.entity;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardColorEnum {
    BLUE("BLUE"), RED("RED"), GREEN("GREEN"), YELLOW("YELLOW");

    private final String cardColor;

    public static boolean isNotExistsColor(String cardColor) {
        CardColorEnum[] cardColorValues = CardColorEnum.values();
        return Arrays.stream(cardColorValues)
            .noneMatch(color -> color.getCardColor().equals(cardColor.toUpperCase())); // cardColor.toUpperCase() 소문자로 들어와도 체킹 혹은 .equalsIgnoreCase

    }
}
