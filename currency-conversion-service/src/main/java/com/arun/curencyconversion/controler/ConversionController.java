package com.arun.curencyconversion.controler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.arun.curencyconversion.domain.CurrencyConversion;
import com.arun.curencyconversion.feignproxy.CurrencyExchangeFeignProxy;

@RestController
public class ConversionController {
	
	@Autowired
	CurrencyExchangeFeignProxy feignProxy;

	@GetMapping("/currency-convert/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion covertCurreny(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		Map<String , String> urlMap = new HashMap<>();
		urlMap.put("from", from);
		urlMap.put("to", to);
		
		ResponseEntity< CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,urlMap);
		
		CurrencyConversion response = responseEntity.getBody(); 
		
		return new CurrencyConversion(response.getId(),
				from,
				to,
				response.getConversionMultiple(),
				quantity,
				quantity.multiply(response.getConversionMultiple()),
				response.getPort());
	}
	
	@GetMapping("/currency-convert-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion covertCurrenyfeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		
		CurrencyConversion response = feignProxy.getExchangeValue(from, to); 
		
		return new CurrencyConversion(response.getId(),
				from,
				to,
				response.getConversionMultiple(),
				quantity,
				quantity.multiply(response.getConversionMultiple()),
				response.getPort());
	}
	
}
