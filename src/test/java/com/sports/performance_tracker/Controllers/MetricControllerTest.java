package com.sports.performance_tracker.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sports.performance_tracker.Models.Metric;
import com.sports.performance_tracker.Repositories.MetricRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MetricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MetricRepository metricRepository;

    @Test
    void testCreateAndGetMetrics() throws Exception {
        Metric metric = new Metric();
        metric.setKey("qb_rating");
        metric.setDescription("Quarterback rating");

        // POST → add a new metric
        mockMvc.perform(post("/api/metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metric)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value("qb_rating"));

        // GET → verify it returns a JSON array and contains the metric
        mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].key").value("qb_rating"));
    }
}