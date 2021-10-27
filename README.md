<h1 align="center">
	<img src="./src/main/resources/Infernal_Expansion_Logo.png" width="75%" alt="Infernal Expansion">
</h1>

<div align="center">
  <a href="https://infernalstudios.org/discord">
    <img alt="Discord" src="https://img.shields.io/discord/681976442220839008?color=yellow&label=Discord&style=for-the-badge">
  </a>

  <a href="https://infernalstudios.org/infernalexpansion/curseforge">
    <img alt="CurseForge Downloads" src="https://img.shields.io/badge/dynamic/json?color=orange&style=for-the-badge&label=CurseForge&query=%24.downloads.total&suffix=%20Downloads&url=https%3A%2F%2Fapi.cfwidget.com%2F395078">
  </a>
  
  <a href="https://infernalstudios.org/infernalexpansion/modrinth">
    <img alt="Modrinth Downloads" src="https://img.shields.io/badge/dynamic/json?color=red&style=for-the-badge&label=Modrinth&query=%24.downloads&suffix=%20Downloads&url=https%3A%2F%2Fapi.modrinth.com%2Fapi%2Fv1%2Fmod%2FZrpxHZN4">
  </a>
</div>
<br>
<br>

[Infernal Expansion](https://www.curseforge.com/minecraft/mc-mods/infernal-expansion) is a Nether expansion mod!

If you're contributing, please read [the contribution guide](./CONTRIBUTE.md).

### Maven Information
Infernal Expansion is published on [maven.infernalstudios.org](https://maven.infernalstudios.org/)!
If you want to use Infernal Expansion as a dependency for your mod, you can use the following in your `build.gradle`:
```gradle
repositories {
    maven { url = 'https://maven.infernalstudios.org/releases' }
}

dependencies {
    implementation fg.deobf('org.infernalstudios:infernalexp:INFERNALEXPANSION_VERSION-MINECRAFT_VERSION')
}
```

