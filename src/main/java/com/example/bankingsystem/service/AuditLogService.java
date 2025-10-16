package com.example.bankingsystem.service;

import com.example.bankingsystem.model.AuditLog;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AuditLogService {
    private final CopyOnWriteArrayList<AuditLog> auditLogs = new CopyOnWriteArrayList<>();
    
    public void addAuditLog(AuditLog auditLog) {
        auditLogs.add(auditLog);
    }

    public List<AuditLog> getAllLogs() {
        // Return a snapshot copy for thread safety
        return Collections.unmodifiableList(auditLogs);
    }

    public void clear() {
        auditLogs.clear();
    }
}
