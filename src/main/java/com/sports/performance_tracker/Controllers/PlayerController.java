package com.sports.performance_tracker.Controllers;

import com.sports.performance_tracker.Models.Player;
import com.sports.performance_tracker.Repositories.PlayerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    @PostMapping
    public Player createPlayer(@RequestBody Player player){
        return playerRepository.save(player);
    }

}
