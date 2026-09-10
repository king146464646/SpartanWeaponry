package org.xiyu.spartanweaponryunofficial.init;

import java.util.function.Supplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.xiyu.spartanweaponryunofficial.ModSpartanWeaponry;
import org.xiyu.spartanweaponryunofficial.advancement.criterion.BoomerangBounceKillTrigger;
import org.xiyu.spartanweaponryunofficial.advancement.criterion.BoomerangReturnKillTrigger;
import org.xiyu.spartanweaponryunofficial.advancement.criterion.BrewOilTrigger;

public class ModCriteriaTriggers {
    // NeoForge 1.21: Must use DeferredRegister for trigger types
    public static final DeferredRegister<CriterionTrigger<?>> REGISTRY =
            DeferredRegister.create(Registries.TRIGGER_TYPE, ModSpartanWeaponry.ID);

    public static final Supplier<BrewOilTrigger> BREW_OIL =
            REGISTRY.register("brew_oil", BrewOilTrigger::new);
    public static final Supplier<BoomerangBounceKillTrigger> BOOMERANG_BOUNCE_KILL =
            REGISTRY.register("boomerang_bounce_kill", BoomerangBounceKillTrigger::new);
    public static final Supplier<BoomerangReturnKillTrigger> BOOMERANG_RETURN_KILL =
            REGISTRY.register("boomerang_return_kill", BoomerangReturnKillTrigger::new);
}
