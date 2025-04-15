package algorithms.search;

import java.util.*;

/**
 * This class implements the Breadth First Search algorithm.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    @Override
    public Solution solve(ISearchable domain) {
        numberOfNodesEvaluated = 0;

        // Use a FIFO queue for BFS
        Queue<AState> openQueue = new LinkedList<>();
        Set<AState> closedSet = new HashSet<>();

        AState startState = domain.getStartState();
        openQueue.add(startState);

        while (!openQueue.isEmpty()) {
            AState current = openQueue.poll();
            numberOfNodesEvaluated++;  // Increase count of evaluated nodes

            // Check if current state is goal
            if (current.equals(domain.getGoalState())) {
                return reconstructSolution(current);
            }

            closedSet.add(current);
            // Get all possible successor states from current state
            List<AState> successors = domain.getAllPossibleStates(current);
            for (AState successor : successors) {
                // Only add successor if it is not already in closed or open queue
                if (!closedSet.contains(successor) && !openQueue.contains(successor)) {
                    successor.setCameFrom(current);
                    openQueue.add(successor);
                }
            }
        }
        // Return an empty solution if no path was found
        return new Solution();
    }

    // Helper method to reconstruct the solution path from goal to start
    private Solution reconstructSolution(AState goalState) {
        List<AState> path = new ArrayList<>();
        AState current = goalState;
        while (current != null) {
            path.add(0, current);  // Insert at the beginning of the list
            current = current.getCameFrom();
        }
        Solution sol = new Solution();
        sol.setSolutionPath(path);
        return sol;
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }
}
