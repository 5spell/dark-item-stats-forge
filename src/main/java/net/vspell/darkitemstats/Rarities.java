package net.vspell.darkitemstats;

public class Rarities {

    public static final String[] DEFAULT_RARITY_ARRAY = {"Legendary", "Epic", "Rare", "Uncommon", "Common"};
    public static final double[] DEFAULT_PROBABILITY_ARRAY = {1.0, 0, 0, 0, 0};

    public static String selectRarity(String[] rarities, double[] probabilities){
        double balls = 0.0; // don't question my naming

        for (int i = 0; i < probabilities.length; i++) {
            balls += probabilities[i];

            if(balls <= probabilities[i]){
                return rarities[i];
            }
        }

        return rarities[rarities.length - 1]; // return the last element if no other ones were rolled (Common rarity usually)

    }
}
