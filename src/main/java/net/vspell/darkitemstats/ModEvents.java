package net.vspell.darkitemstats;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = DarkItemStats.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    private static final UUID BONUS_ARMOR_UUID = UUID.fromString("a5a9c4ea-3560-4f72-9713-aee4a01c9476");

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
                        new AttributeModifier(BONUS_ARMOR_UUID ,"Bonus Armor", tag.getInt("BonusArmor"), AttributeModifier.Operation.ADDITION));
            }

        }
    }

    private static void applyTags(ItemStack stack){

        Random random = new Random(); // making an instance of the Random class
        int bonusArmor = random.nextInt(3);

        if(stack.getTag() == null) {
            stack.setTag(new CompoundTag());
        }

        stack.getTag().putInt("BonusArmor", bonusArmor);

    }

}
