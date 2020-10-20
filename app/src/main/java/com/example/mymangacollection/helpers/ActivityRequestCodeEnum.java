package com.example.mymangacollection.helpers;

public enum ActivityRequestCodeEnum {
    COLECAO(1), EDITORA(2), SERIE(3), VOLUME(4);

    private int value;

    ActivityRequestCodeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
