package comp1110.ass2;

public class Construction {
    private ConstructionType type;
    private boolean built = false;
    private boolean available;
    private final int number;

    /**
     * Create a class of Construction that represents the
     * construction that the ConstructionPane is actually bound to.
     *
     * @param type The type of Construction, enum type which has:
     *            ROAD, CITY, SETTLEMENT, JOKER, KNIGHT.
     * @param number The number of Construction.
     * @param available Is the Construction available or not now.
     */
    public Construction(ConstructionType type, int number, boolean available) {
        this.type = type;
        this.available = available;
        this.number = number;
    }

    /**
     * Get the type of this Construction.
     *
     * @return The type of this Construction, enum type which has:
     * ROAD, CITY, SETTLEMENT, JOKER, KNIGHT.
     */
    public ConstructionType getType() {
        return type;
    }

    /**
     * Set the type of this Construction.
     *
     * @param type The type of this Construction, enum type which
     *            has: ROAD, CITY, SETTLEMENT, JOKER, KNIGHT.
     */
    public void setType(ConstructionType type) {
        this.type = type;
    }

    /**
     * Get this Construction is built or not.
     *
     * @return true iff the Construction is built, false otherwise.
     */
    public boolean isBuilt() {
        return built;
    }

    /**
     * Set this Construction is built or not.
     *
     * @param built true iff the Construction is built, false otherwise.
     */
    public void setBuilt(boolean built) {
        this.built = built;
    }

    /**
     * Get this Construction is available (can be build) or not.
     *
     * @return true iff the Construction is available, false otherwise.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Set this Construction is available (can be build) or not.
     *
     * @param available true iff the Construction is available, false
     *                 otherwise.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Get the number of this Construction.
     *
     * @return Integer; The number of this Construction.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Generate the string representation of this Construction.
     *
     * @return String; The string representation of this Construction.
     */
    @Override
    public String toString() {
        return this.type.toString() + number;
    }
}
