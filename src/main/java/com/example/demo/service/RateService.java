package com.example.demo.service;

import com.example.demo.repository.RateRepository;
import com.example.demo.to.RateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class RateService {
    private final static String EUR_CODE = "EUR";

    private RateRepository repository;

    @Autowired
    public RateService(RateRepository repository) {
        this.repository = repository;
    }

    public BigDecimal getExchangeResult(RateDTO dto) {
        BigDecimal index = dto.getSrc().equalsIgnoreCase(EUR_CODE) ?
                repository.getRateByCurrency(dto.getDest()).getSpot() :
                revertRate(repository.getRateByCurrency(dto.getSrc()).getSpot());
        return index.multiply(dto.getAmount());
    }

    private static BigDecimal revertRate(BigDecimal var) {
        return new BigDecimal(1).divide(var, 1);
    }
}