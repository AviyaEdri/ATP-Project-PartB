package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the solution to a search problem.
 * It stores the path (a list of states) from the start state to the goal state.
 */
public class Solution implements Serializable{
    private ArrayList<AState> solutionPath;

    public Solution() {
        solutionPath = new ArrayList<>();
    }

    public Solution(AState goal) {
        solutionPath = new ArrayList<>();
        AState current = goal;
        while (current != null) {
            solutionPath.add(0, current); // Add the current state to the beginning of the path
            current = current.getCameFrom(); // Move to the previous state
        }
    }


    public void setSolutionPath(ArrayList<AState> path) {
        this.solutionPath = path;
    }

    public ArrayList<AState> getSolutionPath() {
        return solutionPath;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AState state : solutionPath) {
            sb.append(state.toString()).append("\n");
        }
        return sb.toString();
    }
}
