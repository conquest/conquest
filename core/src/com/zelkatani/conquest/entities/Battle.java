package com.zelkatani.conquest.entities;

import com.zelkatani.conquest.Player;

// TODO battle display, battles happen over time rather than end instantly
public class Battle {
    private Tile defender;
    private Tile attacker;
    private Troop troop;

    public Battle(Tile defender, Tile attacker, Troop troop) {
        this.defender = defender;
        this.defender.setAttacked(true);
        this.attacker = attacker;
        this.troop = troop;

        battle();
        if (this.defender.getTroops() < 0) {
            if (this.attacker.getOwner() instanceof Player) {
                ((Player) this.attacker.getOwner()).add(this.defender);
            }
            this.defender.setOwner((Player) this.attacker.getOwner());
            this.defender.setTroops(this.troop.getTroops());
        }
    }

    private void battle() {
        int pre = defender.getTroops();
        defender.adjustTroops(-troop.getTroops());
        troop.adjustTroops(-pre);
    }
}
