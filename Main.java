package server;

import server.reportGenerator.DataCollector;
import server.messages.MessageList;

public class Main {

    private static final String version = "1.4.1";

    public static void main(String[] args) {
        print();
        load();
        run();
    }

    private static void print() {
        System.out.println("------------------------------------------");
        System.out.println("Cipher IM Server | Version " + version);
        System.out.println("Created By       | Angel Fernandez");
        System.out.println("Artgic           | https://artgic.com");
        System.out.println("------------------------------------------");
    }

    private static void load() {
        DataCollector.load();
        Server.load();
        MessageList.load();
    }

    private static void run() {
        new Thread(Server.getInstance()).start();
        new Thread(DataCollector.getInstance()).start();
    }

}
