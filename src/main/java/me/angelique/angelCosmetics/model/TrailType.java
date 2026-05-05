package me.angelique.angelCosmetics.model;

public enum TrailType {
    NONE("None"),
    FLAME("Flame"),
    ENCHANT("Enchant"),
    HEART("Heart"),
    TOTEM("Totem");

    private final String displayName;

    TrailType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
