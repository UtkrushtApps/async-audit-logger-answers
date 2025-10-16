package com.example.bankingsystem.controller;

import com.example.bankingsystem.model.AuditLog;
import com.example.bankingsystem.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-log")
public class AuditLogController {
    private final AuditLogService auditLogService;

    @Autowired
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public List<AuditLog> getAuditLogs() {
        return auditLogService.getAllLogs();
    }
}
