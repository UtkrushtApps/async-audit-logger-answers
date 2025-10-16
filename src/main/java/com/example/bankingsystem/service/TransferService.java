package com.example.bankingsystem.service;

import com.example.bankingsystem.model.AuditLog;
import com.example.bankingsystem.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {
    private final KafkaTemplate<String, AuditLog> kafkaTemplate;

    @Autowired
    public TransferService(KafkaTemplate<String, AuditLog> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public String transfer(String from, String to, double amount) {
        // Business logic for transfer (omitted, assume always succeeds)
        // ...
        String txnId = UUID.randomUUID().toString();
        AuditLog log = new AuditLog(txnId, from, to, amount, Instant.now());
        CompletableFuture<Void> future =
            kafkaTemplate.send(KafkaConfig.AUDIT_TOPIC, log.getTransactionId(), log)
            .completable()
            .thenAccept(result -> {}
            );
        // Attaching async error handler, but not blocking main thread
        future.exceptionally(ex -> {
            // You might log or handle the error, but don't throw (to avoid blocking)
            ex.printStackTrace();
            return null;
        });
        return txnId; // Return transaction id immediately (audit is async)
    }
}
