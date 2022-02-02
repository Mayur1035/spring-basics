/**
 * 
 */
package com.practice.learn.basics.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mayur-raj
 *
 */
@RestController
@RequestMapping(path = "hello")
public class AppController {

	private static int count = 0;

	@GetMapping(path = "visitor/count")
	public String getCount() {
		return "API Vistiors Count is: " + count;
	}

	//@PostMapping(path = "visitor")
	@RequestMapping(method = RequestMethod.POST , path = "visitor" )
	public String sayHello(@RequestBody String name) {
		count++;
		return "Hello " + name;
	}

}
