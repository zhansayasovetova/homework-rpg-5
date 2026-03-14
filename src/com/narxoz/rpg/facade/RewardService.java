package com.narxoz.rpg.facade;

public class RewardService {
    public String determineReward(AdventureResult battleResult) {
        if (battleResult == null || battleResult.getWinner() == null) {
            return "No reward";
        }

        if ("Draw".equals(battleResult.getWinner())) {
            return "Small treasure chest";
        }

        if ("Arthas".equals(battleResult.getWinner())) {
            return "Epic dungeon loot";
        }

        return "No reward";
    }
}