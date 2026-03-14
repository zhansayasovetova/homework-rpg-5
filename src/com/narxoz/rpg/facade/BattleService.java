package com.narxoz.rpg.facade;

import com.narxoz.rpg.decorator.AttackAction;
import com.narxoz.rpg.enemy.BossEnemy;
import com.narxoz.rpg.hero.HeroProfile;

import java.util.Random;

public class BattleService {
    private Random random = new Random(1L);

    public BattleService setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public AdventureResult battle(HeroProfile hero, BossEnemy boss, AttackAction action) {
        AdventureResult result = new AdventureResult();

        if (hero == null || boss == null || action == null) {
            result.setWinner("Invalid");
            result.setRounds(0);
            result.setReward("No reward");
            result.addLine("Battle failed: invalid inputs.");
            return result;
        }

        if (!hero.isAlive()) {
            result.setWinner(boss.getName());
            result.setRounds(0);
            result.setReward("No reward");
            result.addLine("Battle cannot start: hero is already defeated.");
            return result;
        }

        if (!boss.isAlive()) {
            result.setWinner(hero.getName());
            result.setRounds(0);
            result.setReward("No reward");
            result.addLine("Battle cannot start: boss is already defeated.");
            return result;
        }

        int rounds = 0;
        int maxRounds = 20;

        result.addLine("Battle started: " + hero.getName() + " vs " + boss.getName());
        result.addLine("Hero attack: " + action.getActionName());
        result.addLine("Effects: " + action.getEffectSummary());

        while (hero.isAlive() && boss.isAlive() && rounds < maxRounds) {
            rounds++;
            result.addLine("---- Round " + rounds + " ----");

            int heroDamage = action.getDamage();
            boss.takeDamage(heroDamage);
            result.addLine(hero.getName() + " deals " + heroDamage + " damage to " + boss.getName()
                    + ". Boss HP: " + boss.getHealth());

            if (!boss.isAlive()) {
                break;
            }

            int bossDamage = boss.getAttackPower();
            hero.takeDamage(bossDamage);
            result.addLine(boss.getName() + " deals " + bossDamage + " damage to " + hero.getName()
                    + ". Hero HP: " + hero.getHealth());

            if (random.nextInt(1) == 0) {
                // placeholder branch to keep random in use
            }
        }

        result.setRounds(rounds);
        result.setReward("No reward yet");

        if (hero.isAlive() && !boss.isAlive()) {
            result.setWinner(hero.getName());
            result.addLine("Winner: " + hero.getName());
        } else if (boss.isAlive() && !hero.isAlive()) {
            result.setWinner(boss.getName());
            result.addLine("Winner: " + boss.getName());
        } else {
            result.setWinner("Draw");
            result.addLine("Result: Draw");
        }

        return result;
    }
}
