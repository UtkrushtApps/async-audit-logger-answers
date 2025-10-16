package com.example.bankingsystem.kafka;

import com.example.bankingsystem.config.KafkaConfig;
import com.example.bankingsystem.model.AuditLog;
import com.example.bankingsystem.service.AuditLogService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuditLogKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(AuditLogKafkaConsumer.class);

    private final AuditLogService auditLogService;

    public AuditLogKafkaConsumer(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @KafkaListener(topics = KafkaConfig.AUDIT_TOPIC, groupId = "audit-log-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, AuditLog> record) {
        try {
            AuditLog auditLog = record.value();
            if (auditLog == null) {
                logger.warn("Received null AuditLog message, skipping");
                return;
            }
            auditLogService.addAuditLog(auditLog);
        } catch (Exception e) {
            logger.error("Error processing audit log record", e);
            // swallow, proceed. Default error handling configuration will move the offset forward.
        }
    }
}
