package com.incofer.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class DemoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(DemoApplication.class, args);
	}
}
