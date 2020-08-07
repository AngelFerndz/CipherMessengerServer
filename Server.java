package server;

import java.io.*;
import java.net.*;
import server.client.Client;
import server.tools.TimeStampGenerator;

public class Server implements Runnable {

    private static Server instance;
    private String name;
    private ServerSocket serverSocket;
    private int counter;

    private Server(String Name) {
        name = Name;
        println("Loading");
        connect(43122);
        counter = 0;
    }

    public static void load() {
        instance = new Server("Server");
    }

    public static Server getInstance() {
        return instance;
    }

    @Override
    public void run() {
        println("Running");
        while (true) {
            getClient();
        }
    }

    private void getClient() {
        try {
            Socket socket = serverSocket.accept();
            Client client = new Client(socket, counter);
            new Thread(client).start();
            counter++;
        } catch (IOException e) {
            println("Error | " + e);
        }
    }

    public void reset() {
        try {
            println("Reseting");
            serverSocket.close();
        } catch (IOException e) {
            println("Error | " + e);
        }
    }

    private void connect(int port) {
        try {
            println("Connecting to port: " + port);
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            println("Unable to connect to port: " + port);
            println("" + e);
            close();
        }
    }

    private void close() {
        println("Closing Cipher IM Server");
        System.exit(0);
    }

    private void println(String Text) {
        System.out.println(getTimeStamp() + " " + name + ": " + Text);
    }

    private String getTimeStamp() {
        return TimeStampGenerator.get();
    }

}
