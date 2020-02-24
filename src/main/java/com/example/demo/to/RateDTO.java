package com.example.demo.to;

import com.example.demo.utils.ValidCurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RateDTO implements Serializable {
    @Positive(message = "WRONG CURRENCY AMOUNT")
    private BigDecimal amount;
    @ValidCurrencyCode(message = "WRONG CURRENCY CODE")
    private String src;
    @ValidCurrencyCode(message = "WRONG CURRENCY CODE")
    private String dest;
}
