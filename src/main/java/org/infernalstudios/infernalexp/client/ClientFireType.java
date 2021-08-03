/*
 * Copyright 2021 Infernal Studios
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

package org.infernalstudios.infernalexp.client;

import net.minecraft.client.renderer.model.RenderMaterial;

public class ClientFireType {

    private final RenderMaterial associatedSprite0;
    private final RenderMaterial associatedSprite1;

    public ClientFireType(RenderMaterial associatedSprite0, RenderMaterial associatedSprite1) {
        this.associatedSprite0 = associatedSprite0;
        this.associatedSprite1 = associatedSprite1;
    }

    public RenderMaterial getAssociatedSprite0() {
        return associatedSprite0;
    }

    public RenderMaterial getAssociatedSprite1() {
        return associatedSprite1;
    }

}
