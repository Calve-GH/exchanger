package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Currency;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class ValidCurrencyCodeValidator implements ConstraintValidator<ValidCurrencyCode, String> {

    private Boolean isOptional;

    @Override
    public void initialize(ValidCurrencyCode validCurrencyCode) {
        this.isOptional = validCurrencyCode.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean containsIsoCode = false;

        Set<Currency> currencies = Currency.getAvailableCurrencies();
        try {
            containsIsoCode = currencies.contains(Currency.getInstance(value));
        } catch (IllegalArgumentException e) {
        }
        return isOptional ? (containsIsoCode || (Objects.nonNull(value) && !value.isEmpty())) : containsIsoCode;
    }
}