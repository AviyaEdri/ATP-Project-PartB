package Client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Client class that implements the IClientStrategy interface.
 * This class is responsible for defining the client-side strategy for handling input and output streams.
 */
public class Client{
    private String IP;
    private int serverPort;
    private IClientStrategy clientStrategy;

    public Client(String IP, int serverPort, IClientStrategy clientStrategy) {
        this.IP = IP;
        this.serverPort = serverPort;
        this.clientStrategy = clientStrategy;
    }

    public void start(){
        try(Socket serverSocket = new Socket(IP, serverPort)) { // Create a socket to connect to the server
           System.out.println("connected to server - IP: "+ IP + ", Port: " + serverPort); // Print the connection message
            InputStream in = serverSocket.getInputStream(); // Get the input stream from the socket
            OutputStream out = serverSocket.getOutputStream(); // Get the output stream from the socket
            clientStrategy.clientStrategy(in, out); // Call the client strategy method with the input and output streams
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur
        }
    }

    public void communicateWithServer() {
        try {
            start(); // Start the client
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur
        }
    }
}
