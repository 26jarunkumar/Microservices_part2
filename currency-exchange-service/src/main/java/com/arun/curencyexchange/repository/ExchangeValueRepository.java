package com.arun.curencyexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arun.curencyexchange.ExchangeValue;

@Repository
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue,Long > {

	ExchangeValue findByFromAndTo(String from,String to );
}
