package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {
    protected String state;
    protected double cost;
    protected AState cameFrom;

    public AState(String state) {
        this.state = state;
        this.cost = 0;
        this.cameFrom = null;
    }

    public String getState() { return state; }

    public double getCost() { return cost; }

    public void setCost(double cost) { this.cost = cost; }

    public AState getCameFrom() { return this.cameFrom; }

    public void setCameFrom(AState cameFrom) { this.cameFrom = cameFrom; }

    @Override
    public String toString() { return state; }
}
