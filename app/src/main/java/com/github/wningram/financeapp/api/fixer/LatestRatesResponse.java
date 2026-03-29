package com.github.wningram.financeapp.api.fixer;

import com.github.wningram.financeapp.api.fixer.dto.Rate;

import lombok.Getter;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Getter
public class LatestRatesResponse {
	
	private String base;
	
	private String date;
	
	private Rate[] rates;

}
