package algorithms.search;

import java.util.*;

public class BestFirstSearch extends ASearchingAlgorithm {

    public BestFirstSearch() {
        this.name = "Best First Search";
    }

    @Override
    public Solution solve(ISearchable domain) {
        numberOfNodesEvaluated = 0;
        PriorityQueue<AState> openQueue = new PriorityQueue<>(Comparator.comparingDouble(AState::getCost));
        Set<AState> closedSet = new HashSet<>();

        AState startState = domain.getStartState();
        startState.setCost(0);
        openQueue.add(startState);

        while (!openQueue.isEmpty()) {
            AState current = openQueue.poll();
            numberOfNodesEvaluated++;

            if (current.equals(domain.getGoalState())) {
                return reconstructSolution(current);
            }

            closedSet.add(current);
            for (AState successor : domain.getAllPossibleStates(current)) {
                double newCost = current.getCost() + 1;
                if (!closedSet.contains(successor) && !openQueue.contains(successor)) {
                    successor.setCost(newCost);
                    successor.setCameFrom(current);
                    openQueue.add(successor);
                } else if (openQueue.contains(successor) && newCost < successor.getCost()) {
                    openQueue.remove(successor);
                    successor.setCost(newCost);
                    successor.setCameFrom(current);
                    openQueue.add(successor);
                }
            }
        }
        return new Solution();
    }

    private Solution reconstructSolution(AState goalState) {

        ArrayList<AState> path = new ArrayList<>();
        AState current = goalState;
        while (current != null) {
            path.add(0, current);
            current = current.getCameFrom();
        }
        Solution sol = new Solution();
        sol.setSolutionPath(path);
        // Optionally, you can print the solution path
        //System.out.println("Best First Search Solution Path:");
        //System.out.println(String.format("Best First Search evaluated %d nodes", numberOfNodesEvaluated));
        System.out.println("SERVER: Building solution path from goal: " + goalState);
        int count = 0;
        AState curr = goalState;
        while (curr != null) {
            System.out.println(" ← " + curr);
            current = current.getCameFrom();
            count++;
        }
        System.out.println("SERVER: Path length = " + count);

        return sol;
    }
}
