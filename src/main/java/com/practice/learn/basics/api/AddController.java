/**
 * 
 */
package com.practice.learn.basics.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.learn.basics.models.Customer;

/**
 * @author ermay
 *
 */
@RestController
@RequestMapping(path = "add")
public class AddController {

	private static Map<Integer, Customer> custMap = new HashMap<>();
	private static int count = 100;

	@RequestMapping(method = RequestMethod.POST, path = "open-account")
	public String openAccount(@RequestBody Customer customer) {

		Optional<Integer> accountCheck = custMap.keySet().stream().filter(k -> customer.equals(custMap.get(k)))
				.findFirst();

		if (accountCheck.isPresent()) {
			return "Your account number is already present with account number: " + accountCheck.get();
		} else {
			count++;
			customer.setAccountNumber(count);
			custMap.put(count, customer);
			return "Your account number is " + count;
		}
	}

	@PostMapping(path = "deposit")
	public String addMoney(@RequestParam(name = "account-number", defaultValue = "0") Integer accountNumber,
			@RequestParam(name = "deposit-amount", defaultValue = "0") Long amount) {
		Customer customer = custMap.get(accountNumber);
		if (null != customer) {
			long balance = customer.getDeposit() + amount;
			customer.setDeposit(balance);
			custMap.put(accountNumber, customer);
			return "Amount deposited " + amount + " to your account: " + customer.getAccountNumber();
		} else {
			return "Records Not Found for account number: " + accountNumber;
		}

	}

	@RequestMapping(method = RequestMethod.GET, path = "balance-enquiry")
	public String enquiry(@RequestParam(name = "account-number", defaultValue = "0") Integer accountNumber) {
		Customer customer = custMap.get(accountNumber);
		if (null != customer) {
			return "Balance Amount for account number: " + accountNumber + " is : " + customer.getDeposit();
		} else {
			return "Records Not Found for account number: " + accountNumber;
		}

	}

}
