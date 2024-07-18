package com.smilehelper.application.enums;

public enum ExpressionType {
    RAISE_EYEBROWS("눈썹 올리기"),
    BLINK("눈 감기"),
    PUFF_CHEEKS("볼 부풀리기"),
    PUCKER_LIPS("입 오므리기"),
    OPEN_MOUTH("입 벌리기"),
    SURPRISE("놀람"),
    SMILE("웃음"),
    FROWN("찡그림"),
    TEMP2("임시2"),
    TEMP3("임시3");

    private final String koreanDescription;

    ExpressionType(String koreanDescription) {
        this.koreanDescription = koreanDescription;
    }

    public String getKoreanDescription() {
        return koreanDescription;
    }
}