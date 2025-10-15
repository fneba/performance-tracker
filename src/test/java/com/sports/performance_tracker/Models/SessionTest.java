package com.sports.performance_tracker.Models;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class SessionTest {

    @Test
    void testSessionFields() {
        Player player = new Player();
        player.setId(5L);
        player.setName("AJ Brown");

        Session session = new Session();
        session.setId(20L);
        session.setPlayer(player);
        session.setDate(LocalDate.of(2025, 9, 27));
        session.setOpponent("Cowboys");

        assertThat(session.getId()).isEqualTo(20L);
        assertThat(session.getPlayer().getName()).isEqualTo("AJ Brown");
        assertThat(session.getDate()).isEqualTo(LocalDate.of(2025, 9, 27));
        assertThat(session.getOpponent()).isEqualTo("Cowboys");
    }
}