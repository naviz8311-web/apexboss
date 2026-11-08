# ApexBoss

**Gameplay systems & entity AI showcase mod for Minecraft 1.20.1 Forge**

---

## Overview

ApexBoss adds the **Void Warden** — a fully custom boss entity with dual combat phases, five distinct attacks, synchronized boss health bar, custom loot, and proper Forge entity registration. Every system is implemented from scratch using Forge's AI goal framework.

---

## Features

| Feature | Status |
|---|---|
| Custom boss entity (Void Warden) | ✅ |
| Two combat phases with transition trigger | ✅ |
| Phase 1: Melee sweep (AoE damage + knockback) | ✅ |
| Phase 1: Void projectile barrage (3 fan-spread fireballs) | ✅ |
| Phase 1: Ground slam (jump + shockwave particles) | ✅ |
| Phase 2: Enraged charge (dash attack) | ✅ |
| Phase 2: Void nova burst (8 radial projectiles) | ✅ |
| Target selection (NearestAttackableTarget + HurtBy) | ✅ |
| Pathfinding (Forge navigation system) | ✅ |
| Custom attributes (HP: 300, armor, damage, speed) | ✅ |
| Boss bar health sync (ServerBossEvent) | ✅ |
| Custom loot (void crystal, essence, rare void blade) | ✅ |
| Spawn mechanism (Warden Spawn Egg item) | ✅ |
| Phase 2 stat escalation | ✅ |
| Client renderer and boss texture registration | ✅ |
| NBT persistence (phase state survives server restart) | ✅ |

---

## Spawning the Boss

1. Obtain a **Void Warden Spawn Egg** from creative inventory (`/give @p apexboss:warden_spawn_egg`)
2. Right-click on the ground to spawn the Void Warden
3. The boss bar will appear when you approach within tracking range

---

## Boss Mechanics

### Phase 1 (100% → 50% HP)
| Attack | Description | Cooldown |
|---|---|---|
| Melee Sweep | Wide AoE hit (8 dmg) + knockback to all nearby entities | 4s |
| Void Barrage | 3 fan-spread large fireballs with line of sight requirement | 6s |
| Ground Slam | Leap + shockwave (12 dmg, scales with distance) + slowness | 10s |

### Phase 2 Transition (at 50% HP)
- Boss bar turns red: **"⚡ Void Warden — ENRAGED ⚡"**
- Movement speed increases from 0.28 → 0.38
- Attack damage increases from 10 → 16
- Global chat announcement to nearby players

### Phase 2 (50% → 0% HP)
| Attack | Description | Cooldown |
|---|---|---|
| Enraged Charge | Telegraphed dash at CHARGE_SPEED 2.8, heavy knockback (14 dmg) | 5s |
| Void Nova | 8 radial fireballs in all directions, area denial | 8s |

---

## Loot

| Item | Chance | Amount |
|---|---|---|
| Void Crystal | 100% | 3-5 |
| Essence of Void | 100% | 1 |
| Void Blade | 30% + 5% per looting | 1 |

---

## Installation

1. Install **Minecraft Forge 1.20.1-47.3.0**
2. Place `apexboss-1.0.0.jar` in your `mods/` folder
3. Launch the game

---

## Building

```bash
./gradlew build
# Output: build/libs/apexboss-1.0.0.jar
```

---

## Architecture

```
com.navdeep.apexboss/
├── ApexBoss.java                   # Mod entry point
├── entity/
│   ├── BossPhase.java              # PHASE_1 / PHASE_2 enum
│   ├── ModEntityTypes.java         # DeferredRegister entity type
│   ├── VoidWardenEntity.java       # Main boss entity (Monster subclass)
│   └── goal/
│       ├── MeleeSweepGoal.java     # AoE melee attack
│       ├── VoidProjectileGoal.java # Fan fireball attack
│       ├── GroundSlamGoal.java     # Leap + shockwave
│       ├── EnragedChargeGoal.java  # Phase 2 dash
│       └── VoidNovaBurstGoal.java  # Phase 2 radial burst
├── event/
│   └── BossSpawnEventHandler.java  # Entity attribute registration
├── client/
│   ├── ApexBossClientEvents.java   # Client renderer registration
│   └── VoidWardenRenderer.java     # Boss renderer
└── item/
    └── ModItems.java               # Void Crystal, Essence, Void Blade, Spawn Egg
```

**Key design decisions:**
- Each attack is a separate `Goal` class — fully modular, easy to extend.
- Phase transition is one-way and persisted via NBT, so a mid-fight server restart correctly restores Phase 2 state.
- `ServerBossEvent` is used for boss bar sync — this is the canonical Forge approach, not client packets.
- Attribute mutation on phase transition (setBaseValue) is the correct way to modify runtime stats without re-registering attributes.

---

## Known Limitations

- Uses a scaled humanoid model and vanilla zombie texture as a visual placeholder.
- LargeFireball visual is placeholder for "void" projectile — a custom projectile class would improve polish
- No arena/spawner structure — boss is purely item-spawned
