package me.angelique.angelCosmetics.model;

public final class PlayerCosmeticState {
    private TrailType trailType;
    private WingType wingType;
    private RecallType recallType;

    public PlayerCosmeticState() {
        this(TrailType.FLAME, WingType.ANGEL, RecallType.BLUE);
    }

    public PlayerCosmeticState(TrailType trail, WingType wings, RecallType recall) {
        this.trailType = trail;
        this.wingType = wings;
        this.recallType = recall;
    }

    public TrailType getTrailType() {
        return trailType;
    }

    public void setTrailType(TrailType trailType) {
        this.trailType = trailType;
    }

    public WingType getWingType() {
        return wingType;
    }

    public void setWingType(WingType wingType) {
        this.wingType = wingType;
    }

    public RecallType getRecallType() {
        return recallType;
    }

    public void setRecallType(RecallType recallType) {
        this.recallType = recallType;
    }
}
