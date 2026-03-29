package com.github.wningram.financeapp.data;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RateRepository extends JpaRepository<RateEntity, Long> {
	public RateEntity findByCurrencyCode(String currencyCode);
}
