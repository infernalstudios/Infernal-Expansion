/*
 * Copyright 2022 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.capabilities;

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
