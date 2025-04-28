package net.vspell.darkitemstats;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.function.Supplier;

public class ModTags {

    private static final Random RANDOM = new Random();

    public static Rarity rollRarity(Map<Rarity, Double> rarity_probability_map) { // dark probability magic
        double roll = RANDOM.nextDouble(); // Random value between 0.0 and 1.0
        double cumulativeProbability = 0.0;

        for (Map.Entry<Rarity, Double> entry : rarity_probability_map.entrySet()) {
            cumulativeProbability += entry.getValue();

            if (roll < cumulativeProbability) {
                return entry.getKey();
            }
        }
        return Rarity.COMMON;
    }

    public static void applyRandomModifierTagsToNBT(ItemStack stack, Map<Rarity, Double> rarity_probability_map, Map<String, Supplier<Object>> attPool){

        int rarity = rollRarity(rarity_probability_map).getAttributeCountCount();
        List<String> keys = new ArrayList<>(attPool.keySet());
        Set<String> selectedEnchants = new HashSet<>();
        CompoundTag tag = stack.getTag();
        Collections.shuffle(keys);

        if(tag == null) {
            stack.setTag(new CompoundTag());
        }

        tag.putInt("Rarity", rarity);

        while (selectedEnchants.size() < rarity && selectedEnchants.size() < keys.size()){
            selectedEnchants.add(keys.get(RANDOM.nextInt(keys.size())));
        }

        for (String attKey : selectedEnchants) {
            Object att = attPool.get(attKey).get();

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
