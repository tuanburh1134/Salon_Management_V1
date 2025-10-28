package com.example.salon_management.entity;

public enum PaymentMethod {
    CASH("Tiền mặt"),
    BANK_TRANSFER("Chuyển khoản"),
    CARD("Thẻ"),
    E_WALLET("Ví điện tử");

    private final String vietnameseName;

    PaymentMethod(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }

    @Override
    public String toString() {
        return vietnameseName;
    }
}
