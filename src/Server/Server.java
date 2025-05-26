package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool;
    private ServerSocket serverSocket;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.stop = false;
    }

    public void start() {
        threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        new Thread(this::runServer).start();
    }

    private void runServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    threadPool.execute(() -> handleClient(clientSocket));
                } catch (IOException e) {
                    if (!stop) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleClient(Socket clientSocket) {
        try (InputStream in = clientSocket.getInputStream(); // Get the input stream from the client socket
             OutputStream out = clientSocket.getOutputStream()) { // Get the output stream from the client socket
            strategy.serverStrategy(in, out); // Apply the server strategy
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
        stop = true;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (threadPool != null) {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(listeningIntervalMS, TimeUnit.MILLISECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Server on port " + port + " stopped");
    }
}