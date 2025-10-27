package com.example.salon_management.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FutureDateTimeValidator implements ConstraintValidator<FutureDateTime, LocalDateTime> {

    private int hours;

    @Override
    public void initialize(FutureDateTime constraintAnnotation) {
        this.hours = constraintAnnotation.hours();
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null validation
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minimumDateTime = now.plus(hours, ChronoUnit.HOURS);

        return value.isAfter(minimumDateTime);
    }
}


