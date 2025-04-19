package algorithms.search;

/**
 * This abstract class implements part of the common functionality
 * for search algorithms. It keeps track of the number of nodes (states)
 * that were evaluated during the search.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    protected int numberOfNodesEvaluated;
    protected String name;

    public ASearchingAlgorithm() {
        numberOfNodesEvaluated = 0;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return numberOfNodesEvaluated;
    }

    // The abstract methods solve() and getName() will be implemented by concrete algorithms,
    // such as BreadthFirstSearch, DepthFirstSearch, and BestFirstSearch.
    @Override
    public abstract Solution solve(ISearchable domain);

    @Override
    public String getName() {return name;};
}
