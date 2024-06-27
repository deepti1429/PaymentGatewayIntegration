package com.sample.loginform.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.loginform.Entity.Payment;
import com.sample.loginform.Repository.PaymentRepo;

@Service
public class PaymentService {
	 @Autowired
	    private PaymentRepo paymentRepository;

	 public Payment createPayment(Double amount) {
	        Payment payment = new Payment();
	        payment.setAmount(amount);
	        payment.setStatus("PENDING");
	        return paymentRepository.save(payment);
	    }

	    public void updatePaymentStatus(Long paymentId, String status) {
	        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment not found"));
	        payment.setStatus(status);
	        paymentRepository.save(payment);
	    }
	}