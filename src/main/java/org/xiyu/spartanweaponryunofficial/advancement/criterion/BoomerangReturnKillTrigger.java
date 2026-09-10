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

public class BoomerangReturnKillTrigger
        extends SimpleCriterionTrigger<BoomerangReturnKillTrigger.TriggerInstance> {
    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(ModSpartanWeaponry.ID, "boomerang_return_kill");

    @Override
    public @NotNull Codec<BoomerangReturnKillTrigger.TriggerInstance> codec() {
        return BoomerangReturnKillTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer playerIn) {
        this.trigger(playerIn, (triggerInstance) -> true);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player)
            implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<BoomerangReturnKillTrigger.TriggerInstance> CODEC =
                RecordCodecBuilder.create(
                        instance ->
                                instance.group(
                                                EntityPredicate.ADVANCEMENT_CODEC
                                                        .optionalFieldOf("player")
                                                        .forGetter(TriggerInstance::player))
                                        .apply(instance, TriggerInstance::new));

        public static Criterion<BoomerangReturnKillTrigger.TriggerInstance> boomerangReturnKill() {
            return ModCriteriaTriggers.BOOMERANG_RETURN_KILL
                    .get()
                    .createCriterion(
                            new BoomerangReturnKillTrigger.TriggerInstance(Optional.empty()));
        }
    }
}
