package comp1110.ass2;

public enum ConstructionType {
    ROAD, CITY, SETTLEMENT, JOKER, KNIGHT;

    /**
     * Generate the string represent of the type of Construction.
     * @return String; The string representation of the type of
     * Construction.
     */
    @Override
    public String toString() {
        return switch (this) {
            case ROAD -> "R";
            case CITY -> "C";
            case SETTLEMENT -> "S";
            case JOKER -> "J";
            case KNIGHT -> "K";
            default -> "";
        };
    }
}
