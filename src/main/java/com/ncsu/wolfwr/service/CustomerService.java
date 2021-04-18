package com.ncsu.wolfwr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Customer;
import com.ncsu.wolfwr.entity.MembershipTier;
import com.ncsu.wolfwr.repository.CustomerRepository;
import com.ncsu.wolfwr.repository.MembershipTierRepository;

import utility.BasicUtils;

@Service
public class CustomerService {
	CustomerRepository customerRepo;
	
	
	MembershipTierRepository membershipTierRepo;
	
	@Autowired
	CustomerService(CustomerRepository customerRepo, MembershipTierRepository membershipTierRepo) {
		this.customerRepo = customerRepo;
		this.membershipTierRepo = membershipTierRepo;
	}
	
	
	public Customer getCustomerById(int customerId) {
		return customerRepo.findById(customerId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createCustomer(Customer customer) {
		if (customer.getCustomerId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if(BasicUtils.isEmpty(customer.getRewardPoints())) {
			customer.setRewardPoints((float) 0);
		}
		
		customer = this.customerRepo.save(customer);
		
		return customer.getCustomerId();
	}
	
	public void updateCustomer(int id, Customer customer) {
		if (customer.getCustomerId() != null && customer.getCustomerId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.customerRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.customerRepo.save(customer);
	}
	
	public void deleteCustomer(int id) {
		this.customerRepo.deleteById(id);
	}
}
