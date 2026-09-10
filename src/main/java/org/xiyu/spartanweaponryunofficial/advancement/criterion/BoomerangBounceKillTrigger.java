package org.xiyu.spartanweaponryunofficial.advancement.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.xiyu.spartanweaponryunofficial.ModSpartanWeaponry;
import org.xiyu.spartanweaponryunofficial.init.ModCriteriaTriggers;

public class BoomerangBounceKillTrigger
        extends SimpleCriterionTrigger<BoomerangBounceKillTrigger.TriggerInstance> {
    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(ModSpartanWeaponry.ID, "boomerang_bounce_kill");

    @Override
    public @NotNull Codec<BoomerangBounceKillTrigger.TriggerInstance> codec() {
        return BoomerangBounceKillTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer playerIn) {
        this.trigger(playerIn, (triggerInstance) -> true);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player)
            implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<BoomerangBounceKillTrigger.TriggerInstance> CODEC =
                RecordCodecBuilder.create(
                        instance ->
                                instance.group(
                                                EntityPredicate.ADVANCEMENT_CODEC
                                                        .optionalFieldOf("player")
                                                        .forGetter(TriggerInstance::player))
                                        .apply(instance, TriggerInstance::new));

        public static Criterion<BoomerangBounceKillTrigger.TriggerInstance> boomerangBounceKill() {
            return ModCriteriaTriggers.BOOMERANG_BOUNCE_KILL
                    .get()
                    .createCriterion(
                            new BoomerangBounceKillTrigger.TriggerInstance(Optional.empty()));
        }
    }
}
