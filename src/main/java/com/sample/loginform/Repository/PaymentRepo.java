package com.sample.loginform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.loginform.Entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment,Long>{

}
