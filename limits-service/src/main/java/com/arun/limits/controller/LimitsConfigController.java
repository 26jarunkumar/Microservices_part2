package com.arun.limits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arun.limits.configuration.Configuration;
import com.arun.limits.configuration.LimitConfiguration;

@RestController
public class LimitsConfigController {

	@Autowired
	private Configuration config;
	
	@GetMapping("/limits")
	public LimitConfiguration getDefaultLimits() {
		return new LimitConfiguration(config.getMaximum(),config.getMinimum());		
	}
}
