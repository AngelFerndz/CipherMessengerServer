package server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import server.reportGenerator.DataCollector;
import server.tools.TimeStampGenerator;

public class Client implements Runnable {

    private Socket socket;
    private boolean active;
    private RequestHandler requestHandler;
    private long startTime;
    private int id;
    private DataCollector dataCollector;

    public Client(Socket socket, int ID) {
        this.socket = socket;
        id = ID;
        setDataCollector();
        loadDependencies();
        println("Connected");
    }

    @Override
    public void run() {
        while (active) {
            execute();
        }
    }

    public int getID() {
        return id;
    }

    void send(String text) {
        try {
            PrintWriter pr;
            pr = new PrintWriter(socket.getOutputStream());
            pr.println(text);
            pr.flush();
        } catch (IOException e) {
            println("Error | " + e);
            active = false;
        }
    }

    String get() {
        try {
            InputStreamReader in;
            in = new InputStreamReader(socket.getInputStream());

            BufferedReader bf;
            bf = new BufferedReader(in);

            return bf.readLine();
        } catch (IOException e) {
            end();
            return "end";
        }
    }

    void end() {
        try {
            this.finalize();
            socket.close();
            active = false;
            printElapsedTime();
            dataCollector.updateEntryEndTime(id);
        } catch (Throwable e) {
            println("Error | " + e);
        }
    }

    //PRIVATE METHODS
    private void loadDependencies() {
        active = true;
        requestHandler = new RequestHandler();
        requestHandler.setClient(this);
        startTime = TimeStampGenerator.currentTime();
    }

    private void setDataCollector() {
        dataCollector = DataCollector.getInstance();
        dataCollector.createEntry(id);
    }

    private void execute() {
        requestHandler.run();
    }

    private void printElapsedTime() {
        long endTime = TimeStampGenerator.currentTime();
        long elapsedTime = endTime - startTime;
        double elapsedSeconds = (elapsedTime / 1000.0);

        String time = (int) elapsedSeconds + " Seconds";
        if (elapsedSeconds >= 60) {
            time = (int) (elapsedSeconds / 60) + " Minutes";
        }
        println("Disconnected: " + time);
    }

    private void println(String Text) {
        String clientName = "Client-" + id;
        //CONSIDER WRITTING TO A LOG
        System.out.println(getTimeStamp() + " " + clientName + ": " + Text);
    }

    private String getTimeStamp() {
        return TimeStampGenerator.get();
    }

}
