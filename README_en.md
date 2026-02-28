# TamableFairy

[![Image](https://cdn.modrinth.com/data/4EAV6XSC/images/72d1aa45d3f7f670a43cb9db292c4119645e49b8.png)]()

[简体中文](README.md)  
[English](README_en.md)

[![NeoForge](https://img.shields.io/badge/NeoForge-21.1-blue.svg)](https://neoforged.net/)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-brightgreen.svg)](https://minecraft.net)
[![Touhou Little Maid](https://img.shields.io/badge/Touhou%20Little%20Maid-Dependency-orange)](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid)

This is an addon mod for [Touhou Little Maid](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid) that allows you to tame the Maid Fairies from the mod.

---

## Features

- **Tame MaidFairies** – Right‑click a Maid Fairy with a **cake** to tame her.
- **Rewrite Entity AI** – Rewrites part of the maid fairy’s AI. Tamed fairies will follow their owner. If they get too far away (≥16 blocks), they teleport directly to the owner. Tamed fairies will also attack any creature that hurts the owner or that the owner attacks.
- **No Friendly Fire** – Maid Fairies owned by the same player cannot damage each other.
- **Avoid Unwanted Targeting** – Tamed Maid Fairies are no longer targeted by Iron Golems or other mobs that would normally be hostile to them.
- **Heal with Sugar** – Use **sugar** on your own Maid Fairy to restore 2 health points.

---

## Dependencies

- **[Touhou Little Maid](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid)** – Required.

---

## Technical Details

- **Data Storage**: Uses NeoForge’s `AttachmentType` to store `TameData` (`BOOL tamed` and `UUID owner`). Data is serialized and synced to clients.
- **AI Modification**: Since `EntityFairy` extends `Monster` and does not have the default AI of vanilla tamable animals (`TamableAnimal`), upon taming, the fairy’s original target selector is replaced with:
    - `CustomOwnerHurtByTargetGoal` – Attacks mobs that hurt the owner.
    - `CustomOwnerHurtTargetGoal` – Attacks mobs that the owner hurts (with owner check to avoid attacking same‑owner fairies).
    - And a custom goal `CustomFollowOwnerGoal` is added – follows the owner and teleports when too far away.
- **Event Handling**:
    - `AvoidBeingTargeted` – Prevents mobs like Iron Golems from targeting tamed fairies.
    - `IgnoreDamage` – Cancels damage if both the source and the target are maid fairies owned by the same player.
    - `InteractEvents` – Handles interactions like healing.
    - `TameHandler` – Handles taming events.
    - `TameStateUpdate` – Reapplies the tamed AI when a tamed Maid Fairy joins the level.

---

## Future Plans
- **Configuration**: Currently only a placeholder config class (`Config.java`) exists with no actual functionality. Configuration features are planned for later development.
- **Entity Behavior**: Plans to add a right‑click “sit” function similar to vanilla tamable mobs.
- **Other Versions**: This mod is planned to be ported to other Minecraft versions compatible with [Touhou Little Maid](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid).

---

## License

This project is licensed under the [MIT License](LICENSE). You are free to use, modify, and distribute it as long as you retain the original copyright notice.

---

## Credits
- **Dependency Mod**: [Touhou Little Maid](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid) (GitHub repository: [Touhou Little Maid](https://github.com/TartaricAcid/TouhouLittleMaid/) by TartaricAcid)

---

## Contributing

Issues and pull requests are welcome!
If you have any good ideas, feel free to submit an issue as well.

---

## Support

If you have any questions or bug reports, please open an issue in the [GitHub repository](#) (if available).  
