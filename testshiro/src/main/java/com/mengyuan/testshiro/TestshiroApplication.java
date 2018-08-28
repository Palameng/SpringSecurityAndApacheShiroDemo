package com.mengyuan.testshiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@MapperScan(basePackages = {"com.mengyuan.testshiro.mapper"})
public class TestshiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestshiroApplication.class, args);
	}
}
