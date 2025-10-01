package com.sports.performance_tracker.Models;

import jakarta.persistence.*;

@Entity
@Table(name="metrics")
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String key;

    private String description;

    public Metric() {}

    // Getters
    public Long getId() { return id; }
    public String getKey() { return key; }
    public String getDescription() { return description; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setKey(String key) { this.key = key; }
    public void setDescription(String description) { this.description = description; }
}
