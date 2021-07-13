package com.nekomaster1000.infernalexp.capabilities;

public interface IWhipUpdate {

    int getTicksSinceAttack();

    void setTicksSinceAttack(int ticksSinceAttack);

    boolean getAttacking();

    void setAttacking(boolean attacking);

    boolean getCharging();

    void setCharging(boolean charging);

}
