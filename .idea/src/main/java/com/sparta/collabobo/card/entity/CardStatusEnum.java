package com.sparta.collabobo.card.entity;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardStatusEnum {
    BACKLOG("BACKLOG"), IN_PROGRESS("IN_PROGRESS"), COMPLETED("COMPLETED");

    private final String status;

    public static boolean isNotExistsStatus(String cardStatus) {
        CardStatusEnum[] cardStatusValues = CardStatusEnum.values();
        return Arrays.stream(cardStatusValues)
            .noneMatch(status -> status.getStatus().equals(cardStatus.toUpperCase())); // cardColor.toUpperCase() 소문자로 들어와도 체킹 혹은 .equalsIgnoreCase

    }
}
