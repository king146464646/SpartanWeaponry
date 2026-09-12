package org.xiyu.spartanweaponryunofficial.api.trait;

import com.google.common.collect.ImmutableMultimap;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.xiyu.spartanweaponryunofficial.api.SpartanWeaponryAPI;

public class SweepWeaponTrait extends WeaponTraitWithMagnitude implements IMeleeTraitCallback {
    public static final ResourceLocation SWEEP_DAMAGE_RATIO_MODIFIER =
            ResourceLocation.fromNamespaceAndPath(SpartanWeaponryAPI.MOD_ID, "sweep_damage_ratio");

    public SweepWeaponTrait(String propType, String propModId) {
        super(propType, propModId, TraitQuality.POSITIVE);
        this.isMelee = true;
    }

    @Override
    public Optional<IMeleeTraitCallback> getMeleeCallback() {
        return Optional.of(this);
    }

    /**
     * Applies the sweep bonus through the vanilla {@link Attributes#SWEEPING_DAMAGE_RATIO}
     * attribute, which is how 1.21 computes sweep damage in {@code Player.attack}.
     */
    @Override
    public void onModifyAttributesMelee(
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {
        builder.put(
                Attributes.SWEEPING_DAMAGE_RATIO.value(),
                new AttributeModifier(
                        SWEEP_DAMAGE_RATIO_MODIFIER,
                        this.getMagnitude(),
                        AttributeModifier.Operation.ADD_VALUE));
    }

    @Override
    protected void addTooltipDescription(ItemStack stack, List<Component> tooltip) {
        if (this.level == 1)
            tooltip.add(
                    tooltipIndent()
                            .append(
                                    Component.translatable(
                                                    String.format(
                                                            "tooltip.%s.trait.%s.fixed.desc",
                                                            SpartanWeaponryAPI.MOD_ID, this.type),
                                                    this.magnitude * 100.0f)
                                            .withStyle(WeaponTrait.DESCRIPTION_FORMAT)));
        else
            tooltip.add(
                    tooltipIndent()
                            .append(
                                    Component.translatable(
                                                    String.format(
                                                            "tooltip.%s.trait.%s.desc",
                                                            this.modId, this.type),
                                                    this.magnitude * 100.0f)
                                            .withStyle(WeaponTrait.DESCRIPTION_FORMAT)));
    }

    @Override
    public boolean isEnchantmentCompatible(Holder<Enchantment> enchantIn) {
        // Sweep I weapons (Longsword, Katana, Saber, Lance) can get Sweeping Edge
        // Sweep II/III weapons cannot (matching 1.20.1 behavior)
        return this.level == 1 && enchantIn.is(Enchantments.SWEEPING_EDGE);
    }

    @Override
    public boolean canPerformToolAction(ItemStack stack, ItemAbility action) {
        return action == ItemAbilities.SWORD_SWEEP;
    }
}
