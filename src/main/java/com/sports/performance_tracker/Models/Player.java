package com.sports.performance_tracker.Models;

import jakarta.persistence.*;

@Entity
@Table(name="players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;
    private String team;

    public Player() {}

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public String getTeam() { return team; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPosition(String position) { this.position = position; }
    public void setTeam(String team) { this.team = team; }
}
