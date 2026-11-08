package com.navdeep.apexboss;

import com.navdeep.apexboss.entity.ModEntityTypes;
import com.navdeep.apexboss.event.BossSpawnEventHandler;
import com.navdeep.apexboss.item.ModItems;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ApexBoss — Custom boss entity mod for Minecraft 1.20.1 Forge.
 *
 * Architecture:
 *  - VoidWardenEntity: custom boss entity extending Monster
 *  - Phase1 / Phase2 goals: Forge AI goal system
 *  - BossBarManager: server-side ServerBossEvent health sync
 *  - ModItems: void crystal (spawn item), void blade (loot), essence of void
 *  - ModEntityTypes: entity registration via DeferredRegister
 */
@Mod(ApexBoss.MOD_ID)
public class ApexBoss {

    public static final String MOD_ID = "apexboss";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public ApexBoss() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        // Entity attributes must be registered on the MOD event bus
        modEventBus.addListener(BossSpawnEventHandler::registerAttributes);

        LOGGER.info("ApexBoss initialized — Void Warden awaits.");
    }
}
