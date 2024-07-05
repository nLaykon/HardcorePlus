package org.laykon.thebois.Utilities;

public enum ChallengeTypes {
    FISHING("fishing", 30, 100),
    FIGHTING("fighting", 100, 200),
    MINING("mining", 50, 150),
    BUILDING("building", 50, 150),
    ACHIEVEMENTS("achievements", 5, 10);

    private final String name;
    private final int minDifficulty;
    private final int maxDifficulty;

    ChallengeTypes(String name, int minDifficulty, int maxDifficulty) {
        this.name = name;
        this.minDifficulty = minDifficulty;
        this.maxDifficulty = maxDifficulty;
    }

    public String getName() {
        return name;
    }

    public int getMinDifficulty() {
        return minDifficulty;
    }

    public int getMaxDifficulty() {
        return maxDifficulty;
    }
}
