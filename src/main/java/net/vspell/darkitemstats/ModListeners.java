package net.vspell.darkitemstats;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = DarkItemStats.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModListeners {

    @SubscribeEvent
    public static void onItemCraft(PlayerEvent.ItemCraftedEvent event){
        ItemStack stack = event.getCrafting();
        Item item = stack.getItem();

//        if(stack.getItem() instanceof ArmorItem){
//            ModTags.applyRandomModifierTagsToNBT(item, ModTags.ARMOR_ENCHANTMENTS_POOL);
//        }
        if (item instanceof ArmorItem){

            if (((ArmorItem) item).getEquipmentSlot() == EquipmentSlot.FEET){
                ModTags.applyRandomModifierTagsToNBT(stack, Rarity.DEFAULT_RARITY_PROBABILITY_MAP, AttributePool.BOOTS_POOL.getAsMap());
            } else{
                ModTags.applyRandomModifierTagsToNBT(stack, Rarity.DEFAULT_RARITY_PROBABILITY_MAP, AttributePool.ARMOR_POOL.getAsMap());
            }

        } else if (item instanceof TieredItem) {
           //later
        }

    }

    @SubscribeEvent
    public static void onAttributeModification(ItemAttributeModifierEvent event){
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        CompoundTag tag = stack.getTag();

        if (item instanceof ArmorItem armor && tag != null){

            // Generic Armor
            if (tag.contains("BonusArmor") && armor.getEquipmentSlot() == event.getSlotType()){

                event.addModifier(Attributes.ARMOR,
                        new AttributeModifier(
                                genUUID(stack, "bonus_armor_", "BonusArmor"),
                                "Bonus Armor",
                                tag.getInt("BonusArmor"),
                                AttributeModifier.Operation.ADDITION)
                );
            }

            if (tag.contains("BonusArmorToughness") && armor.getEquipmentSlot() == event.getSlotType()){

                event.addModifier(Attributes.ARMOR_TOUGHNESS,
                        new AttributeModifier(genUUID(stack, "bonus_armor_toughness_", "BonusArmorToughness"),
                                "Bonus Armor Toughness",
                                tag.getInt("BonusArmorToughness"),
                                AttributeModifier.Operation.ADDITION)
                );
            }

            if (tag.contains("PhysicalDamageBonus") && armor.getEquipmentSlot() == event.getSlotType()){

                event.addModifier(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(genUUID(stack, "physical_damage_bonus_", "PhysicalDamageBonus"),
                                "Physical Damage Bonus",
                                tag.getDouble("PhysicalDamageBonus"),
                                AttributeModifier.Operation.MULTIPLY_BASE)
                );
            }

            if (tag.contains("KnockbackResistance") && armor.getEquipmentSlot() == event.getSlotType()){

                event.addModifier(Attributes.KNOCKBACK_RESISTANCE,
                        new AttributeModifier(genUUID(stack, "knockback_resistance_", "KnockbackResistance"),
                                "Knockback Resistance",
                                tag.getDouble("KnockbackResistance"),
                                AttributeModifier.Operation.ADDITION) // since the default is 0
                );
            }

            // Boot modifiers
            if (tag.contains("BonusMovespeed") && armor.getEquipmentSlot() == event.getSlotType()){

                event.addModifier(Attributes.MOVEMENT_SPEED,
                        new AttributeModifier(
                                genUUID(stack, "bonus_movespeed_", "BonusMovespeed"),
                                "Bonus Movespeed",
                                tag.getDouble("BonusMovespeed"),
                                AttributeModifier.Operation.MULTIPLY_BASE)
                );
            }
        }
    }

    private static UUID genUUID(ItemStack stack, String prefix, String tagName){
        return UUID.nameUUIDFromBytes((prefix + stack.getTag().getInt(tagName)).getBytes());
    }
}
