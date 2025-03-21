package net.vspell.darkitemstats;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;


@Mod.EventBusSubscriber(modid = DarkItemStats.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TooltipHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event){

        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof ArmorItem && stack.hasTag()){
            CompoundTag tag = stack.getTag();
            List<Component> tooltip = event.getToolTip();
            List<Component> customLines = new ArrayList<>();


            customLines.add(Component.empty()); //spacer

            if (tag.contains("Rarity")){
                ChatFormatting Color;
                switch (tag.getString("Rarity")){
                    default:
                        Color = ChatFormatting.WHITE;

                    case "Common":
                        Color = ChatFormatting.WHITE;
                        break;

                    case "Uncommon":
                        Color = ChatFormatting.GREEN;
                        break;

                    case "Rare":
                        Color = ChatFormatting.BLUE;
                        break;

                    case "Epic":
                        Color = ChatFormatting.DARK_PURPLE;
                        break;

                    case "Legendary":
                        Color = ChatFormatting.GOLD;
                        break;
                }
                customLines.add(Component.literal(tag.getString("Rarity") + " Item").withStyle(Color));
            }

            customLines.add(Component.empty());

            if (tag.contains("BonusArmorToughness")){
                customLines.add(
                        Component.literal("+" + tag.getInt("BonusArmorToughness") + " Additional Armor Toughness")
                                .withStyle(ChatFormatting.BLUE)
                );
                removeTooltipLinesInt(tooltip, tag, "BonusArmorToughness", "Armor Toughness");
            }

            if (tag.contains("BonusArmor")){
                customLines.add(
                        Component.literal("+" + tag.getInt("BonusArmor") + " Additional Armor")
                                .withStyle(ChatFormatting.BLUE)
                );
                removeTooltipLinesInt(tooltip, tag, "BonusArmor", "Armor");
            }

            if (tag.contains("PhysicalDamageBonus")){
                customLines.add(
                        Component.literal("+" + (int)(tag.getDouble("PhysicalDamageBonus") * 100) + "% Physical Damage Bonus")
                                .withStyle(ChatFormatting.BLUE)
                );
                removeTooltipLinesDouble(tooltip, tag, "PhysicalDamageBonus", "Attack Damage");
            }



            tooltip.addAll(tooltip.size() - 2, customLines);
        }
    }

    private static void removeTooltipLinesInt(List<Component> tooltip, CompoundTag tag, String attModName, String linePostfix){

        //Looping through the tooltip lines and deleting the one added by the attribute modifier
        for (int i = 0; i < tooltip.size() ; i++) {

            if (tooltip.get(i).getString().equals("+" + tag.getInt(attModName) + " " + linePostfix)){ //concat result: + X *vanilla postfix*
                tooltip.remove(i);
                break;
            }
        }
    }

    private static void removeTooltipLinesDouble(List<Component> tooltip, CompoundTag tag, String attModName, String linePostfix){

        for (int i = 0; i < tooltip.size() ; i++) {

            if (tooltip.get(i).getString().equals("+" + (int)(tag.getDouble(attModName) * 100) + "% " + linePostfix)){ //concat result: + X% *vanilla postfix*
                tooltip.remove(i);
                break;
            }
        }
    }

}
