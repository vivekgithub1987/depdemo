package com.vp.depdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {
	@GetMapping("/info")
	public String info() {
	    return "Running Microservice On AWS Aws Ec2 Successfully";
	}
}
