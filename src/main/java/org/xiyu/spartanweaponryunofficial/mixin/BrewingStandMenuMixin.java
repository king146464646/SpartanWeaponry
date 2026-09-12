package org.xiyu.spartanweaponryunofficial.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * NeoForge 1.21.x only fires {@code PlayerBrewedPotionEvent} when the brewed item has the {@code
 * POTION_CONTENTS} data component. Weapon oil uses a custom capability instead, so the event never
 * fires. This mixin fires the event for items that lack {@code POTION_CONTENTS} (like weapon oil)
 * so the brew-oil advancement trigger works.
 */
@Mixin(targets = "net.minecraft.world.inventory.BrewingStandMenu$PotionSlot")
public class BrewingStandMenuMixin {
    @Inject(method = "onTake", at = @At("HEAD"))
    private void spartanweaponry$fireBrewOilEvent(Player player, ItemStack stack, CallbackInfo ci) {
        if (player instanceof ServerPlayer) {
            java.util.Optional<Holder<Potion>> optional =
                    stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                            .potion();
            if (optional.isEmpty()) {
                EventHooks.onPlayerBrewedPotion(player, stack);
            }
        }
    }
}
