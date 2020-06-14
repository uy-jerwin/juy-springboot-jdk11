package juy.web.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppInfoController {
	
	@GetMapping(path = { "/app-info" })
	public String uuid() {
		return UUID.randomUUID().toString();
	}

}
