package net.vspell.darkitemstats;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;


@Mod.EventBusSubscriber(modid = DarkItemStats.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TooltipHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event){

        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof ArmorItem && stack.hasTag()){
            CompoundTag tag = stack.getTag();
            List<Component> tooltip = event.getToolTip();

            if (tag.contains("BonusArmor")){
                event.getToolTip().add(
                        Component.literal("+" + tag.getInt("BonusArmor") + " Additional Armor")
                                .withStyle(ChatFormatting.BLUE)
                );
                removeTooltipLines(tooltip, tag, "BonusArmor", "Armor");
            }

            if (tag.contains("BonusArmorToughness")){
                event.getToolTip().add(
                        Component.literal("+" + tag.getInt("BonusArmorToughness") + " Additional Armor Toughness")
                                .withStyle(ChatFormatting.BLUE)
                );
                removeTooltipLines(tooltip, tag, "BonusArmorToughness", "Armor Toughness");
            }

        }
    }

    private static void removeTooltipLines(List<Component> tooltip, CompoundTag tag, String attModName, String linePostfix){

        //Looping through the tooltip lines and deleting the one added by the attribute modifier
        for (int i = 0; i < tooltip.size() ; i++) {

            if (tooltip.get(i).getString().equals("+" + tag.getInt(attModName) + " " + linePostfix)){ //concat result: + X *vanilla postfix*
                tooltip.remove(i);
                break;
            }
        }

    }

}
