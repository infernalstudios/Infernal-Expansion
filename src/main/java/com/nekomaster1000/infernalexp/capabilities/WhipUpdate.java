package com.nekomaster1000.infernalexp.capabilities;

public class WhipUpdate implements IWhipUpdate {

    private int ticksSinceAttack;
    private boolean attacking;
    private boolean charging;

    @Override
    public int getTicksSinceAttack() {
        return this.ticksSinceAttack;
    }

    @Override
    public void setTicksSinceAttack(int ticksSinceAttack) {
        this.ticksSinceAttack = ticksSinceAttack;
    }

    @Override
    public boolean getAttacking() {
        return this.attacking;
    }

    @Override
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    @Override
    public boolean getCharging() {
        return this.charging;
    }

    @Override
    public void setCharging(boolean charging) {
        this.charging = charging;
    }
}
