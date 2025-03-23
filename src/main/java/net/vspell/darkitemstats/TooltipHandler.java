package net.vspell.darkitemstats;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.DecimalFormat;
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
                ChatFormatting color;
                String rarName;
                switch (tag.getInt("Rarity")){
                    default:
                        color = ChatFormatting.WHITE;
                        rarName = "Common";

                    case 0:
                        color = ChatFormatting.WHITE;
                        rarName = "Common";
                        break;

                    case 1:
                        color = ChatFormatting.GREEN;
                        rarName = "Uncommon";
                        break;

                    case 2:
                        color = ChatFormatting.BLUE;
                        rarName = "Rare";
                        break;

                    case 3:
                        color = ChatFormatting.DARK_PURPLE;
                        rarName = "Epic";
                        break;

                    case 4:
                        color = ChatFormatting.GOLD;
                        rarName = "Legendary";
                        break;
                }
                customLines.add(Component.literal( rarName + " Item").withStyle(color));
            }

            customLines.add(Component.empty());

            if (tag.contains("BonusArmorToughness")){
                customLines.add(
                        Component.literal("+" + tag.getInt("BonusArmorToughness") + " Additional Armor Toughness")
                                .withStyle(ChatFormatting.BLUE)
                );
                String tLine = tag.getInt("BonusArmorToughness") + " Armor Toughness";
                removeTooltipLinesContaining(tooltip, tLine);
            }

            if (tag.contains("BonusArmor")){
                customLines.add(
                        Component.literal("+" + tag.getInt("BonusArmor") + " Additional Armor")
                                .withStyle(ChatFormatting.BLUE)
                );
                String tLine = tag.getInt("BonusArmor") + " Armor";
                removeTooltipLinesContaining(tooltip, tLine);
            }

            if (tag.contains("PhysicalDamageBonus")){
                customLines.add(
                        Component.literal("+" + (int)(tag.getDouble("PhysicalDamageBonus") * 100) + "% Physical Damage Bonus")
                                .withStyle(ChatFormatting.BLUE)
                );
                String tLine = (int)(tag.getDouble("PhysicalDamageBonus") * 100) + "% Attack Damage";
                removeTooltipLinesContaining(tooltip, tLine);
            }

            if (tag.contains("KnockbackResistance")){
                customLines.add(
                        Component.literal("+" + (int)(tag.getDouble("KnockbackResistance") * 100) + "% Knockback Resistance")
                                .withStyle(ChatFormatting.BLUE)
                );
                String tLine = String.format("%.1f", (tag.getDouble("KnockbackResistance") * 10)) + " Knockback Resistance";
                removeTooltipLinesContaining(tooltip, tLine);
            }

            tooltip.addAll(tooltip.size() - 2, customLines);
        }
    }

    private static void removeTooltipLinesContaining(List<Component> tooltip, String targetSubstring) {
        tooltip.removeIf(line -> {
            String strippedText = ChatFormatting.stripFormatting(line.getString()).trim();
            return strippedText.contains(targetSubstring);
        });
    }
}
