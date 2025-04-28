package net.vspell.darkitemstats;

import java.util.Map;

public enum Rarity {
    COMMON(0),
    UNCOMMON(1),
    RARE(2),
    EPIC(3),
    LEGENDARY(4);

    private final int attributeCount;

    Rarity(int attributeCount) {
        this.attributeCount = attributeCount;
    }


    public static final Map<Rarity, Double> DEFAULT_RARITY_PROBABILITY_MAP = Map.of(
            COMMON, 0.0,
            UNCOMMON, 0.0,
            RARE, 0.5,
            EPIC, 0.49,
            LEGENDARY, 0.01
    );


    public int getAttributeCountCount() {
        return attributeCount;
    }
}


//public static final int[] RARITY_ARRAY = {4, 3, 2, 1, 0}; //as amount of enchants
//public static final double[] PROBABILITY_ARRAY = {0.01, 0.49, 0.5, 0, 0};