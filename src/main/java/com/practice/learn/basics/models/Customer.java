/**
 * 
 */
package com.practice.learn.basics.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author mayur-raj
 *
 */
public class Customer {

	private String name;
	private long deposit = 0;
	private boolean accountStatus = false;
	@JsonIgnore
	private int accountNumber;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDeposit() {
		return deposit;
	}

	public void setDeposit(long deposit) {
		this.deposit = deposit;
	}

	public boolean isAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(boolean accountStatus) {
		this.accountStatus = accountStatus;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(name, other.name);
	}
	
	

}
