package server.reportGenerator;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import server.tools.*;

public class DataCollector implements Runnable {

    private static DataCollector instance;
    private ArrayList<ClientInformation> list;
    private String currentDate;

    private DataCollector() {
        list = new ArrayList<ClientInformation>();
        currentDate = getDate();
    }

    public static void load() {
        instance = new DataCollector();
    }

    public static DataCollector getInstance() {
        return instance;
    }

    @Override
    public void run() {
        while (true) {
            dailyReport();
            sleep();
        }
    }

    public void createEntry(int ID) {
        long currentTime = TimeStampGenerator.currentTime();
        ClientInformation clientInformation = new ClientInformation();
        clientInformation.id = ID;
        clientInformation.startTime = currentTime;
        list.add(clientInformation);
    }

    public void updateEntryEndTime(int ID) {
        long endTime = TimeStampGenerator.currentTime();
        ClientInformation clientInformation;
        clientInformation = getClientInformation(ID);
        clientInformation.endTime = endTime;
    }

    //PRIVATE METHODS
    private void dailyReport() {
        if (!getDate().equals(currentDate)) {
            println("Daily Report <----------------------------------------->");
            printReport();
            refresh();
        }
    }

    private void printReport() {
        printNumbers();
    }

    private void printNumbers() {
        if (list.size() > 0) {
            println("Current Connections:           " + getCurrentConnections());
            println("Total Connections:             " + list.size());
            println("Average Connection in Seconds: " + getAverageConnectionInSeconds());
            println("Average Connection in Minutes: " + getAverageConnectionInMinutes());
        } else {
            println("No Connections Today");
        }
    }

    private void refresh() {
        currentDate = getDate();
        refreshList();
    }

    private void refreshList() {
        removeEntries(generateRemoveList());
    }

    private ArrayList<ClientInformation> generateRemoveList() {
        ArrayList<ClientInformation> remove;
        remove = new ArrayList<ClientInformation>();
        for (ClientInformation C : list) {
            if (C.endTime != 0) {
                remove.add(C);
            }
        }
        return remove;
    }

    private void removeEntries(ArrayList<ClientInformation> RemoveList) {
        for (ClientInformation C : RemoveList) {
            list.remove(C);
        }
    }

    private int getCurrentConnections() {
        int amount = 0;
        for (int i = 0; i < list.size(); i++) {
            ClientInformation clientInformation;
            clientInformation = list.get(i);
            if (clientInformation.endTime == 0) {
                amount++;
            }
        }
        return amount;
    }

    private double getAverageConnectionInSeconds() {
        int[] times = getElapsedSecondsTimeArray();
        int size = times.length;
        int total = 0;
        for (int i = 0; i < size; i++) {
            total += times[i];
        }
        double average = total / size;
        return average;
    }

    private double getAverageConnectionInMinutes() {
        int[] times = getElapsedMinutesTimeArray();
        int size = times.length;
        int total = 0;
        for (int i = 0; i < size; i++) {
            total += times[i];
        }
        double average = total / size;
        return average;
    }

    private int[] getElapsedSecondsTimeArray() {
        int size = list.size();
        int[] times = new int[size];
        for (int i = 0; i < size; i++) {
            int elapsedTime;
            elapsedTime = getElapsedTimeInSeconds(list.get(i));
            times[i] = elapsedTime;
        }
        return times;
    }

    private int[] getElapsedMinutesTimeArray() {
        int size = list.size();
        int[] times = new int[size];
        for (int i = 0; i < size; i++) {
            int elapsedTime;
            elapsedTime = getElapsedTimeInMinutes(list.get(i));
            times[i] = elapsedTime;
        }
        return times;
    }

    private int getElapsedTimeInSeconds(ClientInformation clientInformation) {
        long endTime = clientInformation.endTime;
        long startTime = clientInformation.startTime;
        long elapsedTime = endTime - startTime;
        double elapsedSeconds = (elapsedTime / 1000);
        return (int) elapsedSeconds;
    }

    private int getElapsedTimeInMinutes(ClientInformation clientInformation) {
        long endTime = clientInformation.endTime;
        long startTime = clientInformation.startTime;
        long elapsedTime = endTime - startTime;
        double elapsedSeconds = elapsedTime / 1000;
        double elapsedMinutes = elapsedSeconds / 60;
        return (int) elapsedMinutes;
    }

    private ClientInformation getClientInformation(int ID) {
        for (ClientInformation C : list) {
            if (C.id == ID) {
                return C;
            }
        }
        return new ClientInformation();
    }

    private void println(String text) {
        System.out.println(getTimeStamp() + " DataCollector: " + text);
    }

    private String getTimeStamp() {
        return TimeStampGenerator.get();
    }

    private String getDate() {
        return TimeStampGenerator.getDate();
    }

    private int getHour() {
        return AdvancedClock.getRawHours();
    }

    private void sleep() {
        try {
            int minutes = AdvancedClock.getRawMinutes();
            int remainingMinutes = (60 - minutes) + 1;
            TimeUnit.MINUTES.sleep(remainingMinutes);
        } catch (Exception e) {
            println("Error | " + e);
        }
    }

}
