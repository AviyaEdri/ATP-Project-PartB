package algorithms.mazeGenerators;

import java.util.*;

public class MyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int cols) {
        int height = rows * 2 + 1;
        int width = cols * 2 + 1;
        int[][] maze = new int[height][width];
        for (int[] row : maze) Arrays.fill(row, 1);

        Random rand = new Random();
        int startRow = 2 * rand.nextInt(rows) + 1;
        int startCol = 2 * rand.nextInt(cols) + 1;

        maze[startRow][startCol] = 0;
        List<int[]> walls = new ArrayList<>();
        addWalls(maze, startRow, startCol, walls);

        while (!walls.isEmpty()) {
            int[] wall = walls.remove(rand.nextInt(walls.size()));
            int wallRow = wall[0], wallCol = wall[1];
            int[][] neighbors = getNeighbors(wallRow, wallCol, maze);
            if (neighbors == null) continue;

            int[] cell1 = neighbors[0], cell2 = neighbors[1];
            if (maze[cell1[0]][cell1[1]] == 0 && maze[cell2[0]][cell2[1]] == 1) {
                maze[cell2[0]][cell2[1]] = 0;
                maze[(wallRow + cell2[0]) / 2][(wallCol + cell2[1]) / 2] = 0;
                addWalls(maze, cell2[0], cell2[1], walls);
            } else if (maze[cell1[0]][cell1[1]] == 1 && maze[cell2[0]][cell2[1]] == 0) {
                maze[cell1[0]][cell1[1]] = 0;
                maze[(wallRow + cell1[0]) / 2][(wallCol + cell1[1]) / 2] = 0;
                addWalls(maze, cell1[0], cell1[1], walls);
            }
        }

        Position innerStart = new Position(startRow, startCol);
        Position innerGoal = findFurthestCell(maze, innerStart);
        Position start = tunnelToEdge(innerStart, maze);
        Position goal = tunnelToEdge(innerGoal, maze);

        Maze result = new Maze(height, width);
        result.setMaze(maze);
        result.setStartPosition(start);
        result.setGoalPosition(goal);
        return result;
    }

    private void addWalls(int[][] maze, int row, int col, List<int[]> walls) {
        int[][] directions = {{-2, 0}, {2, 0}, {0, -2}, {0, 2}};
        for (int[] dir : directions) {
            int newRow = row + dir[0], newCol = col + dir[1];
            if (newRow > 0 && newRow < maze.length && newCol > 0 && newCol < maze[0].length) {
                int wallRow = row + dir[0] / 2, wallCol = col + dir[1] / 2;
                if (maze[newRow][newCol] == 1) {
                    walls.add(new int[]{wallRow, wallCol});
                }
            }
        }
    }

    private int[][] getNeighbors(int row, int col, int[][] maze) {
        if (row % 2 == 1 && col % 2 == 0) {
            return new int[][]{{row, col - 1}, {row, col + 1}};
        } else if (row % 2 == 0 && col % 2 == 1) {
            return new int[][]{{row - 1, col}, {row + 1, col}};
        }
        return null;
    }

    private Position findFurthestCell(int[][] maze, Position start) {
        int rows = maze.length, cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<Position> queue = new LinkedList<>();
        queue.add(start);
        visited[start.getRowIndex()][start.getColumnIndex()] = true;
        Position furthest = start;

        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            furthest = current;
            for (int[] d : directions) {
                int newRow = current.getRowIndex() + d[0];
                int newCol = current.getColumnIndex() + d[1];
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                        maze[newRow][newCol] == 0 && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    queue.add(new Position(newRow, newCol));
                }
            }
        }
        return furthest;
    }

    private Position tunnelToEdge(Position from, int[][] maze) {
        int row = from.getRowIndex();
        int col = from.getColumnIndex();
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        for (int[] dir : directions) {
            int r = row, c = col;
            while (r > 0 && r < maze.length - 1 && c > 0 && c < maze[0].length - 1) {
                r += dir[0];
                c += dir[1];
                maze[r][c] = 0;
                if (r == 0 || c == 0 || r == maze.length - 1 || c == maze[0].length - 1) {
                    return new Position(r, c);
                }
            }
        }
        maze[row][0] = 0;
        return new Position(row, 0);
    }

    @Override
    public boolean possiblePath(Maze maze) {
        return false;
    }
}
