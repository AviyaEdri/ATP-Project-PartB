package algorithms.search;

import java.util.*;

/**
 * This class implements the Depth First Search algorithm.
 */
public class DepthFirstSearch extends ASearchingAlgorithm {

    @Override
    public Solution solve(ISearchable domain) {
        numberOfNodesEvaluated = 0;

        // Use a Stack for DFS (LIFO)
        Stack<AState> openStack = new Stack<>();
        Set<AState> closedSet = new HashSet<>();

        AState startState = domain.getStartState();
        openStack.push(startState);

        while (!openStack.isEmpty()) {
            AState current = openStack.pop();
            numberOfNodesEvaluated++;  // Increase the evaluated nodes count

            // Check if the current state is the goal
            if (current.equals(domain.getGoalState())) {
                return reconstructSolution(current);
            }

            closedSet.add(current);
            // Expand current state: get all successors
            List<AState> successors = domain.getAllPossibleStates(current);
            for (AState successor : successors) {
                if (!closedSet.contains(successor) && !openStack.contains(successor)) {
                    successor.setCameFrom(current);
                    openStack.push(successor);
                }
            }
        }
        // Return an empty solution if no path is found
        return new Solution();
    }

    // Helper method to reconstruct the solution path from goal to start
    private Solution reconstructSolution(AState goalState) {
        List<AState> path = new ArrayList<>();
        AState current = goalState;
        while (current != null) {
            path.add(0, current);
            current = current.getCameFrom();
        }
        Solution sol = new Solution();
        sol.setSolutionPath(path);
        return sol;
    }

    @Override
    public String getName() {
        return "Depth First Search";
    }
}
