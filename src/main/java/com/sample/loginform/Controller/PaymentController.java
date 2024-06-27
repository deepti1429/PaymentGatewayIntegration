package com.sample.loginform.Controller;


import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.WriterException;
import com.sample.loginform.Entity.Payment;
import com.sample.loginform.Service.PaymentService;
import com.sample.loginform.Service.QRcodeService;



@Controller
public class PaymentController {
	@Autowired
    private PaymentService paymentService;
    @Autowired
    private QRcodeService qrCodeService;

    private static final String UPI_ID = "86393002@axl"; // Replace with your UPI ID

    @GetMapping("/pay")
    public String pay(Model model) {
        return "pay";
    }

    @PostMapping("/generateQRCode")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> generateQRCode(@RequestParam Double amount) throws WriterException, IOException {
        Payment payment = paymentService.createPayment(amount);
        String upiString = "upi://pay?pa=" + UPI_ID + "&am=" + amount + "&tn=Payment"; // UPI string with your UPI ID
        byte[] qrCode = qrCodeService.generateQRCodeImage(upiString, 250, 250);

        // Encode QR code as base64 string
        String base64QRCode = Base64.getEncoder().encodeToString(qrCode);

        Map<String, Object> response = new HashMap<>();
        response.put("qrCode", base64QRCode);
        response.put("paymentId", payment.getId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/proceedToPay")
    public String proceedToPay(@RequestParam Long paymentId) {
        paymentService.updatePaymentStatus(paymentId, "COMPLETED");
        return "redirect:/paySuccess";
    }

    @GetMapping("/paySuccess")
    public String paySuccess() {
        return "paySuccess";
    }
}