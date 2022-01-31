/**
 * 
 */
package com.practice.learn.basics.api;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mayur-raj
 *
 */
@RestController
@RequestMapping(path = "hello")
public class AppController {

	@GetMapping(path =  "{name}")
	public String sayHello(@PathParam(value = "name") String name) {
		return "Hello "+name;
	}

}
