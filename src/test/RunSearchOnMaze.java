package test;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;
import java.util.*;
/**
 * This class demonstrates how to run different searching algorithms on a maze.
 * It generates a maze using the MyMazeGenerator class and then runs three different
 * searching algorithms (BestFirstSearch, DepthFirstSearch, and BreadthFirstSearch)
 * on the generated maze. The results of each search are printed to the console.
 */
public class RunSearchOnMaze {
    public static void main(String[] args){
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(30, 30);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        solveProblem(searchableMaze, new BestFirstSearch());
    }
    /**
     * This method solves a searching problem using the specified searcher algorithm.
     * It prints the number of nodes evaluated and the solution path.
     *
     * @param domain  The searchable domain (maze) to be solved.
     * @param searcher The searching algorithm to be used for solving the maze.
     */
    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
        //Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
        //Printing Solution Path
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s",i,solutionPath.get(i)));
        }
    }
}
