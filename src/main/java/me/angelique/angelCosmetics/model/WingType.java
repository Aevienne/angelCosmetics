package me.angelique.angelCosmetics.model;

public enum WingType {
    NONE("None"),
    ANGEL("Angel"),
    DEMON("Demon"),
    ARCANE("Arcane");

    private final String displayName;

    WingType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
