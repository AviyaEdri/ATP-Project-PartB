package algorithms.search;

/**
 * Abstract class representing a generic state within the search space.
 * It includes a description, a cost (the effort to reach this state),
 * and a reference to the preceding state (for reconstructing the path).
 */
public abstract class AState {
    protected String state; // Unique state description or identifier
    protected double cost;  // Cost to reach this state
    protected AState cameFrom; // Pointer to the previous state for path reconstruction

    public AState(String state) {
        this.state = state;
        this.cost = 0;
        this.cameFrom = null;
    }

    public String getState() {
        return state;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    @Override
    public String toString() {
        return state;
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof AState))
            return false;
        AState state = (AState) other;
        return this.state.equals(state.state);
    }

    @Override
    public int hashCode(){
        return state.hashCode();
    }


}
