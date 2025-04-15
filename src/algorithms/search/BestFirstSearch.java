package algorithms.search;

import java.util.*;

/**
 * This class implements the Best First Search algorithm using a PriorityQueue.
 * The algorithm selects the state with the lowest cost first.
 */
public class BestFirstSearch extends ASearchingAlgorithm {

    @Override
    public Solution solve(ISearchable domain) {
        numberOfNodesEvaluated = 0;

        // Use a PriorityQueue to select the state with the best (lowest) cost
        PriorityQueue<AState> openQueue = new PriorityQueue<>(Comparator.comparingDouble(AState::getCost));
        Set<AState> closedSet = new HashSet<>();

        AState startState = domain.getStartState();
        startState.setCost(0); // Initialize start state's cost to 0
        openQueue.add(startState);

        while (!openQueue.isEmpty()) {
            AState current = openQueue.poll();
            numberOfNodesEvaluated++;

            if (current.equals(domain.getGoalState())) {
                return reconstructSolution(current);
            }

            closedSet.add(current);
            // Get successors from the current state
            List<AState> successors = domain.getAllPossibleStates(current);
            for (AState successor : successors) {
                // Calculate new cost; here we assume a step cost of 1 (can be adjusted)
                double newCost = current.getCost() + 1;

                // If successor is not in open or closed, add it
                if (!closedSet.contains(successor) && !openQueue.contains(successor)) {
                    successor.setCost(newCost);
                    successor.setCameFrom(current);
                    openQueue.add(successor);
                } else if (openQueue.contains(successor) && newCost < successor.getCost()) {
                    // If the new path is shorter, update the successor cost and parent
                    // Remove and re-add the successor to update its position in the queue
                    openQueue.remove(successor);
                    successor.setCost(newCost);
                    successor.setCameFrom(current);
                    openQueue.add(successor);
                }
            }
        }
        return new Solution();
    }

    // Helper method to reconstruct the solution path from goal to start
    private Solution reconstructSolution(AState goalState) {
        ArrayList<AState> path = new ArrayList<>();
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
        return "Best First Search";
    }
}
