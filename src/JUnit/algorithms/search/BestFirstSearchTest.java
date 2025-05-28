package algorithms.search;

import algorithms.mazeGenerators.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class    BestFirstSearchTest {

    @Test
    public void testSolutionIsNotEmpty() {
        AMazeGenerator generator = new MyMazeGenerator();
        Maze maze = generator.generate(50, 50);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        BestFirstSearch bfs = new BestFirstSearch();

        Solution solution = bfs.solve(searchableMaze);

        assertNotNull(solution, "Solution should not be null");
        assertFalse(solution.getSolutionPath().isEmpty(), "Solution path should not be empty");
    }

    @Test
    public void testStartAndEndMatch() {
        AMazeGenerator generator = new MyMazeGenerator();
        Maze maze = generator.generate(30, 30);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        BestFirstSearch bfs = new BestFirstSearch();
        Solution solution = bfs.solve(searchableMaze);

        Position expectedStart = maze.getStartPosition();
        Position actualStart = ((MazeState) solution.getSolutionPath().get(0)).getPosition();

        Position expectedGoal = maze.getGoalPosition();
        Position actualGoal = ((MazeState) solution.getSolutionPath().get(solution.getSolutionPath().size() - 1)).getPosition();

        assertEquals(expectedStart, actualStart, "Start position should match");
        assertEquals(expectedGoal, actualGoal, "Goal position should match");
    }

}
