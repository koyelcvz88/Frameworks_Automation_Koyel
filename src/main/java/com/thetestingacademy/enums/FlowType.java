package com.thetestingacademy.enums;

public enum FlowType {

    EXISTING_OC("Existing OC"),
    NEW_OC("New OC");

    private final String excelValue;

    FlowType(String excelValue) {
        this.excelValue = excelValue;
    }

    public String getExcelValue() {
        return excelValue;
    }

    // Safe converter for Excel string → Enum
    public static FlowType from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("FlowType cannot be null");
        }

        for (FlowType type : values()) {
            if (type.excelValue.equalsIgnoreCase(value.trim())
                    || type.name().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid FlowType from Excel: " + value);
    }
}