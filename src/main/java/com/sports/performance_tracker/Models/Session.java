package com.sports.performance_tracker.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(nullable = false)
    private LocalDate date;

    private String opponent;

    public Session() {}

    // Getters
    public Long getId() { return id; }
    public Player getPlayer() { return player; }
    public LocalDate getDate() { return date; }
    public String getOpponent() { return opponent; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPlayer(Player player) { this.player = player; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setOpponent(String opponent) { this.opponent = opponent; }
}
