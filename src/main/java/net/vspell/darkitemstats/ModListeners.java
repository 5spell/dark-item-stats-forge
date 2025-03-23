package net.vspell.darkitemstats;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = DarkItemStats.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModListeners {

    @SubscribeEvent
    public static void onItemCraft(PlayerEvent.ItemCraftedEvent event){
        ItemStack item = event.getCrafting();

        if(item.getItem() instanceof ArmorItem){
            ModTags.applyRandomTags(item);
        }
    }

    @SubscribeEvent
    public static void onAttributeModification(ItemAttributeModifierEvent event){
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        CompoundTag tag = stack.getTag();

        if (item instanceof ArmorItem && tag != null){

            if (tag.contains("BonusArmor") && ((ArmorItem) stack.getItem()).getEquipmentSlot() == event.getSlotType()){

                event.addModifier(Attributes.ARMOR,
                        new AttributeModifier(
                                genUUID(stack, "bonus_armor_", "BonusArmor"),
                                "Bonus Armor",
                                tag.getInt("BonusArmor"),
                                AttributeModifier.Operation.ADDITION)
                );
            }

            if (tag.contains("BonusArmorToughness") && ((ArmorItem) stack.getItem()).getEquipmentSlot() == event.getSlotType()){

                event.addModifier(Attributes.ARMOR_TOUGHNESS,
                        new AttributeModifier(genUUID(stack, "bonus_armor_toughness_", "BonusArmorToughness"),
                                "Bonus Armor Toughness",
                                tag.getInt("BonusArmorToughness"),
                                AttributeModifier.Operation.ADDITION)
                );
            }

            if (tag.contains("PhysicalDamageBonus") && ((ArmorItem) stack.getItem()).getEquipmentSlot() == event.getSlotType()){

                event.addModifier(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(genUUID(stack, "physical_damage_bonus_", "PhysicalDamageBonus"),
                                "Physical Damage Bonus",
                                tag.getDouble("PhysicalDamageBonus"),
                                AttributeModifier.Operation.MULTIPLY_BASE)
                );
            }

            if (tag.contains("KnockbackResistance") && ((ArmorItem) stack.getItem()).getEquipmentSlot() == event.getSlotType()){

                event.addModifier(Attributes.KNOCKBACK_RESISTANCE,
                        new AttributeModifier(genUUID(stack, "knockback_resistance_", "KnockbackResistance"),
                                "Knockback Resistance",
                                tag.getDouble("KnockbackResistance"),
                                AttributeModifier.Operation.ADDITION)
                );
            }
        }
    }

    private static UUID genUUID(ItemStack stack, String prefix, String tagName){
        return UUID.nameUUIDFromBytes((prefix + stack.getTag().getInt(tagName)).getBytes());
    }
}
