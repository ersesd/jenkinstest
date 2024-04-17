package com.sparta.collabobo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardAuthorityEnum {
    WORKER("WORKER"), VIEWER("viewer");

    private final String value;

//    public static boolean validation(String type) {
//        CardAuthorityEnum[] enums = values();
//        for (CardAuthorityEnum target : enums) {
//            if (target.name().equals(type)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static CardAuthorityEnum getEnum(String type) {
//        return Arrays.stream(values()).filter(value -> value.name().equals(type)).findAny()
//            .orElseThrow(NotExistsEnumException::new);
//    }
}
