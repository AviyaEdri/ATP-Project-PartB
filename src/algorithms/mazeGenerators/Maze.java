package algorithms.mazeGenerators;

import java.io.Serializable;

public class Maze implements Serializable {
    private int[][] maze; // 2D array representing the maze
    private int rows; // Number of rows in the maze
    private int columns; // Number of columns in the maze
    private Position startPosition; // Starting  position
    private Position goalPosition; // Goal position

    /**
     * Constructor for the Maze class.
     * Initializes the maze with null values and sets the number of rows and columns to 0.
     */
    public Maze(int rows, int columns) {
        this.maze = new int [rows][columns]; // Initialize the maze to null
        this.rows = rows; // Set the number of rows to 0
        this.columns = columns; // Set the number of columns to 0
        this.startPosition = new Position(0,0); // Initialize the starting position to null
        this.goalPosition = new Position(rows -1, columns-1); // Initialize the goal position to null
    }
    /**
     * Constructor for the Maze class.
     * Initializes the maze with a byte array.
     * The first 4 bytes represent the number of rows, the next 4 bytes represent the number of columns,
     * and the remaining bytes represent the maze data.
     * @param byteArray A byte array representing the maze object.
     */
    public Maze(byte[] byteArray){
        int index = 0; // Initialize the index to 0
        rows = 0; // Initialize the number of rows to 0
        columns = 0; // Initialize the number of columns to 0

        // Convert the first 4 bytes to an integer for the number of rows
        for (int i = 0; i < 4; i++) {
            rows |= (byteArray[index++] & 0xFF) << (i * 8);
        }

        // Convert the next 4 bytes to an integer for the number of columns
        for (int i = 0; i < 4; i++) {
            columns |= (byteArray[index++] & 0xFF) << (i * 8);
        }
        int size = (rows * columns) +  24; // Calculate the size of the maze
        if (byteArray.length != size) {
            throw new IllegalArgumentException("Invalid byte array size"); // Check if the byte array size is valid
        }

        maze = new int[rows][columns]; // Initialize the maze with the given dimensions

        // Fill the maze with data from the byte array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                maze[i][j] = byteArray[index++]; // Fill the maze with data from the byte array
            }
        }
        int startRow = 0, startCol = 0;
        int goalRow = 0, goalCol = 0;

        for (int i = 0; i < 4; i++) startRow |= (byteArray[index++] & 0xFF) << (i * 8); // Convert the starting position to bytes
        for (int i = 0; i < 4; i++) startCol |= (byteArray[index++] & 0xFF) << (i * 8); // Convert the starting position to bytes
        for (int i = 0; i < 4; i++) goalRow |= (byteArray[index++] & 0xFF) << (i * 8); // Convert the goal position to bytes
        for (int i = 0; i < 4; i++) goalCol |= (byteArray[index++] & 0xFF) << (i * 8); // Convert the goal position to bytes

        startPosition = new Position(startRow, startCol);
        goalPosition = new Position(goalRow, goalCol);
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
    /**
     * Converts the maze object to a byte array.
     * The first 4 bytes represent the number of rows, the next 4 bytes represent the number of columns,
     * and the remaining bytes represent the maze data.
     * @return A byte array representing the maze object.
     */
    public byte[] toByteArray(){
        int size = (rows * columns) + 4 * 6; // Calculate the size of the byte array
        byte[] byteArray = new byte[size]; // Create a new byte array
        int index = 0; // Initialize the index to 0

        // Convert the number of rows to bytes and store it in the byte array
        for (int i = 0; i < 4; i++) {
            byteArray[index++] = (byte) ((rows >> (i * 8)) & 0xFF);
        }

        // Convert the number of columns to bytes and store it in the byte array
        for (int i = 0; i < 4; i++) {
            byteArray[index++] = (byte) ((columns >> (i * 8)) & 0xFF);
        }

        // Convert the maze data to bytes and store it in the byte array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                byteArray[index++] = (byte) maze[i][j];
            }
        }
        // Convert the starting position to bytes and store it in the byte array
        for (int i = 0; i < 4; i++) {
            byteArray[index++] = (byte) ((startPosition.getRowIndex() >> (i * 8)) & 0xFF);
        }
        // Convert the starting position to bytes and store it in the byte array
        for (int i = 0; i < 4; i++) {
            byteArray[index++] = (byte) ((startPosition.getColumnIndex() >> (i * 8)) & 0xFF);
        }

        // Convert the goal position to bytes and store it in the byte array
        for (int i = 0; i < 4; i++) {
            byteArray[index++] = (byte) ((goalPosition.getRowIndex() >> (i * 8)) & 0xFF);
        }

        // Convert the goal position to bytes and store it in the byte array
        for (int i = 0; i < 4; i++) {
            byteArray[index++] = (byte) ((goalPosition.getColumnIndex() >> (i * 8)) & 0xFF);
        }
        return byteArray; // Return the byte array
    }
}
