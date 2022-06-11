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

package org.infernalstudios.infernalexp.config.gui.widgets;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TextFieldOption {

    // TODO: Finish converting this to the new OptionInstance system
    //    private record TextFieldValueSet() implements OptionInstance.ValueSet<String> {
    //
    //        @Override
    //        public @NotNull Function<OptionInstance<String>, AbstractWidget> createButton(@NotNull OptionInstance.TooltipSupplier<String> tooltipSupplier, @NotNull Options options, int x, int y, int width) {
    //            return (optionInstance) -> new TextField(options, x, y, width, ((OptionInstanceAccessor)(Object) optionInstance).getToString().apply();
    //        }
    //
    //        @Override
    //        public @NotNull Optional<String> validateValue(@NotNull String value) {
    //            return Optional.empty();
    //        }
    //
    //        @Override
    //        public @NotNull Codec<String> codec() {
    //            return null;
    //        }
    //
    //    }

}
