package com.sports.performance_tracker.Models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PlayerTest {

    @Test
    void testPlayerGettersAndSetters() {
        Player player = new Player();
        player.setId(1L);
        player.setName("Jalen Hurts");
        player.setPosition("QB");
        player.setTeam("Eagles");

        assertThat(player.getId()).isEqualTo(1L);
        assertThat(player.getName()).isEqualTo("Jalen Hurts");
        assertThat(player.getPosition()).isEqualTo("QB");
        assertThat(player.getTeam()).isEqualTo("Eagles");
    }
}