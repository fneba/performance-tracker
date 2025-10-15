package com.sports.performance_tracker.Repositories;

import com.sports.performance_tracker.Models.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void testFindByName() {
        Player player = new Player();
        player.setName("AJ Brown");
        player.setPosition("WR");
        player.setTeam("Eagles");
        playerRepository.save(player);

        Player found = playerRepository.findByName("AJ Brown");
        assertThat(found).isNotNull();
        assertThat(found.getTeam()).isEqualTo("Eagles");
        assertThat(found.getPosition()).isEqualTo("WR");
    }
}