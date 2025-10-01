package com.sports.performance_tracker.Controllers;

import com.sports.performance_tracker.Models.Metric;
import com.sports.performance_tracker.Repositories.MetricRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class MetricController {

    private final MetricRepository metricRepository;

    public MetricController(MetricRepository metricRepository){
        this.metricRepository = metricRepository;
    }

    @GetMapping
    public List<Metric> getAllMetrics(){
        return metricRepository.findAll();
    }

    @PostMapping
    public Metric createMetric(@RequestBody Metric metric){
        return metricRepository.save(metric);
    }
}
