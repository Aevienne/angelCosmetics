package me.angelique.angelCosmetics.model;

public final class PlayerCosmeticState {
    private TrailType trailType = TrailType.FLAME;
    private WingType wingType = WingType.ANGEL;
    private RecallType recallType = RecallType.BLUE;

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
