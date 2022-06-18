package com.example.mvcpwads.audit.controller;

import com.example.mvcpwads.audit.StatsLog;
import com.example.mvcpwads.audit.StatsLogRepository;
import com.example.mvcpwads.security.authorization.annotation.AdminAccess;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stats-logs")
public class StatsLogController {
    private final StatsLogRepository repository;

    public StatsLogController(StatsLogRepository repository) {
        this.repository = repository;
    }

    @AdminAccess
    @GetMapping("/")
    public List<StatsLog> allStats() {
        return repository.findAll();
    }
}
