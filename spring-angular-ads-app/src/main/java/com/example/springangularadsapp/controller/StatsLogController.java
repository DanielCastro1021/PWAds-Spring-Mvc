package com.example.springangularadsapp.controller;

import com.example.springangularadsapp.aop.annotation.AdminAccess;
import com.example.springangularadsapp.models.StatsLog;
import com.example.springangularadsapp.repository.StatsLogRepository;
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
