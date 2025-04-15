package algorithms.search;

import java.util.List;

/**
 * This interface represents a generic search problem.
 * It defines methods for obtaining the start state, the goal state,
 * and retrieving all possible next states from a given state.
 */
public interface ISearchable {
    AState getStartState();
    AState getGoalState();
    List<AState> getAllPossibleStates(AState state);
}
