package io.bookingapi.dataload.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
public class HealthCheckController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String healthChcek() {
		return "OK";
	}

}
