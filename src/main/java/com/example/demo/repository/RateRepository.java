package com.example.demo.repository;

import com.example.demo.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RateRepository extends JpaRepository<Rate, String> {

    Rate getRateByCurrency(String currency);
}
