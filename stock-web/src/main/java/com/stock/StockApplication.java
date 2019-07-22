package com.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.stock.repository")
@EnableScheduling
public class StockApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}
}
