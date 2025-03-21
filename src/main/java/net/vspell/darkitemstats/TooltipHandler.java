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

                tooltip.removeIf(line -> line.getString().equals("+" + tag.getInt("BonusArmor") + " Armor"));

            }



        }
    }
}
