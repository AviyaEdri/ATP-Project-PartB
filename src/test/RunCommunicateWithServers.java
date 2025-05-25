import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.*;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class RunCommunicateWithServers {

    public static void main(String[] args) {
        // Initialize servers
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());

        // Start servers
        mazeGeneratingServer.start();
        solveSearchProblemServer.start();

        // Communicate with each server
        CommunicateWithServer_MazeGenerating();
        CommunicateWithServer_SolveSearchProblem();

        // Stop servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
    }

    public static void CommunicateWithServer_MazeGenerating() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new
                    IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new
                                        ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new
                                        ObjectInputStream(inFromServer);
                                toServer.flush();
                                int[] mazeDimensions = new int[]{50, 50};
                                toServer.writeObject(mazeDimensions); //send mazedimensions to server
                                toServer.flush();
                                byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed withMyCompressor) from server
                                InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));

                                int rows = mazeDimensions[0];
                                int cols = mazeDimensions[1];
                                int decompressedSize = 8 + rows * cols + 16; // 24 = 6 ints * 4 bytes
                                byte[] decompressedMaze = new byte[decompressedSize];

                                is.read(decompressedMaze); //Fill decompressedMaze
                                Maze maze = new Maze(decompressedMaze);
                                maze.print();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);

                        toServer.flush();

                        MyMazeGenerator mg = new MyMazeGenerator();
                        Maze maze = mg.generate(50, 50);
                        maze.print();

                        toServer.writeObject(maze);
                        toServer.flush();

                        Solution mazeSolution = (Solution) fromServer.readObject();

                        System.out.println("Solution steps:");
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                            System.out.println(i + ". " + mazeSolutionSteps.get(i));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static void CommunicateWithServer_StringReverser() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
                        PrintWriter toServer = new PrintWriter(outToServer);

                        String message = "Client Message";
                        toServer.write(message + "\n"); // שולח את ההודעה עם ירידת שורה
                        toServer.flush();

                        String serverResponse = fromServer.readLine(); // קורא את התשובה
                        System.out.println("Server response: " + serverResponse);

                        fromServer.close();
                        toServer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
