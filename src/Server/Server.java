package Server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private int ListeningIntervalMS;
    private IServerStrategy serverStrategy;
    private boolean stop;
    private ExecutorService threadPool;

    public Server(int port, int ListeningIntervalMS, IServerStrategy serverStrategy) {// Constructor implementation
        this.port = port; // Set the port number
        this.ListeningIntervalMS = ListeningIntervalMS; // Set the listening interval
        this.serverStrategy = serverStrategy; // Set the server strategy
    }

    public void start(){
        stop = false; // Initialize the stop flag to false
        threadPool = Executors.newFixedThreadPool(10); // Create a thread pool with 10 threads
        new Thread(this::runServer).start(); // Start the server in a new thread
    }

    private void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) { // Create a server socket
            System.out.println("Server started on port " + port); // Print the server start message
            while (!stop) { // Loop until the stop flag is set to true
                try {
                    Socket clientSocket = serverSocket.accept(); // Accept a client connection
                    threadPool.execute(() -> handleClient(clientSocket)); // Handle the client in a separate thread
                } catch (SocketTimeoutException e) {
                    // Ignore timeout exceptions
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for any IO exceptions
        }
    }

    private void handleClient(Socket clientSocket) {
        try (InputStream in = clientSocket.getInputStream(); // Get the input stream from the client socket
             OutputStream out = clientSocket.getOutputStream()) { // Get the output stream from the client socket
            serverStrategy.applyStrategy(in, out); // Apply the server strategy
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for any IO exceptions
        } finally {
            try {
                clientSocket.close(); // Close the client socket
            } catch (IOException e) {
                e.printStackTrace(); // Print the stack trace for any IO exceptions
            }
        }
    }

    public void stop() {
        stop = true; // Set the stop flag to true
        threadPool.shutdown(); // Shutdown the thread pool
        System.out.println("Server on port " + port +" stopped"); // Print the server stop message
        try {
            threadPool.awaitTermination(ListeningIntervalMS, java.util.concurrent.TimeUnit.MILLISECONDS); // Wait for the threads to finish
        } catch (InterruptedException e) {
            e.printStackTrace(); // Print the stack trace for any interrupted exceptions
        }
    }
}
