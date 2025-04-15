package algorithms.search;

/**
 * This interface defines the behavior of a search algorithm.
 * It provides methods to solve a search problem, get the algorithm's name,
 * and count the number of nodes evaluated during the search.
 */
public interface ISearchingAlgorithm {
    Solution solve(ISearchable domain);
    String getName();
    int getNumberOfNodesEvaluated();
}
