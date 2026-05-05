package me.angelique.angelCosmetics.model;

public enum RecallType {
    NONE("None"),
    BLUE("Blue Recall"),
    GOLD("Golden Recall"),
    VOID("Void Recall");

    private final String displayName;

    RecallType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
