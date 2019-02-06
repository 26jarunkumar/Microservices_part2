package com.arun.curencyexchange.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.arun.curencyexchange.ExchangeValue;
import com.arun.curencyexchange.repository.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ExchangeValueRepository exchangeRepository;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue getExchangeValue(@PathVariable String from,@PathVariable String to ) {
		
		
		//ExchangeValue value = new ExchangeValue(1000L,from,to,BigDecimal.valueOf(65));
		ExchangeValue value = exchangeRepository.findByFromAndTo(from, to);
		value.setPort(Integer.parseInt((null==env.getProperty("port")? "0":env.getProperty("port"))));
		 return value;
	}
}
