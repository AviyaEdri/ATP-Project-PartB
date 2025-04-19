package JUnit.algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.ISearchable;
import algorithms.search.BestFirstSearch;
import algorithms.search.Solution;
import algorithms.search.MazeState;
import algorithms.search.SearchableMaze;
import algorithms.mazeGenerators.MyMazeGenerator;


import algorithms.mazeGenerators.Position;


import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BestFirstSearchTest {

    @Test
    /**
     * verifies that when the searchable object has no neighbors (i.e., an empty maze), the solution path returned is empty.
     */
    void testEmptyMaze() {
        ISearchable empty = new EmptySearchableMock();
        BestFirstSearch bfs = new BestFirstSearch();
        Solution sol = bfs.solve(empty);
        assertTrue(sol.getSolutionPath().isEmpty(),"Should be no path in empty maze");
    }
    @Test
    /**
     * Tests a trivial 1x2 maze where the path is known and direct. It asserts that the solution contains exactly the start and goal states.
     */
    void testTrivialMaze() {
        Maze maze = new Maze(1, 2); // 1 row, 2 columns

        // Create a simple path manually
        int[][] map = maze.getMaze();
        map[0][0] = 0; // start
        map[0][1] = 0; // goal

        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(0, 1));

        ISearchable searchableMaze = new SearchableMaze(maze);
        BestFirstSearch bfs = new BestFirstSearch();
        Solution solution = bfs.solve(searchableMaze);

        assertNotNull(solution, "Solution should not be null");
        assertEquals(2, solution.getSolutionPath().size(), "Path should contain start and goal");
    }



    @Test
    /**
     *  Uses MyMazeGenerator to create a random 5x5 maze and ensures that:
     *
     * The solution is not null.
     *
     * The path is not empty.
     *
     * The first node in the path matches the maze's start position.
     */
    void testMazeGeneratedSearch() {
        MyMazeGenerator generator = new MyMazeGenerator();
        Maze maze = generator.generate(5, 5);

        ISearchable searchableMaze = new SearchableMaze(maze);

        BestFirstSearch bfs = new BestFirstSearch();
        Solution solution = bfs.solve(searchableMaze);

        assertNotNull(solution);
        assertFalse(solution.getSolutionPath().isEmpty(), "Path should not be empty in a valid maze");

        // 5. אפשר גם לבדוק שהנקודה הראשונה היא נקודת ההתחלה של המבוך
        assertEquals(maze.getStartPosition(), ((MazeState) solution.getSolutionPath().get(0)).getPosition());
    }

    static class EmptySearchableMock implements ISearchable {
        private AState start = new MazeState(0, 0);
        private AState goal = new MazeState(0, 1);


        @Override
        public AState getStartState() { return start; }

        @Override
        public AState getGoalState() { return goal; }

        @Override
        public List<AState> getAllPossibleStates(AState state) {
            return new ArrayList<>(); // No neighbors
        }
    }
}
