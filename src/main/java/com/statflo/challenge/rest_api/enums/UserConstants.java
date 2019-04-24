package com.statflo.challenge.rest_api.enums;


public enum UserConstants {
    ROLE("role"),
    ID("id"),
    NAME("name");
    String value;

    UserConstants(String value) {
        this.value = value;
    }

    public UserConstants getUserConstants(String value) {
        for (UserConstants userConstants : UserConstants.values()) {
            if (userConstants.value.equals(value))
                return userConstants;
        }
        throw new IllegalArgumentException("Invalid value passed");
    }

    public static boolean isValidValue(String value) {
        for (UserConstants userConstants : UserConstants.values()) {
            if (userConstants.value.equals(value))
                return true;
        }
        return false;
    }

    public String getValue() {
        return value;
    }
}


