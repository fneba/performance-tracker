package com.sports.performance_tracker.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sports.performance_tracker.Models.Player;
import com.sports.performance_tracker.Repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    private Long playerId;

    @BeforeEach
    void setUp() {
        Player player = new Player();
        player.setName("AJ Brown");
        player.setPosition("WR");
        player.setTeam("Eagles");
        playerId = playerRepository.save(player).getId();
    }

    @Test
    void testCreateSessionAndGetSessions() throws Exception {
        Map<String, String> sessionRequest = new HashMap<>();
        sessionRequest.put("playerId", playerId.toString());
        sessionRequest.put("date", LocalDate.now().toString());
        sessionRequest.put("opponent", "Cowboys");

        // POST → create session
        mockMvc.perform(post("/api/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opponent").value("Cowboys"));

        // GET → verify we get a JSON array (list)
        mockMvc.perform(get("/api/sessions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].opponent").value("Cowboys"));
    }

    @Test
    void testAddMetricsToSession() throws Exception {
        // Create a session first
        Map<String, String> sessionRequest = new HashMap<>();
        sessionRequest.put("playerId", playerId.toString());
        sessionRequest.put("date", LocalDate.now().toString());
        sessionRequest.put("opponent", "Cowboys");

        String sessionResponse = mockMvc.perform(post("/api/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, Object> createdSession = objectMapper.readValue(sessionResponse, Map.class);
        Long sessionId = Long.valueOf(createdSession.get("id").toString());

        // Define metric values to attach
        Map<String, Double> metrics = new HashMap<>();
        metrics.put("passing_yards", 310.0);
        metrics.put("passing_tds", 4.0);
        metrics.put("rushing_yards", 52.0);

        // POST metrics to /api/sessions/{id}/metrics
        mockMvc.perform(post("/api/sessions/" + sessionId + "/metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrics)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())                   // check that it's a list
                .andExpect(jsonPath("$[0].metric").exists())          // check the structure
                .andExpect(jsonPath("$[0].value").exists());          // check that a value is stored
    }
}