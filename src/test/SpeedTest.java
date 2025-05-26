import algorithms.mazeGenerators.*;
import algorithms.search.*;

public class SpeedTest {
    public static void main(String[] args) {
        IMazeGenerator generator = new MyMazeGenerator(); // מחולל יותר מורכב
        Maze maze = generator.generate(50, 50); // גודל המבוך

        System.out.println("=== Maze Preview ===");
        maze.print(); // אופציונלי – לראות את המבוך עצמו

        ISearchable searchableMaze = new SearchableMaze(maze);

        runAndCompare(new BreadthFirstSearch(), searchableMaze);
        runAndCompare(new DepthFirstSearch(), searchableMaze);
        runAndCompare(new BestFirstSearch(), searchableMaze);
    }

    private static void runAndCompare(ISearchingAlgorithm algo, ISearchable domain) {
        int runs = 10;
        long totalTime = 0;
        int totalEvaluated = 0;
        int totalLength = 0;

        for (int i = 0; i < runs; i++) {
            long start = System.currentTimeMillis();
            Solution solution = algo.solve(domain);
            long end = System.currentTimeMillis();
            totalTime += (end - start);
            totalEvaluated += algo.getNumberOfNodesEvaluated();
            totalLength += solution.getSolutionPath().size();
        }

        double avgTime = totalTime / (double) runs;
        double avgNodes = totalEvaluated / (double) runs;
        double avgLength = totalLength / (double) runs;

        System.out.println("\n--- " + algo.getName() + " ---");
        System.out.println("Average Time: " + avgTime + " ms");
        System.out.println("Average Nodes Evaluated: " + avgNodes);
        System.out.println("Average Path Length: " + avgLength);
    }
}
