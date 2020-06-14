package io.bookingapi;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BookingApiApplication {
	
	private static Logger logger = LoggerFactory.getLogger(BookingApiApplication.class);

	public static void main(String[] args) {
		
		logger.info("Staring Booking API Service");
		SpringApplication.run(BookingApiApplication.class, args);
	}
	
	
	@Bean
	public Docket swaggerConfiguration() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				//.paths(PathSelectors.ant("/reservation"))
				.apis(RequestHandlerSelectors.basePackage("io.bookingapi"))
				.build()
				.apiInfo(getApiDetails());
	}
	
	
	private ApiInfo getApiDetails() {
		
		
		return new ApiInfo(
				"Hotel Booking API",
				"Developer documentation for Hotel Booking API.",
				"1.0",
				"Enterprise Use",
				new springfox.documentation.service.Contact("Hotel Booking Service", "https://hotelbooking.io", "contact@hotelbooking.io"),
				"API 1.0 License",
				"https://hotelbooking.io",
				Collections.emptyList()
				);
		
		
	}

}
