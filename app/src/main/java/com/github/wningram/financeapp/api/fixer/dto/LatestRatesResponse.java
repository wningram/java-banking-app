package com.github.wningram.financeapp.api.fixer.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Getter
public class LatestRatesResponse {
	
	private String base;
	
	private String date;
	
	private Rates rates;

}
