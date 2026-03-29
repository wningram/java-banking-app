package com.github.wningram.financeapp.api.fixer.dto;

import lombok.Data;
import lombok.AllArgsConstructor;


@Data
@AllArgsConstructor
public class Rate {
	
	public String currencyCode;
	
	public double rate;

}