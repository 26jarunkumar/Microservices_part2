package com.arun.curencyconversion.feignproxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.arun.curencyconversion.domain.CurrencyConversion;

//@FeignClient(name="currency-exchange-service")
@FeignClient(name="zuul-proxy-server")
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeFeignProxy {

	//@GetMapping("/currency-exchange/from/{from}/to/{to}")
	@GetMapping("currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion getExchangeValue(@PathVariable("from") String from,@PathVariable("to") String to );
}
