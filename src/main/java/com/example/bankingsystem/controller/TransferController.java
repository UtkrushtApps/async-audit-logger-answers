package com.example.bankingsystem.controller;

import com.example.bankingsystem.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {
    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<?> transferMoney(@RequestParam String from,
                                           @RequestParam String to,
                                           @RequestParam double amount) {
        // In a real app, validate etc.
        String txnId = transferService.transfer(from, to, amount);
        return ResponseEntity.ok().body("Transfer submitted, transactionId: " + txnId);
    }
}
