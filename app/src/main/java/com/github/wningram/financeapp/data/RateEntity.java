package com.github.wningram.financeapp.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
@Data
@NoArgsConstructor
public class RateEntity {
	
	public RateEntity(String currencyCode, double rate) {
		this.currencyCode = currencyCode;
		this.rate = rate;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String currencyCode;
	
	private double rate;

}