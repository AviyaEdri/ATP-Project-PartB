package algorithms.mazeGenerators;

public class Maze {
    private int[][] maze; // 2D array representing the maze
    private int rows; // Number of rows in the maze
    private int columns; // Number of columns in the maze
    private Position startPosition; // Starting  position
    private Position goalPosition; // Goal position

    /**
     * Constructor for the Maze class.
     * Initializes the maze with null values and sets the number of rows and columns to 0.
     */
    public Maze() {
        this.maze = null; // Initialize the maze to null
        this.rows = 0; // Set the number of rows to 0
        this.columns = 0; // Set the number of columns to 0
        this.startPosition = null; // Initialize the starting position to null
        this.goalPosition = null; // Initialize the goal position to null
    }


    public int[][] getMaze() {
        return maze; // Return the maze
    }
    public int getRows() {
        return rows; // Return the number of rows
    }
    public int getColumns() {
        return columns; // Return the number of columns
    }
    public Position getStartPosition() {
        return startPosition; // Return the starting position
    }
    public Position getGoalPosition() {
        return goalPosition; // Return the goal position
    }
    public void setMaze(int[][] maze) {
        this.maze = maze; // Set the maze
    }
    public void setRows(int rows) {
        this.rows = rows; // Set the number of rows
    }
    public void setColumns(int columns) {
        this.columns = columns; // Set the number of columns
    }
    public void setStartPosition(Position start) {
        this.startPosition = start; // Set the starting position
    }
    public void setGoalPosition(Position goal) {
        this.goalPosition = goal; // Set the goal position
    }

    /**
     * Prints the maze in a readable format.
     * It prints 'S' for the starting position, 'E' for the goal position, '#' for walls, and ' ' for empty cells.
     * This function iterates through each cell in the maze and prints the corresponding character.
     * It uses System.out.print() to print characters without new lines and System.out.println() to move to the next line after each row.
     */
    public void print(){
        for(int i = 0; i< rows; i++){
            for(int j = 0; j< columns; j++){
                if(startPosition != null && i == startPosition.getRowIndex() && j == startPosition.getColumnIndex()){
                    System.out.print("S"); // Print 'S' for the starting position
                }
                else if(goalPosition != null && i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex()){
                    System.out.print("E"); // Print 'E' for the goal position
                }
                else if(maze[i][j] == 1){
                    System.out.print("#"); // Print '#' for walls
                }
                else{
                    System.out.print(" "); // Print ' ' for empty cells
                }
            }
            System.out.println(); // Move to the next line after printing a row
        }
    }
}
