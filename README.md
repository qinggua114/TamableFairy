# TamableFairy

[![NeoForge](https://img.shields.io/badge/NeoForge-21.1-blue.svg)](https://neoforged.net/)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-brightgreen.svg)](https://minecraft.net)
[![Touhou Little Maid](https://img.shields.io/badge/Touhou%20Little%20Maid-依赖-orange)](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid)

这是一个 [Touhou Little Maid](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid) 的扩展模组，允许你驯服模组中的女仆妖精。

---

## 特性

- **驯服妖精** – 手持**蛋糕**右键点击妖精即可驯服。
- **生物AI** – 重写了女仆妖精的部分AI，驯服的妖精会跟随主人。如果距离过远（≥16 格），它们会直接传送到主人身边。 且驯服的妖精会攻击任何伤害主人或主人正在攻击的生物。
- **无友军伤害** – 同一玩家拥有的妖精之间不会互相伤害。
- **避免被误伤** – 驯服的女仆妖精不会再被铁傀儡等通常会对它们产生敌意的生物锁定为目标。
- **吃糖回血** – 对自己的女仆妖精使用**糖**，可为其恢复 2点生命值。

---

## 依赖

- **[Touhou Little Maid](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid)** – 必需。

---

## 技术细节

- **数据存储**：使用 NeoForge 的 `AttachmentType` 存储 `TameData`（ `BOOL tamed` 和 `UUID owner`）。数据会被序列化并在客户端同步。
- **AI 修改**：由于EntityFairy继承自Monster,没有原版的可驯服生物（TamableAnimal）默认AI，驯服时，会将将女仆妖精原有的目标选择器（targetSelecter）替换为：
    - `CustomOwnerHurtByTargetGoal` – 攻击伤害主人的生物。
    - `CustomOwnerHurtTargetGoal` – 攻击主人正在攻击的生物（包含主人检查，避免攻击同主的妖精）。
    - 并添加一个自定义行为 `CustomFollowOwnerGoal` – 跟随主人，距离过远时传送。
- **事件处理**：
    - `AvoidBeingTargeted` – 防止怪物和铁傀儡将驯服妖精设为攻击目标。
    - `IgnoreDamage` – 如果伤害来源和目标都是同一主人的女仆妖精，则取消伤害，防止撞到自己人的弹幕趋势。
    - `InteractEvents` – 治疗等互动事件。
    - `TameHandler` - 处理驯服事件
    - `TameStateUpdate` – 当驯服的女仆妖精被加入level时，重新应用驯服后的 AI。

---

## 开发计划
- **配置文件**：本模组目前仅有一个示例配置类 (`Config.java`)，无实际作用。计划在以后开发配置功能。
- **生物行为**：计划添加类似原版可驯服生物的右键“坐下”功能
- **其他版本**：本模组计划移植至与 [Touhou Little Maid](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid) 兼容的其他Minecraft版本

---

## 许可证

本项目采用 [MIT 许可证](LICENSE)。您可以自由使用、修改和分发，但需保留原始版权声明。

---

## 鸣谢
- **依赖模组**：[Touhou Little Maid](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid) （Github仓库：[Touhou Little Maid](https://github.com/TartaricAcid/TouhouLittleMaid/) 作者 TartaricAcid）

---

## 贡献

欢迎提交 Issue 或 Pull Request！

---

## 支持

如有问题或 Bug 报告，请在 [GitHub 仓库](#)（如果可用）中提交 Issue。
