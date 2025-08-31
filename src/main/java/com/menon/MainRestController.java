package com.menon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("payment/v1")
public class MainRestController {

    private static final Logger log = LoggerFactory.getLogger(MainRestController.class);

    @Autowired
    CustomerService authService;
    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping("get/{orderid}")
    public ResponseEntity<?> getPayment(@PathVariable("orderid") String orderid) {
        Payment payment;

        if (paymentRepository.findByOrderId(orderid).isPresent()) {
            payment = paymentRepository.findByOrderId(orderid).get();
            log.info("Payment found: {}", payment);
            return ResponseEntity.ok(payment);
        } else {
            log.info("Payment not found for orderid: {}", orderid);
            return ResponseEntity.ok().body(null);
        }

    }

    @PostMapping("create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest,
                                           @RequestHeader("Authorization") String token) throws InterruptedException {

        if (authService.validateToken(token, paymentRequest.getCustomerPhone())) {
            log.info("Token is valid: {}", token);
            log.info("Received request to create payment: {}", paymentRequest);
            Thread.sleep(20000); // simulating delay in response from payment service
            Payment payment = savePaymentDetailsWithPendingStatus(paymentRequest);
            log.info("Payment created successfully: {}", payment);
            return ResponseEntity.ok(payment.getPaymentId());
        } else {
            log.info("Token is invalid: {}", token);
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    private Payment savePaymentDetailsWithPendingStatus(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setPaymentId(String.valueOf(new Random().nextInt()));
        payment.setPaymentId("PAYMENT-" + new Random().nextInt(1000000));
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setStatus("COMPLETED");
        payment.setPaymentMethod("CARD PAYMENT");
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
        return payment;
    }
}
