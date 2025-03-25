package net.vspell.darkitemstats;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.function.Supplier;

public class ModTags {

    private static final Random RANDOM = new Random();

    public static final int[] RARITY_ARRAY = {4, 3, 2, 1, 0}; //as amount of enchants
    public static final double[] PROBABILITY_ARRAY = {0.01, 0.49, 0.5, 0, 0};

    public static final Map<String, Supplier<Object>> ARMOR_ENCHANTMENTS_POOL = new HashMap<>();
    static {
        ARMOR_ENCHANTMENTS_POOL.put("BonusArmor", () -> RANDOM.nextInt(1, 5));
        ARMOR_ENCHANTMENTS_POOL.put("BonusArmorToughness", () -> RANDOM.nextInt(1, 4));
        ARMOR_ENCHANTMENTS_POOL.put("PhysicalDamageBonus", () -> (double) Math.round(RANDOM.nextDouble(0.1, 0.3) * 100) / 100);
        ARMOR_ENCHANTMENTS_POOL.put("KnockbackResistance", () -> (double) Math.round(RANDOM.nextDouble(0.1, 0.6) * 100) / 100);
    } // apparently this is a thing

    public static final Map<String, Supplier<Object>> BOOTS_ENCHANTMENTS_POOL = new HashMap<>();
    static {
        BOOTS_ENCHANTMENTS_POOL.put("BonusArmor", () -> RANDOM.nextInt(1, 5));
        BOOTS_ENCHANTMENTS_POOL.put("BonusArmorToughness", () -> RANDOM.nextInt(1, 4));
        BOOTS_ENCHANTMENTS_POOL.put("PhysicalDamageBonus", () -> (double) Math.round(RANDOM.nextDouble(0.1, 0.3) * 100) / 100);
        BOOTS_ENCHANTMENTS_POOL.put("KnockbackResistance", () -> (double) Math.round(RANDOM.nextDouble(0.1, 0.6) * 100) / 100);
        BOOTS_ENCHANTMENTS_POOL.put("BonusMovespeed", () -> (double) Math.round(RANDOM.nextDouble(0.1, 0.3) * 100) / 100);
    }

    public static final Map<String, Supplier<Object>> TIERED_ENCHANTMENTS_POOL = new HashMap<>();
    static {
        
    }

    public static int rollRarity(int[] rarities, double[] probabilities){ // dark probability magic
        double roll = RANDOM.nextDouble(); // Random value between 0.0 and 1.0
        double cumulativeProbability = 0.0;

        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];

            if (roll < cumulativeProbability) {
                return rarities[i];
            }
        }
        return rarities[rarities.length - 1]; // Default to the last rarity if no match
    }

    public static void applyRandomModifierTagsToNBT(ItemStack stack, Map<String, Supplier<Object>> enchantPool){

        int rarity = rollRarity(RARITY_ARRAY, PROBABILITY_ARRAY);
        List<String> keys = new ArrayList<>(enchantPool.keySet());
        Set<String> selectedEnchants = new HashSet<>();
        CompoundTag tag = stack.getTag();
        Collections.shuffle(keys);

        if(tag == null) {
            stack.setTag(new CompoundTag());
        }

        tag.putInt("Rarity", rarity);// the warning's fine, the tag is never null

        while (selectedEnchants.size() < rarity && selectedEnchants.size() < keys.size()){
            selectedEnchants.add(keys.get(RANDOM.nextInt(keys.size())));
        }

        for (String attKey : selectedEnchants) {
            Object att = enchantPool.get(attKey).get();

            if (att instanceof Integer){
                tag.putInt(attKey, (Integer) att);
            } else if (att instanceof Double){
                tag.putDouble(attKey, (Double) att);
            } else if (att instanceof String){
                tag.putString(attKey, (String) att);
            } else if (att instanceof Boolean){
                tag.putBoolean(attKey, (Boolean) att);
            }
        }
    }
}
