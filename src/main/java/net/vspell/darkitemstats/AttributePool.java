package net.vspell.darkitemstats;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public enum AttributePool {
    ARMOR_POOL(Map.of(
            "BonusArmor", () -> ThreadLocalRandom.current().nextInt(1, 5),
            "BonusArmorToughness", () -> ThreadLocalRandom.current().nextInt(1, 4),
            "PhysicalDamageBonus", () -> randomRoundDouble(0.1, 0.3),
            "KnockbackResistance", () -> randomRoundDouble(0.1, 0.6)
    )),
    BOOTS_POOL(Map.of(
            "BonusArmor", () -> ThreadLocalRandom.current().nextInt(1, 5),
            "BonusArmorToughness", () -> ThreadLocalRandom.current().nextInt(1, 4),
            "PhysicalDamageBonus", () -> randomRoundDouble(0.1, 0.3),
            "KnockbackResistance", () -> randomRoundDouble(0.1, 0.6),
            "BonusMovespeed", () -> randomRoundDouble(0.1, 0.3)
    ));
//    TIERED_POOL();

    private static final Random RANDOM = new Random();

    private final Map<String, Supplier<Object>> attributePool;

    AttributePool(Map<String, Supplier<Object>> attributePool) {
        this.attributePool = attributePool;
    }

    //Gets the AttributePool as Map<String, Supplier<Object>> for map functionality
    public Map<String, Supplier<Object>> getAsMap() {
        return attributePool;
    }



    private static double randomRoundDouble(Double low, Double high){
        return (double) Math.round(RANDOM.nextDouble(low, high) * 100) / 100;
    }

}
