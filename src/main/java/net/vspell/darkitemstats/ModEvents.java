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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = DarkItemStats.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onItemCraft(PlayerEvent.ItemCraftedEvent event){

        ItemStack item = event.getCrafting();

        if(item.getItem() instanceof ArmorItem){
            applyTags(item);
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
        }
    }

    private static UUID genUUID(ItemStack stack, String prefix, String tagName){
        assert stack.getTag() != null;
        return UUID.nameUUIDFromBytes((prefix + stack.getTag().getInt(tagName)).getBytes());
    }

    private static void applyTags(ItemStack stack){

        String[] rarities = {"Common", "Uncommon", "Rare", "Epic", "Legendary"};

        Random random = new Random(); // making an instance of the Random class
        int bonusArmor = random.nextInt(1, 5);
        int bonusArmorToughness = random.nextInt(1, 4);
        double bonusPhysicalDamage = (double) Math.round(random.nextDouble(0.01, 0.07) * 100) / 100;
        String rarity = rarities[random.nextInt(rarities.length)];


        if(stack.getTag() == null) {
            stack.setTag(new CompoundTag());
        }

        stack.getTag().putInt("BonusArmor", bonusArmor);
        stack.getTag().putInt("BonusArmorToughness", bonusArmorToughness);
        stack.getTag().putDouble("PhysicalDamageBonus", bonusPhysicalDamage);
        stack.getTag().putString("Rarity", rarity);

    }
}
