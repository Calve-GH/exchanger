package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Rate")
public class Rate {
    @Id
    @Column(name = "currency", nullable = false)
    private String currency;
    @Column(name = "spot", nullable = false)
    private BigDecimal spot;
}
