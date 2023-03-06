#  Copyright 2023 Infernal Studios
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

#
# This script is a bit of a cobbled together mess.
# It converts JSON lang files into the Java code that is then used to generate the JSON lang files.
# A bit ironic, isn't it?
#

import json

with open("base.json", "r", encoding="utf-8") as f:
    base = json.load(f)

with open("zh_tw.json", "r", encoding="utf-8") as f:
    lang = json.load(f)

f = open("output.txt", "w", encoding="utf-8")

for key in lang:
    if lang[key] == base[key]:
        lang[key] = "null"
    else:
        lang[key] = f"\"{lang[key]}\""

for key in lang:
    if key.endswith(".desc") or key.endswith(".hurt") or key.endswith(".death"):
        continue

    if "potion" in key or "tipped" in key:
        continue

    if key.startswith("itemGroup."):
        f.write(f"add(InfernalExpansion.TAB, {lang[key]});\n")
        continue

    name = key.split(".")[2]

    if key.startswith("block."):
        f.write(f"add(IEBlocks.{name.upper()}.get(), {lang[key]});\n")

    elif "music_disc" in key:
        f.write(f"addMusicDisc(IEItems.{name.upper()}.get(), {lang[key]}, {lang[key + '.desc']});\n")

    elif key.startswith("item."):
        f.write(f"add(IEItems.{name.upper()}.get(), {lang[key]});\n")

    elif key.startswith("entity."):
        f.write(f"add(IEEntityTypes.{name.upper()}.get(), {lang[key]});\n")

    elif key.startswith("subtitles."):
        if key.endswith(".ambient"):
            key = key.replace(".ambient", "")
            f.write(f"addEntitySubtitles(IEEntityTypes.{name.upper()}.get(), {lang.get(key + '.ambient', 'null')}, {lang.get(key + '.hurt', 'null')}, {lang.get(key + '.death', 'null')});\n")
        else:
            f.write(f"add(\"subtitles.entity.\" + ForgeRegistries.ENTITIES.getKey(IEEntityTypes.{name.upper()}.get()).getPath() + \".{key.split('.')[-1]}\", {lang[key]});\n")

    elif key.startswith("biome."):
        f.write(f"add(IEBiomes.{name.upper()}, {lang[key]});\n")

    elif key.startswith("effect."):
        f.write(f"addEffect(IEEffects.{name.upper()}.get(), {lang[key]});\n")

    elif key.startswith("generator."):
        f.write(f"add(\"generator.\" + InfernalExpansion.MOD_ID + \".{name}\", {lang[key]});\n")

    elif "config" in key:
        f.write(f"addConfig(\"{key.split('.', 2)[-1]}\", {lang[key]});\n")

    elif "commands" in key:
        f.write(f"add(InfernalExpansion.MOD_ID + \".{key.split('.', 1)[1]}\", {lang[key]});\n")

f.close()
